package com.hanyi.daily.algorithm.hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 参考地址：https://www.jianshu.com/p/27578e130525
 *
 * @author wcwei@iflytek.com
 * @since 2022-03-29 16:11
 */
public class ConsistentHashingTest {

    @Test
    public void hashTest() {
        ConsistentHashing.Node[] nodes = new ConsistentHashing.Node[4];
        Map<ConsistentHashing.Node, List<String>> map = new HashMap<>();

        // make nodes 4台服务器节点
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new ConsistentHashing.Node("10.1.32.2" + i, 8070, "myNode" + i);
        }

        ConsistentHashing ch = new ConsistentHashing(nodes, 160);

        // make keys 100万个key
        String[] keys = new String[1_000_000];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = "key" + (i + 17) + "ss" + (i * 19);
        }

        // make results
        for (String key : keys) {
            ConsistentHashing.Node n = ch.getNode(key);
            List<String> list = map.computeIfAbsent(n, k -> new ArrayList<>());
            list.add(key);
        }

        // 统计标准差，评估服务器节点的负载均衡性
        int[] loads = new int[nodes.length];
        int x = 0;
        for (ConsistentHashing.Node key : map.keySet()) {
            List<String> list = map.get(key);
            loads[x++] = list.size();
        }
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int load : loads) {
            min = Math.min(min, load);
            max = Math.max(max, load);
        }
        System.out.println("最小值: " + min + "; 最大值: " + max);
        System.out.println("方差：" + variance(loads));
    }

    public static double variance(int[] data) {
        double variance = 0;
        double expect = (double) sum(data) / data.length;
        for (double datum : data) {
            variance += (Math.pow(datum - expect, 2));
        }
        variance /= data.length;
        return Math.sqrt(variance);
    }

    private static int sum(int[] data) {
        return IntStream.of(data).reduce(0,Integer::sum);
    }

    @Test
    public void hashCircleTest(){
        ConsistentHashing.Node[] nodes = new ConsistentHashing.Node[3];
        ConsistentHashing.Node[] newNodes = new ConsistentHashing.Node[4];

        // make nodes 4台服务器节点
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new ConsistentHashing.Node("10.1.32.2" + i, 8070, "myNode" + i);
        }

        for (int i = 0; i < newNodes.length; i++) {
            newNodes[i] = new ConsistentHashing.Node("10.1.32.2" + i, 8070, "myNode" + i);
        }

        ConsistentHashing ch = new ConsistentHashing(nodes, 5);
        ch.getHashCircle().forEach((k,v) -> System.out.println(k +":" + v));

        System.out.println("==========================");
        ConsistentHashing chTen = new ConsistentHashing(newNodes, 5);
        chTen.getHashCircle().forEach((k,v) -> System.out.println(k +":" + v));
    }

}
