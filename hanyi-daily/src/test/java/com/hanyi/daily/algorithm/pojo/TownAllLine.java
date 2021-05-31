package com.hanyi.daily.algorithm.pojo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 两城市之间所有路线
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/30 3:18 下午
 */
public class TownAllLine {

    /**
     * 临时保存路径节点的栈
     */
    private static final LinkedList<Character> charLinkedList = new LinkedList<>();

    /**
     * 所有节点对应路线集合
     */
    private static Map<Character, List<Character>> lineMap;

    static {
        //初始化数据
        lineMap = Stream.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7").map(s -> {
            char start = s.charAt(0);
            char end = s.charAt(1);

            int weight = Integer.parseInt(s.substring(2));
            return new VillageLine(start, end, weight);
        }).collect(Collectors.groupingBy(VillageLine::getStartVillage,
                Collectors.mapping(VillageLine::getOriginVillage, Collectors.toList())));
    }

    /***
     * 判断节点是否在栈中
     *
     * @param c 节点名称
     * @return 返回判断结果
     */
    private static boolean isCharInStack(Character c) {
        return charLinkedList.contains(c);
    }

    public static List<String> getAllLineForNode(Character start,Character end){

        lineMap.getOrDefault(start,Collections.emptyList()).forEach(s -> getAllLine(s,null,null,end));

        /*GraphParam graphParam = GraphParam.builder().cNode(start).eNode(end).allLineList(new ArrayList<>(8)).build();
        getAllLine(graphParam);*/
        return null;
    }

    private static boolean getAllLine(GraphParam graphParam){
        Character pNode = graphParam.getPNode();
        Character cNode = graphParam.getCNode();
        /* 如果符合条件判断说明出现环路，不能再顺着该路径继续寻路，返回false */
        boolean firstFlag = graphParam.isFirstFlag();
        if (Objects.equals(pNode,cNode)) {
            if(firstFlag){
                return false;
            }
            graphParam.setFirstFlag(true);
        }

        if (cNode != null) {
            /* 起始节点入栈 */
            charLinkedList.push(cNode);
            Character eNode = graphParam.getENode();
            /* 如果该起始节点就是终点，说明找到一条路径 */
            if (cNode == eNode && firstFlag) {
                /* 转储并打印输出该路径，返回true */
                showAndSavePath();
                return true;
            }
            /* 如果不是,继续寻路 */
            else {
                /*
                 * 从与当前起始节点cNode有连接关系的节点集中按顺序遍历得到一个节点 作为下一次递归寻路时的起始节点
                 */
                int i = 0;
                List<Character> characterList = lineMap.get(cNode);
                Character nNode = characterList.get(i);
                while (nNode != null) {
                    /*
                     * 如果nNode是最初的起始节点或者nNode就是cNode的上一节点或者nNode已经在栈中 ， 说明产生环路
                     * ，应重新在与当前起始节点有连接关系的节点集中寻找nNode
                     */
                    Character sNode = graphParam.getSNode();
                    if (pNode != null && (nNode == sNode || nNode == pNode || isCharInStack(nNode))) {
                        i++;
                        if (i >= characterList.size())
                            nNode = null;
                        else
                            nNode = characterList.get(i);
                        continue;
                    }
                    /* 以nNode为新的起始节点，当前起始节点cNode为上一节点，递归调用寻路方法 */
                    graphParam.setCNode(nNode);
                    graphParam.setPNode(cNode);
                    if (getAllLine(graphParam)) {
                        /* 如果找到一条路径，则弹出栈顶节点 */
                        charLinkedList.pop();
                    }
                    /* 继续在与cNode有连接关系的节点集中测试nNode */
                    i++;
                    if (i >= characterList.size()) {
                        nNode = null;
                    } else {
                        nNode = characterList.get(i);
                    }
                }
                /*
                 * 当遍历完所有与cNode有连接关系的节点后， 说明在以cNode为起始节点到终点的路径已经全部找到
                 */
                System.out.println(characterList);
                charLinkedList.pop();
                return false;
            }
        } else {
            return false;
        }

    }

    private static boolean getAllLine(Character cNode, Character pNode, Character sNode, Character eNode){
        /* 如果符合条件判断说明出现环路，不能再顺着该路径继续寻路，返回false */
        if (Objects.equals(pNode,cNode)) {
            return false;
        }

        if (cNode != null) {
            /* 起始节点入栈 */
            charLinkedList.push(cNode);
            /* 如果该起始节点就是终点，说明找到一条路径 */
            if (cNode == eNode) {
                /* 转储并打印输出该路径，返回true */
                showAndSavePath();
                return true;
            }
            /* 如果不是,继续寻路 */
            else {
                /*
                 * 从与当前起始节点cNode有连接关系的节点集中按顺序遍历得到一个节点 作为下一次递归寻路时的起始节点
                 */
                int i = 0;
                List<Character> characterList = lineMap.get(cNode);
                Character nNode = characterList.get(i);
                while (nNode != null) {
                    /*
                     * 如果nNode是最初的起始节点或者nNode就是cNode的上一节点或者nNode已经在栈中 ， 说明产生环路
                     * ，应重新在与当前起始节点有连接关系的节点集中寻找nNode
                     */
                    if (pNode != null && (nNode == sNode || nNode == pNode || isCharInStack(nNode))) {
                        i++;
                        if (i >= characterList.size())
                            nNode = null;
                        else
                            nNode = characterList.get(i);
                        continue;
                    }
                    /* 以nNode为新的起始节点，当前起始节点cNode为上一节点，递归调用寻路方法 */
                    if (getAllLine(nNode, cNode, sNode, eNode)) {
                        /* 如果找到一条路径，则弹出栈顶节点 */
                        charLinkedList.pop();
                    }
                    /* 继续在与cNode有连接关系的节点集中测试nNode */
                    i++;
                    if (i >= characterList.size()) {
                        nNode = null;
                    } else {
                        nNode = characterList.get(i);
                    }
                }
                /*
                 * 当遍历完所有与cNode有连接关系的节点后， 说明在以cNode为起始节点到终点的路径已经全部找到
                 */
                charLinkedList.pop();
                return false;
            }
        } else {
            return false;
        }
    }

    /* 此时栈中的节点组成一条所求路径，转储并打印输出 */
    private static void showAndSavePath() {
        System.out.println(charLinkedList.stream().map(String::valueOf).collect(Collectors.joining("->")));
        System.out.println("");
    }

}
