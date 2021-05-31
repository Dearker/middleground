package com.hanyi.daily.algorithm.pojo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>
 * https://www.jianshu.com/p/3d4b6dcfb59a
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/31 8:06 下午
 */
public class AllLine {

    /**
     * 所有路线集合
     */
    private static final int[][] LINE_GRAPH;

    /**
     * 节点集合
     */
    private static final List<Character> NODE_LIST;

    /**
     * 节点路线集合
     */
    private static final Map<Character, Set<Character>> NODE_LINE_MAP;

    /**
     * 路径长度集合
     */
    private static final Map<String, Integer> LINE_LENGTH_MAP;

    static {
        NODE_LIST = new ArrayList<>();
        NODE_LINE_MAP = new HashMap<>();
        LINE_LENGTH_MAP = new HashMap<>();
        Stream.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7").forEach(s -> {
            char start = s.charAt(0);
            char end = s.charAt(1);

            if (!NODE_LIST.contains(start)) {
                NODE_LIST.add(start);
            }
            if (!NODE_LIST.contains(end)) {
                NODE_LIST.add(end);
            }

            LINE_LENGTH_MAP.put(s.substring(0, 2), Integer.parseInt(s.substring(2)));
            NODE_LINE_MAP.computeIfAbsent(start, v -> new HashSet<>()).add(end);
        });

        NODE_LIST.sort(Comparator.naturalOrder());

        int nodeLength = NODE_LIST.size();
        LINE_GRAPH = new int[nodeLength][nodeLength];

        IntStream.range(0, nodeLength).forEach(s ->
                NODE_LINE_MAP.getOrDefault(NODE_LIST.get(s), Collections.emptySet())
                        .forEach(a -> LINE_GRAPH[s][NODE_LIST.indexOf(a)] = 1));
    }

    /**
     * 根据起始节点查询所有可能路线
     *
     * @param startNode 开始节点
     * @param endNode   结束节点
     * @return 返回所有可能路线
     */
    public static List<String> findResult(Character startNode, Character endNode) {
        //如果开始节点和结束节点相同，则查询出开节点的所有邻近节点
        int endIndex = NODE_LIST.indexOf(endNode);
        LineParam lineParam = new LineParam(NODE_LIST.indexOf(startNode), endIndex, new int[NODE_LIST.size()]);
        if (startNode.equals(endNode)) {
            NODE_LINE_MAP.getOrDefault(startNode, Collections.emptySet()).forEach(s -> {
                int indexOf = NODE_LIST.indexOf(s);
                lineParam.setStartIndex(indexOf);
                dfs(lineParam);
            });
            return lineParam.getAllLineList().stream().map(s -> startNode + s).collect(Collectors.toList());
        }
        dfs(lineParam);
        return lineParam.getAllLineList();
    }

    /**
     * 根据起始节点查询最短路径集合
     *
     * @param startNode 开始节点
     * @param endNode   结束节点
     * @return 返回最短路径集合
     */
    public static Pair<Integer, List<String>> findShortLine(Character startNode, Character endNode) {
        List<String> allLineList = findResult(startNode, endNode);
        Map<Integer, List<String>> shortLineMap = new HashMap<>(allLineList.size());
        CollUtil.emptyIfNull(allLineList).forEach(s -> {
            int lineCount = computeLineLength(s);
            shortLineMap.computeIfAbsent(lineCount, v -> new ArrayList<>()).add(s);
        });
        Integer shortLength = shortLineMap.keySet().stream().min(Comparator.naturalOrder()).orElse(-1);
        return new Pair<>(shortLength, shortLineMap.getOrDefault(shortLength, Collections.emptyList()));
    }

    /**
     * 计算路线总长度
     *
     * @param line 路线
     * @return 返回总长度
     */
    public static int computeLineLength(String line) {
        int lineCount = 0;
        for (int i = 0, length = line.length(); i < length; i++) {
            if (i + 2 <= length) {
                String substring = line.substring(i, i + 2);
                lineCount += LINE_LENGTH_MAP.getOrDefault(substring, 0);
            }
        }
        return lineCount;
    }

    /**
     * 查询所有可能路径
     *
     * @param lineParam 路线参数
     */
    private static void dfs(LineParam lineParam) {
        int startIndex = lineParam.getStartIndex();
        int endIndex = lineParam.getEndIndex();

        int[] visited = lineParam.getVisited();

        visited[startIndex] = 1;

        List<Character> pathList = lineParam.getPathList();
        List<String> allLineList = lineParam.getAllLineList();
        pathList.add(NODE_LIST.get(startIndex));
        if (startIndex == endIndex) {
            String nodeLine = pathList.stream().map(String::valueOf).collect(Collectors.joining(StrUtil.EMPTY));
            allLineList.add(nodeLine);
        } else {
            for (int i = 0; i < LINE_GRAPH.length; i++) {
                if (visited[i] == 0 && i != startIndex && LINE_GRAPH[startIndex][i] == 1) {
                    lineParam.setStartIndex(i);
                    dfs(lineParam);
                }
            }
        }
        pathList.remove(pathList.size() - 1);
        visited[startIndex] = 0;
    }

}
