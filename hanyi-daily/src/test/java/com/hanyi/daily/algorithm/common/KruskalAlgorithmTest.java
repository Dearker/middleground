package com.hanyi.daily.algorithm.common;

import com.hanyi.daily.algorithm.pojo.VillageLine;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>
 * Kruskal 算法，用于解决最小生成树问题，路线有多少条就会遍历多少次
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/29 10:03 上午
 */
public class KruskalAlgorithmTest {

    @Test
    public void kruskalTest() {
        //初始化路线数据
        List<VillageLine> villageLineList = new ArrayList<>();
        Set<Character> countSet = new HashSet<>();
        Stream<String> testStream = Stream.of("AB5", "AG2", "AC7", "CE8", "BD9", "GE4", "GF6", "GB3", "EF5", "DF4");
        testStream.forEach(s -> {
            char start = s.charAt(0);
            char end = s.charAt(1);
            countSet.add(start);
            countSet.add(end);

            int weight = Integer.parseInt(s.substring(2));
            villageLineList.addAll(Arrays.asList(new VillageLine(start, end, weight), new VillageLine(end, start, weight)));
        });
        villageLineList.sort(Comparator.comparingInt(VillageLine::getWeight));

        //节点集合
        List<Character> characterList = countSet.stream().sorted().collect(Collectors.toList());

        //获取统计的节点总数
        int size = countSet.size();
        List<VillageLine> finallyLineList = new ArrayList<>(size);

        int lineCount = villageLineList.size() / 2;
        //用于保存"已有最小生成树"中的每个顶点在最小生成树中的终点
        int[] ends = new int[lineCount];

        //循环将边添加到最小生成树中时，判断是准备加入的边否形成了回路，如果没有，就加入路线集合中,否则不能加入
        IntStream.range(0, lineCount).forEach(s -> {
            VillageLine villageLine = villageLineList.get(s);

            //获取到第i条边的第一个顶点(起点)
            int start = characterList.indexOf(villageLine.getStartVillage());
            //获取到第i条边的第2个顶点
            int origin = characterList.indexOf(villageLine.getOriginVillage());

            //获取start这个顶点在已有最小生成树中的终点
            int startEnd = getEnd(ends, start);
            ////获取origin这个顶点在已有最小生成树中的终点
            int originEnd = getEnd(ends, origin);

            //如果不相等则没有构成回路，反之则构成回路
            if (startEnd != originEnd) {
                //设置startEnd在"已有最小生成树"中的终点
                ends[startEnd] = originEnd;
                //添加符合条件的路线到集合中
                finallyLineList.add(villageLine);
                //删除首尾节点相同的数据
                villageLineList.removeIf(a -> Objects.equals(a.getStartVillage(), villageLine.getOriginVillage()) &&
                        Objects.equals(a.getOriginVillage(), villageLine.getStartVillage()));
            }
        });

        finallyLineList.forEach(System.out::println);
    }

    /**
     * 获取下标为i的顶点的终点(),用于后面判断两个顶点的终点是否相同
     *
     * @param ends 数组就是记录了各个顶点对应的终点是哪个,ends数组是在遍历过程中，逐步形成
     * @param i    表示传入的顶点对应的下标
     * @return 下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

}
