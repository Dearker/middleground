package com.hanyi.daily.thread.pojo;

/**
 * @PackAge: middleground com.hanyi.daily.thread.pojo
 * @Author: weiwenchang
 * @Description: ThreadLocal原理：ThreadLocal通过map集合,Map.put(“当前线程”,值)；
 * @CreateDate: 2020-02-10 20:01
 * @Version: 1.0
 */
public class Res {

    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public Integer getNumber() {
        int count = threadLocal.get() + 1;
        threadLocal.set(count);
        return count;
    }

}
