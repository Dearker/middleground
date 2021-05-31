package com.hanyi.daily.algorithm.common;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 贪心算法测试类
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/25 9:38 下午
 */
public class GreedyAlgorithmTest {

    @Test
    public void greedyTest() {
        //创建广播电台,放入到Map
        Map<String, Set<String>> broadcasts = new HashMap<>();
        broadcasts.put("K1", Sets.newHashSet("北京", "上海", "天津"));
        broadcasts.put("K2", Sets.newHashSet("广州", "北京", "深圳"));
        broadcasts.put("K3", Sets.newHashSet("成都", "上海", "杭州"));
        broadcasts.put("K4", Sets.newHashSet("上海", "天津"));
        broadcasts.put("K5", Sets.newHashSet("杭州", "大连"));

        //所有地区
        Set<String> allAreas = broadcasts.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

        //创建ArrayList,存放选择的电台集合
        List<String> selects = new ArrayList<>();

        //定义一个临时的集合，在遍历的过程中，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        Set<String> tempSet = new HashSet<>();

        //定义给maxKey，保存在一次遍历过程中，能够覆盖最大未覆盖的地区对应的电台的key
        //如果maxKey不为null,则会加入到selects
        String maxKey;
        //如果allAreas不为0,则表示还没有覆盖到所有的地区
        while (!allAreas.isEmpty()) {
            //每进行一次while,需要
            maxKey = null;

            //遍历broadcasts,取出对应key
            for (Map.Entry<String, Set<String>> entry : broadcasts.entrySet()) {
                tempSet.clear();
                //当前这个key能够覆盖的地区
                tempSet.addAll(entry.getValue());
                //求出tempSet和allAreas集合的交集,交集会赋给tempSet
                tempSet.retainAll(allAreas);
                //如果当前这个集合包含的未覆盖地区的数量，比maxKey指向的集合地区还多
                //就需要重置maxKey
                // tempSet.size() >broadcasts.get(maxKey).size()) 体现出贪心算法的特点,每次都选择最优的
                if (!tempSet.isEmpty() &&
                        (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())) {
                    maxKey = entry.getKey();
                }
            }

            //maxKey != null, 就应该将maxKey加入selects
            if (maxKey != null) {
                selects.add(maxKey);
                //将maxKey指向的广播电台覆盖的地区，从allAreas去掉
                allAreas.removeAll(broadcasts.get(maxKey));
                broadcasts.remove(maxKey);
            }
        }

        System.out.println("得到的选择结果是" + selects);//[K1,K2,K3,K5]
    }

}
