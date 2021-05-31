package com.hanyi.daily.algorithm.common;

import cn.hutool.core.collection.CollUtil;
import com.hanyi.daily.algorithm.pojo.VillageLine;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * prim算法，用于解决最小生成树问题
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/26 10:26 下午
 */
public class PrimAlgorithmTest {

    @Test
    public void primTest() {
        //初始化路线数据
        List<VillageLine> villageLineList = new ArrayList<>();
        Set<Character> countSet = new HashSet<>();
        //测试数据
        Stream<String> dataStream = Stream.of("AB12", "AF16", "AG14", "BF7", "GF9", "BC10", "GE8", "CF6", "EF2", "CE5", "DE4", "CD3");
        Stream<String> testStream = Stream.of("AB5", "AG2", "AC7", "CE8", "BD9", "GE4", "GF6", "GB3", "EF5", "DF4");
        testStream.forEach(s -> {
            char start = s.charAt(0);
            char end = s.charAt(1);
            countSet.add(start);
            countSet.add(end);

            int weight = Integer.parseInt(s.substring(2));
            villageLineList.addAll(Arrays.asList(new VillageLine(start, end, weight), new VillageLine(end, start, weight)));
        });

        //根据节点进行分组
        Map<Character, List<VillageLine>> characterListMap = villageLineList.stream().collect(
                Collectors.groupingBy(VillageLine::getStartVillage));

        //获取统计的节点总数
        int size = countSet.size();

        List<VillageLine> finallyLineList = new ArrayList<>(size);
        //从A节点开始
        Character character = countSet.stream().findFirst().orElse(Character.MIN_VALUE);
        Set<Character> characterSet = CollUtil.newHashSet(character);

        //每次获取到最小路径之后修改对应的单个或双个节点的状态为已访问，
        //如果当前查询的最小路径的首尾节点都已经存在characterSet则直接跳过
        while (characterSet.size() != size) {
            characterListMap.keySet().stream().filter(characterSet::contains).map(characterListMap::get).flatMap(Collection::stream)
                    .filter(v -> !characterSet.containsAll(Arrays.asList(v.getStartVillage(), v.getOriginVillage())))
                    .min(Comparator.comparingInt(VillageLine::getWeight)).map(a -> {
                characterSet.addAll(Arrays.asList(a.getStartVillage(), a.getOriginVillage()));
                finallyLineList.add(a);
                this.changeVisited(a, characterListMap);
                return a;
            });
        }

        finallyLineList.forEach(System.out::println);
        System.out.println(characterSet.size());
        finallyLineList.sort(Comparator.comparingInt(VillageLine::getWeight));
        System.out.println("排序后：");
        finallyLineList.forEach(System.out::println);
    }

    /**
     * 修改节点是否访问，同时修改双向的节点属性
     *
     * @param villageLine      当前节点
     * @param characterListMap 节点集合map
     */
    private void changeVisited(VillageLine villageLine, Map<Character, List<VillageLine>> characterListMap) {
        villageLine.setVisited(true);
        characterListMap.getOrDefault(villageLine.getOriginVillage(), Collections.emptyList())
                .stream().filter(s -> Objects.equals(s.getOriginVillage(), villageLine.getStartVillage()))
                .forEach(a -> a.setVisited(true));
        //删除已经访问的节点
        characterListMap.values().forEach(s -> s.removeIf(VillageLine::isVisited));
    }

}
