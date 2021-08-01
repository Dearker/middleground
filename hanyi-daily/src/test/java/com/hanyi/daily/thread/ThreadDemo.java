package com.hanyi.daily.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import com.hanyi.daily.pojo.TimeInfo;
import com.hanyi.daily.thread.pojo.Accumulator;
import com.hanyi.daily.thread.pojo.Athlete;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ThreadPoolExecutor调用其对应的setXXX()方法可以动态的修改线程池的相关配置属性
 *
 * @Author: weiwenchang
 * @Description: 线程测试类
 * @CreateDate: 2020-02-09 16:47
 */
@Slf4j
public class ThreadDemo {

    private static final ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(Integer.SIZE, Integer.SIZE);

    /**
     * join 子线程先执行，执行完成后再执行主线程
     *
     * @throws InterruptedException 抛出线程异常
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
     * 不能将ThreadLocalRandom的实例设置到静态变量中，
     * 使用current() 的时候初始化一个初始化种子到线程，每次nextSeed再使用之前的种子生成新的种子
     *
     * @throws InterruptedException 异常
     */
    @Test
    public void localRandomTest() throws InterruptedException {
        //循环次数
        int loopCount = 10000000;
        //线程数量
        int threadCount = 10;
        //元素数量
        int itemCount = 10;

        ConcurrentHashMap<String, Long> longConcurrentHashMap = new ConcurrentHashMap<>(itemCount);
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, loopCount).parallel().forEach(i -> {
            //获得一个随机的Key
            String key = "item" + ThreadLocalRandom.current().nextInt(itemCount);
            synchronized (longConcurrentHashMap) {
                if (longConcurrentHashMap.containsKey(key)) {
                    //Key存在则+1
                    longConcurrentHashMap.put(key, longConcurrentHashMap.get(key) + 1);
                } else {
                    //Key不存在则初始化为1
                    longConcurrentHashMap.put(key, 1L);
                }
            }
        }));

        //上述代码的优化方案
        ConcurrentHashMap<String, LongAdder> adderConcurrentHashMap = new ConcurrentHashMap<>(itemCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, loopCount).parallel().forEach(i -> {
            //获得一个随机的Key
            String key = "item" + ThreadLocalRandom.current().nextInt(itemCount);
            //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
            adderConcurrentHashMap.computeIfAbsent(key, k -> new LongAdder()).increment();
        }));

        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        Map<String, Long> longMap = new HashMap<>(itemCount);
        adderConcurrentHashMap.forEach((k, v) -> longMap.put(k, v.longValue()));
        System.out.println("优化前的数据：" + longConcurrentHashMap);
        System.out.println("优化后的数据：" + longMap);
    }

    @Test
    public void forkJoinPoolTest() {
        TimeInterval timer = DateUtil.timer();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        List<Callable<Integer>> callableList = new ArrayList<>();
        IntStream.rangeClosed(1, 5).forEach(s ->
                callableList.add(() -> {
                    TimeUnit.SECONDS.sleep(1);
                    return s;
                }));

        List<Future<Integer>> futureList = forkJoinPool.invokeAll(callableList);
        Integer reduce = futureList.stream().map(integerFuture -> {
            int result = 0;
            try {
                result = integerFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e);
            }
            return result;
        }).reduce(0, Integer::sum);

        System.out.println("获取的总数" + reduce);
        System.out.println("执行耗时：" + timer.intervalMs());
    }

    @Test
    public void forkJoinTaskTest() {
        TimeInterval timer = DateUtil.timer();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        List<ForkJoinTask<Integer>> forkJoinTaskList = new ArrayList<>();
        IntStream.rangeClosed(1, 5).forEach(s ->
                forkJoinTaskList.add(forkJoinPool.submit(() -> {
                    TimeUnit.SECONDS.sleep(1);
                    return s;
                })));

        Integer reduce = forkJoinTaskList.stream().map(ForkJoinTask::join).reduce(0, Integer::sum);
        System.out.println("获取的总数" + reduce);
        System.out.println("执行耗时：" + timer.intervalMs());
    }

    /**
     * 创建线程池执行任务
     *
     * @throws InterruptedException 异常
     */
    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        //使用一个计数器跟踪完成的任务数
        AtomicInteger atomicInteger = new AtomicInteger();

        //创建一个具有2个核心线程、5个最大线程，使用容量为10的ArrayBlockingQueue阻塞队列作为工作队列的线程池，使用默认的AbortPolicy拒绝策略
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                ThreadUtil.newNamedThreadFactory("demo-threadpool-", false),
                new ThreadPoolExecutor.AbortPolicy());
        //启动所有核心线程
        threadPool.prestartAllCoreThreads();
        //线程池在空闲的时候同样回收核心线程
        threadPool.allowCoreThreadTimeOut(Boolean.TRUE);

        //每隔1秒提交一次，一共提交20次任务
        IntStream.rangeClosed(1, 20).forEach(i -> {
            ThreadUtil.sleep(1000);
            int id = atomicInteger.incrementAndGet();
            try {
                threadPool.submit(() -> {
                    log.info("{} started", id);
                    //每个任务耗时10秒
                    ThreadUtil.sleep(10000);
                    log.info("{} finished", id);
                });
            } catch (Exception ex) {
                //提交出现异常的话，打印出错信息并为计数器减一
                log.error("error submitting task {}", id, ex);
                atomicInteger.decrementAndGet();
            }
        });

        TimeUnit.SECONDS.sleep(60);
        System.out.println(atomicInteger.intValue());
    }

    /**
     * invokeAll会等所有任务都处理完成了之后才将结果一起返回，返回之后才会释放所有线程
     * <p>
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
     * 使用并行流进行多个线程异步执行，并汇总执行的结果
     */
    @Test
    public void multithreadedParallelStream() {
        TimeInterval timer = DateUtil.timer();

        int length = 5;
        List<Accumulator> accumulatorList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            accumulatorList.add(new Accumulator(i));
        }
        //并行流
        int sum = accumulatorList.parallelStream().mapToInt(Accumulator::getNumber).sum();
        System.out.println("并行流耗时：" + timer.intervalRestart());
        System.out.println("并行流总数：" + sum);

        //串行流
        int total = accumulatorList.stream().mapToInt(Accumulator::getNumber).sum();
        System.out.println("串行流耗时：" + timer.intervalRestart());
        System.out.println("串行流总数：" + total);
    }

    /**
     * 直接在并行流中的map函数中完成计算即可
     */
    @Test
    public void parallelStreamMapTest() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);

        TimeInterval timer = DateUtil.timer();

        List<String> stringList = integerList.parallelStream().map(s -> {
            ThreadUtil.sleep(1000);
            return "哈士奇" + s;
        }).collect(Collectors.toList());

        //耗时：1066
        System.out.println("并行流耗时：" + timer.intervalRestart());
        System.out.println(stringList);

        List<String> list = integerList.stream().map(s -> {
            ThreadUtil.sleep(1000);
            return "柯基" + s;
        }).collect(Collectors.toList());

        //耗时：5014
        System.out.println("串行流耗时：" + timer.intervalRestart());
        System.out.println(list);
    }

    /**
     * 定时线程池,设置守护线程，当JVM的用户线程退出时，守护线程会舍弃掉任务直接退出
     * 定时每5秒执行一次
     * <p>
     * scheduleAtFixedRate：每隔1秒执行一次任务，而执行一次任务的时间需要5秒，执行效果相当于每隔5秒执行一次任务
     * scheduleWithFixedDelay：每次任务执行完成之后延时1秒再执行，而执行一次任务的时间需要5秒，即5秒执行完之后，再延时1秒，执行效果相当于每隔6秒执行一次任务
     */
    @Test
    public void scheduledExecutorServiceTest() throws InterruptedException {
        TimeInterval timer = DateUtil.timer();
        AtomicInteger atomicInteger = new AtomicInteger(0);

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3, r -> {
            Thread thread = new Thread(r);
            //设置为守护线程
            thread.setDaemon(true);
            thread.setName("scheduled_" + atomicInteger.incrementAndGet());
            return thread;
        });

        //线程号：scheduled_1，启动2秒后开始每5秒执行一次
        executorService.scheduleAtFixedRate(() ->
                        System.out.println(Thread.currentThread().getName() + ":" + DateUtil.date()),
                2, 5, TimeUnit.SECONDS);

        //线程号：scheduled_2，启动2秒后开始每3秒执行一次
        executorService.scheduleAtFixedRate(() ->
                        System.out.println(Thread.currentThread().getName() + "---" + DateUtil.date()),
                2, 3, TimeUnit.SECONDS);

        //线程号: scheduled_3，启动1秒后开始执行，每次任务执行完成之后延时2秒再执行
        executorService.scheduleWithFixedDelay(() ->
                System.out.println(Thread.currentThread().getName() + " || " + DateUtil.date()), 1, 2, TimeUnit.SECONDS);

        System.out.println("消耗时间：" + timer.intervalMs());

        //注册JVM钩子函数代码,当JVM退出时调用线程池的关闭方法
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();
            System.out.println("关闭executorService线程池");
        }));

        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * 延迟线程，该线程会在一秒之后执行，并且只执行一次，会新创建一个线程进行执行该任务
     * 如果当前延迟的任务过多时，则可能无法在指定的延迟时间全部开始执行
     */
    @Test
    public void scheduledThreadPoolTest() {
        // 创建线程池
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
        // 添加定时执行任务(1s 后执行)
        System.out.println("添加任务线程：" + Thread.currentThread().getName() + "时间:" + DateUtil.date());
        threadPool.schedule(() -> {
            System.out.println("任务被执行线程：" + Thread.currentThread().getName() + "时间:" + DateUtil.date());
            ThreadUtil.sleep(1000);
        }, 1, TimeUnit.SECONDS);

        ThreadUtil.sleep(2000);
    }

    /**
     * 抢占式执行的线程池（任务执行顺序不确定）,jdk1.8才能使用，底层使用ForkJoinPool实现
     */
    @Test
    public void workStealingPoolTest() {
        // 创建线程池
        ExecutorService threadPool = Executors.newWorkStealingPool();
        // 执行任务
        IntStream.range(0, 10).forEach(s -> threadPool.execute(() -> {
            ThreadUtil.sleep(1000);
            System.out.println(s + " 被执行,线程名:" + Thread.currentThread().getName());
        }));
        ThreadUtil.sleep(2000);
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
     * 无法获取到上一步的返回结果
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
    public void completableFutureBothTest() throws Exception {
        TimeInterval timer = DateUtil.timer();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10 / 4;
        }, threadPoolExecutor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程号 -> " + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "柯基";
        }, threadPoolExecutor);

        CompletableFuture<String> thenCombine = future1.thenCombineAsync(future2, (result1, result2) -> {
            System.out.println("-----");
            return result1 + ":" + result2;
        }, threadPoolExecutor);

        System.out.println("获取的数据：" + thenCombine.get());
        System.out.println("耗时：" + timer.intervalMs());
    }

    /**
     * 当两个任务中，任意一个future任务完成的时候，执行任务
     * 1）、applyToEither()方法：两个任务中有一个执行完成，获取它的返回值，处理任务并有新的返回值
     * 2）、acceptEither()方法：两个任务中有一个执行完成，获取它的返回值，处理任务，没有新的返回值
     * 3）、runAfterEither()方法：两个任务中有一个执行完成，不需要获取future的结果，处理任务，也没有返回值
     * 注：两个任务的返回类型需要兼容
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
        System.out.println("获取的返回数据：" + future.get());
    }

    /**
     * 多任务组合
     * 1)、allOf()方法：等待所有任务完成
     * 2)、anyOf()方法：只要有一个任务完成
     */
    @Test
    public void completableFutureOfTest() {
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

    /**
     * 应用场景测试
     */
    @Test
    public void applicationScenarioTest() throws ExecutionException, InterruptedException {
        TimeInterval timer = DateUtil.timer();

        TimeInfo timeInfo = new TimeInfo();
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long nextId = IdUtil.createSnowflake(1, 1).nextId();
            timeInfo.setId(nextId);
            return nextId;
        }, threadPoolExecutor);

        CompletableFuture<Void> thenAcceptAsync = future.thenAcceptAsync(result -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("根据主键id查询详情");
            timeInfo.setTimeExtent(System.currentTimeMillis());
        }, threadPoolExecutor);

        CompletableFuture<Void> thenAcceptAsync2 = future.thenAcceptAsync(result -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("根据主键id查询日期格式");
            timeInfo.setFormatName("日期格式转换");
        }, threadPoolExecutor);

        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("设置对象创建时间");
            timeInfo.setCreateTime(LocalDateTime.now());
        }, threadPoolExecutor);

        //等待所有任务线程执行完成
        CompletableFuture.allOf(thenAcceptAsync, thenAcceptAsync2, runAsync).get();

        System.out.println("获取的数据：" + timeInfo);
        //总共耗时3秒
        System.out.println("耗时：" + timer.intervalMs());
    }

}
