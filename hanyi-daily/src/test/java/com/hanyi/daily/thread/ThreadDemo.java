package com.hanyi.daily.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import com.hanyi.daily.thread.pojo.Athlete;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackAge: middleground com.hanyi.daily.thread
 * @Author: weiwenchang
 * @Description: 线程测试类
 * @CreateDate: 2020-02-09 16:47
 * @Version: 1.0
 */
public class ThreadDemo {

    private static final ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(Integer.SIZE, Integer.SIZE);

    /**
     * join 子线程先执行，执行完成后再执行主线程
     *
     * @throws InterruptedException
     */
    @Test
    public void joinTest() throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                System.out.println("子线程: " + i);
            }
        });
        thread.start();
        thread.join();

        for (int i = 0; i < 30; i++) {
            System.out.println("主线程: " + i);
        }
    }

    /**
     * 初始化线程池的核心线程数最好为需要执行的任务数量，在最开始的时候初始化完成，
     * 后续无需再进行创建线程，效率最高，不过还需要考虑服务器是IO密集型还是CPU密集型
     * <p>
     * 频繁的在同一时间创建线程池，如果创建的线程数相同，则会使用当前存活的线程；
     * 如果创建的线程数每次都不一样，实际上是用当前存活的线程数进行累加
     * <p>
     * 多个线程异步执行，在将各线程执行的结果汇总
     */
    @Test
    public void multithreadedAsyncCallback() throws Exception {


        System.out.println("当前线程总数：" + threadPoolExecutor.getMaximumPoolSize());
        TimeInterval timer = DateUtil.timer();
        int length = 32;

        List<Integer> integerList = new ArrayList<>(length);

        List<Athlete> personList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            personList.add(new Athlete(i));
        }
        List<Future<Integer>> futureList = threadPoolExecutor.invokeAll(personList, 5, TimeUnit.MINUTES);

        for (Future<Integer> future : futureList) {
            integerList.add(future.get());
        }

        System.out.println("执行耗时--》 " + timer.intervalRestart());
        System.out.println("获取的数组为：" + integerList);
        int sum = integerList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("总数为：" + sum);

    }

    /**
     * 定时线程池,设置守护线程，当JVM的用户线程退出时，守护线程会舍弃掉任务直接退出
     */
    @Test
    public void scheduledExecutorServiceTest() {
        TimeInterval timer = DateUtil.timer();

        AtomicInteger atomicInteger = new AtomicInteger(0);

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2, r -> {
            Thread thread = new Thread(r);
            //设置为守护线程
            thread.setDaemon(true);
            thread.setName("scheduled_" + atomicInteger.incrementAndGet());
            return thread;
        });

        executorService.schedule(() -> System.out.println(Thread.currentThread().getName()), 2, TimeUnit.MILLISECONDS);
        System.out.println("消耗时间：" + timer.intervalMs());
    }

    /**
     * CompletableFuture 测试类
     */
    @Test
    public void completableFutureAsyncTest() throws ExecutionException, InterruptedException {

        System.out.println("main...start");

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return 10 / 0;
        }, threadPoolExecutor).whenComplete((result, exception) -> {
            //可以得到异常信息，但无法修改返回数据
            System.out.println("运行结果：" + result + "异常：" + exception);
        }).exceptionally(throwable -> {
            //可以感知异常，同时返回默认值
            return 10;
        });

        //如果在异常时未指定默认值，则在调用get()方法获取返回值时会直接将异常抛出
        Integer integer = future.get();
        System.out.println("最终运行结果：" + integer);
        System.out.println("main...end");

    }

    /**
     * 通过handle()方法处理出现异常的默认返回值
     *
     * @throws ExecutionException   异常
     * @throws InterruptedException 异常
     */
    @Test
    public void CompletableFutureHandleTest() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return 10 / 0;
        }, threadPoolExecutor).handle((result, exception) -> {
            if (Objects.nonNull(result)) {
                return result * 2;
            }
            if (Objects.nonNull(exception)) {
                return 0;
            }
            return 1;
        });

        System.out.println("获取的结果数据：" + future.get());
    }

    /**
     * 线程串行化方法
     * 1）、thenApply()方法：当一个线程依赖另一个线程时，获取上一个任务的返回结果，并返回当前任务的返回值
     * 2）、thenAccept()方法：接收任务的处理结果，并消费处理。无返回结果
     * 3）、thenRun()方法：只要上面的任务执行完成，就开始执行thenRun()，只是处理完任务后，执行thenRun的后续操作
     *                    无法获取到上一步的返回结果
     * 注：不带Async后缀的都是拿到上一个执行任务的线程进行执行，带Async后缀的都会创建一个新的线程异步去执行任务
     */
    @Test
    public void completableFutureThenTest() throws Exception {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return 10 / 4;
        }, threadPoolExecutor).thenApplyAsync(result -> {
            System.out.println(result);
            return "柯基" + result;
        }, threadPoolExecutor);

        System.out.println("获取的结果数据：" + future.get());
    }

    /**
     * 两个任务必须都完成，触发该任务
     * 1）、thenCombine()方法：组合两个future，获取两个future的返回结果，并返回当前任务的返回值
     * 2）、thenAcceptBoth()方法：组合两个future，获取两个future的返回结果，然后处理任务，没有返回值
     * 3）、runAfterBoth()方法：组合两个future，不需要获取future的结果，只需两个future处理完任务后，处理该任务
     */
    @Test
    public void completableFutureBothTest() throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return 10 / 4;
        }, threadPoolExecutor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return "柯基";
        }, threadPoolExecutor);

        CompletableFuture<String> thenCombine = future1.thenCombineAsync(future2, (result1, result2) -> {
            System.out.println("-----");
            return result1 + ":" + result2;
        }, threadPoolExecutor);

        System.out.println("获取的数据：" + thenCombine.get());
    }

    /**
     * 当两个任务中，任意一个future任务完成的时候，执行任务
     *      1）、applyToEither()方法：两个任务中有一个执行完成，获取它的返回值，处理任务并有新的返回值
     *      2）、acceptEither()方法：两个任务中有一个执行完成，获取它的返回值，处理任务，没有新的返回值
     *      3）、runAfterEither()方法：两个任务中有一个执行完成，不需要获取future的结果，处理任务，也没有返回值
     *  注：两个任务的返回类型需要兼容
     */
    @Test
    public void completableFutureEitherTest() throws ExecutionException, InterruptedException {

        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return 10 / 4;
        }, threadPoolExecutor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return "柯基";
        }, threadPoolExecutor);

        CompletableFuture<String> future = future1.applyToEitherAsync(future2, (result) -> {
            System.out.println();
            return result.toString() + "框架";
        }, threadPoolExecutor);
        System.out.println("获取的返回数据："+ future.get());
    }

    /**
     * 多任务组合
     *      1)、allOf()方法：等待所有任务完成
     *      2)、anyOf()方法：只要有一个任务完成
     */
    @Test
    public void completableFutureOfTest(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return "小短腿";
        }, threadPoolExecutor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            return "柯基";
        }, threadPoolExecutor);

        List<String> stringList = Stream.of(future1, future2).map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("获取的数据：" + stringList);
    }


}
