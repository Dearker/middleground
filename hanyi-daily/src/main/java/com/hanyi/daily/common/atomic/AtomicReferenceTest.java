package com.hanyi.daily.common.atomic;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author weiwenchang
 * 修改user的属性
 */
public class AtomicReferenceTest {

    @Test
    public void atomicReferenceTest() {
        User u1 = new User("张三", 22);
        User u2 = new User("李四", 33);

        AtomicReference<User> ar = new AtomicReference<>(u1);
        ar.compareAndSet(u1, u2);

        System.out.println(ar.get());
    }

    /**
     * 参数1：数组下标； 参数2：修改原始值对比； 参数3：修改目标值 修改成功返回true，否则返回false
     * compareAndSet(int, Object, Object)
     * 参数1：数组下标 参数2：修改的目标 修改成功为止，返回修改前的数据
     * getAndSet(int, Object)
     */
    @Test
    public void atomicReferenceArrayTest() {
        User u1 = new User("张三", 22);
        User u2 = new User("李四", 33);
        User[] arr = {u1, u2};

        AtomicReferenceArray<User> ara = new AtomicReferenceArray<>(arr);
        System.out.println(ara.toString());

        User u3 = new User("王五", 44);
        ara.compareAndSet(0, u1, u3);
        System.out.println(ara.toString());
    }

    @Test
    public void atomicMarkableReferenceTest() {
        User u1 = new User("张三", 22);
        User u2 = new User("李四", 33);

        //和AtomicStampedReference效果一样，用于解决ABA的
        //区别是表示不是用的版本号，而只有true和false两种状态。相当于未修改和已修改
        AtomicMarkableReference<User> amr = new AtomicMarkableReference<>(u1, true);
        amr.compareAndSet(u1, u2, false, true);

        System.out.println(amr.getReference());
    }

    /**
     * addAndGet(int, int)//执行加法，第一个参数为数组的下标，第二个参数为增加的 数量，返回增加后的结果
     * compareAndSet(int, int, int)// 对比修改，参1数组下标，参2原始值，参3修 改目标值，成功返回true否则false
     * decrementAndGet(int)// 参数为数组下标，将数组对应数字减少1，返回减少后的 数据
     * incrementAndGet(int)// 参数为数组下标，将数组对应数字增加1，返回增加后的 数据
     * getAndAdd(int, int)// 和addAndGet类似，区别是返回值是变化前的数据
     * getAndDecrement(int)// 和decrementAndGet类似，区别是返回变化前的数据
     * getAndIncrement(int)// 和incrementAndGet类似，区别是返回变化前的数据
     * getAndSet(int, int)// 将对应下标的数字设置为指定值，第二个参数为设置的值， 返回是变化前的数据
     */
    @Test
    public void atomicIntegerArrayTest() {
        int[] arr = {1, 2, 3, 4, 5};
        AtomicIntegerArray aia = new AtomicIntegerArray(arr);

        aia.compareAndSet(1, 2, 200);
        System.out.println(aia.toString());
    }

    /**
     * 创建修改整型类型属性的原子操作更新器，使用的是newUpdater()方法创建，需要2个参数
     * 第一个参数为需要操作的类的class数据
     * 第二个参数为需要操作的类的哪一个属性：属性名
     * <p>
     * 通过反射实现
     */
    @Test
    public void atomicIntegerFieldUpdaterTest() {
        AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User user = new User("Java", 22);
        System.out.println(a.get(user));
        System.out.println(a.getAndAdd(user, 10));
        System.out.println(a.get(user));
    }

    /**
     * AtomicLong是多个线程去竞争一个变量，随并发的增加性能会下降
     * LongAdder是多个线程去竞争多个变量，性能相比AtomicLong较高（1.8）
     */
    @Test
    public void longAdderTest() {
        AtomicLong atomicLong = new AtomicLong(0L);
        LongAdder longAdder = new LongAdder();

        TimeInterval timer = DateUtil.timer();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    //atomicLong.incrementAndGet();
                    longAdder.increment();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) {
        }
        System.out.println(atomicLong.get());
        System.out.println(longAdder.longValue());
        System.out.println("耗时：" + timer.intervalRestart());
    }

    /**
     * 读写锁
     */
    @Test
    public void reentrantReadWriteLockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        WriteDemo writeDemo = new WriteDemo(lock);
        ReadDemo readDemo = new ReadDemo(lock);

        for (int i = 0; i < 3; i++) {
            new Thread(writeDemo).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(readDemo).start();
        }
    }

    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        volatile int age;
    }

    private static volatile int count = 0;

    @AllArgsConstructor
    static class WriteDemo implements Runnable {
        private final ReentrantReadWriteLock lock;

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.writeLock().lock();
                count++;
                lock.writeLock().unlock();
            }
        }
    }

    @AllArgsConstructor
    static class ReadDemo implements Runnable {

        private final ReentrantReadWriteLock lock;

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.readLock().lock();
                System.out.println(count);
                lock.readLock().unlock();
            }
        }
    }

}
