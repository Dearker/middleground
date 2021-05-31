package com.hanyi.daily.algorithm.common;

import com.hanyi.daily.algorithm.pojo.Node;
import com.hanyi.daily.algorithm.pojo.TownAllLine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.hanyi.daily.algorithm.pojo.NodeAllLine.getPaths;

/**
 * <p>
 * 查询指定两节点之间所有路线
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/30 11:39 上午
 */
public class NodeAllLineTest {

    @Test
    public void nodeTest(){
        /* 定义节点关系 */
        String nodeRalation[][] = { { "0" }, // 0
                { "2" }, // 1
                { "1", "3" }, // 2
                { "2", "4", "9" }, // 3
                { "3", "5" }, // 4
                { "4" }, // 5
                { "9" }, // 6
                { "10", "8" }, // 7
                { "7", "11" }, // 8
                { "3", "6", "10" }, // 9
                { "9", "7", "11" }, // 10
                { "10", "8" } // 11
        };

        /* 定义节点数组 */
        Node[] node = new Node[nodeRalation.length];

        for (int i = 0; i < nodeRalation.length; i++) {
            node[i] = new Node();
            node[i].setName("node" + i);
        }

        /* 定义与节点相关联的节点集合 */
        for (int i = 0; i < nodeRalation.length; i++) {
            List<Node> List = new ArrayList<>();
            for (int j = 0; j < nodeRalation[i].length; j++) {
                for (int z = 0; z < nodeRalation.length; z++) {
                    if (node[z].getName().equals("node" + nodeRalation[i][j])) {
                        List.add(node[z]);
                        break;
                    }
                }
            }
            node[i].setRelationNodes(List);
        }

        /* 开始搜索所有路径 */
        getPaths(node[1], null, null, node[8]);
    }

    @Test
    public void twoNodeTest(){
        TownAllLine.getAllLineForNode('C','C');

    }

    @Test
    public void charTest(){
        List<Character> characterList = new ArrayList<>();
        characterList.add('A');

        char c = 'A';
        System.out.println(characterList.contains(c));
    }


}
