package com.hanyi.daily.loadBalance;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @PackAgeName: middleground com.hanyi.daily
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-08 11:59
 * @Version: 1.0
 */
public class LoadBalanceDemo {

    private static int pos = 0;

    private static final Map<String, Integer> ipServerMap = new ConcurrentHashMap<>(2 << 1);

    static {
        ipServerMap.put("192.168.13.1", 1);
        ipServerMap.put("192.168.13.2", 2);
        ipServerMap.put("192.168.13.3", 4);
    }

    /**
     * 轮询
     */
    @Test
    public void lunXunTest() {

        int i = 0;
        int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int index = 0;
        for (; i < 11; i++) {
            index = (index + 1) % arr.length;
            System.out.println(arr[index]);
        }
    }

    @Test
    public void roundRobinTest() {
        for (int i = 0; i < 10; i++) {
            String serverName = roundRobin();
            System.out.println(serverName);
        }
    }

    private String roundRobin() {

        List<String> ipList = new ArrayList<>(ipServerMap.keySet());
        String serverName = null;
        //4.定义一个循环的值，如果大于set就从0开始
        synchronized (this) {
            if (pos >= ipServerMap.size()) {
                pos = 0;
            }
            serverName = ipList.get(pos);
            //轮询+1
            pos++;
        }
        return serverName;
    }

    /**
     * 加权轮询
     */
    @Test
    public void weightRobinTest() {
        for (int i = 0; i < 10; i++) {
            String serverName = weightRobin();
            System.out.println(serverName);
        }
    }

    private String weightRobin() {

        List<String> ipList = new ArrayList<>(3);

        for (String serverName : ipServerMap.keySet()) {
            Integer weight = ipServerMap.get(serverName);
            for (int i = 0; i < weight; i++) {
                ipList.add(serverName);
            }
        }

        String serverName = null;
        if (pos >= ipList.size()) {
            pos = 0;
        }
        serverName = ipList.get(pos);
        //轮询+1
        pos++;

        return serverName;
    }

    /**
     * 随机数
     */
    @Test
    public void randomTest() {
        for (int i = 0; i < 10; i++) {
            String serverName = random();
            System.out.println(serverName);
        }
    }

    private String random() {

        List<String> ipList = new ArrayList<>(ipServerMap.keySet());

        //循环随机数
        Random random = new Random();
        //随机数在list数量中取（1-list.size）
        int index = random.nextInt(ipList.size());
        return ipList.get(index);
    }

    /**
     * 加权随机数
     */
    @Test
    public void robinRandomTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(robinRandom());
        }
    }

    private String robinRandom() {

        List<String> ipList = new ArrayList<>(3);

        for (String serverName : ipServerMap.keySet()) {
            Integer weight = ipServerMap.get(serverName);
            for (int i = 0; i < weight; i++) {
                ipList.add(serverName);
            }
        }

        //循环随机数
        Random random = new Random();
        //随机数在list数量中取（1-list.size）
        int pos = random.nextInt(ipList.size());
        return ipList.get(pos);
    }

    /**
     * ip_Hash算法
     */
    @Test
    public void ipHashTest() {
        System.out.println(ipHash("192.168.13.1"));
        System.out.println(ipHash("192.168.13.2"));
    }

    private String ipHash(String clientIp) {

        List<String> ipList = new ArrayList<>(ipServerMap.keySet());

        int hashCode = clientIp.hashCode();
        int size = ipList.size();
        int index = hashCode % size;
        return ipList.get(index);
    }

}
