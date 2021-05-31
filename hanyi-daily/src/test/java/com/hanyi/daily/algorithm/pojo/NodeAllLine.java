package com.hanyi.daily.algorithm.pojo;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 两节点之间的所有路线
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/30 11:32 上午
 */
public class NodeAllLine {

    /* 临时保存路径节点的栈 */
    private static final LinkedList<Node> nodeLinkedList = new LinkedList<>();

    /* 判断节点是否在栈中 */
    public static boolean isNodeInStack(Node node) {
         return nodeLinkedList.contains(node);
    }

    /* 此时栈中的节点组成一条所求路径，转储并打印输出 */
    public static void showAndSavePath() {
        System.out.println(nodeLinkedList.stream().map(Node::getName).collect(Collectors.joining("->")));
        System.out.println("\n");
    }

    /*
     * 寻找路径的方法 cNode: 当前的起始节点currentNode pNode: 当前起始节点的上一节点previousNode sNode:
     * 最初的起始节点startNode eNode: 终点endNode
     */
    public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode) {
        /* 如果符合条件判断说明出现环路，不能再顺着该路径继续寻路，返回false */
        if (Objects.equals(pNode,cNode)) {
            return false;
        }

        if (cNode != null) {
            /* 起始节点入栈 */
            nodeLinkedList.push(cNode);
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
                Node nNode = cNode.getRelationNodes().get(i);
                while (nNode != null) {
                    /*
                     * 如果nNode是最初的起始节点或者nNode就是cNode的上一节点或者nNode已经在栈中 ， 说明产生环路
                     * ，应重新在与当前起始节点有连接关系的节点集中寻找nNode
                     */
                    if (pNode != null && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {
                        i++;
                        if (i >= cNode.getRelationNodes().size())
                            nNode = null;
                        else
                            nNode = cNode.getRelationNodes().get(i);
                        continue;
                    }
                    /* 以nNode为新的起始节点，当前起始节点cNode为上一节点，递归调用寻路方法 */
                    if (getPaths(nNode, cNode, sNode, eNode)) {
                        /* 如果找到一条路径，则弹出栈顶节点 */
                        nodeLinkedList.pop();
                    }
                    /* 继续在与cNode有连接关系的节点集中测试nNode */
                    i++;
                    if (i >= cNode.getRelationNodes().size()) {
                        nNode = null;
                    } else {
                        nNode = cNode.getRelationNodes().get(i);
                    }
                }
                /*
                 * 当遍历完所有与cNode有连接关系的节点后， 说明在以cNode为起始节点到终点的路径已经全部找到
                 */
                nodeLinkedList.pop();
                return false;
            }
        } else {
            return false;
        }
    }

}
