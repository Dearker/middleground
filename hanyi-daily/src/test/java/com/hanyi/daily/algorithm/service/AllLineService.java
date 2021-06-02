package com.hanyi.daily.algorithm.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.hanyi.daily.algorithm.pojo.LineParam;
import com.hanyi.daily.algorithm.pojo.MaxDistanceParam;
import com.hanyi.daily.algorithm.pojo.MaxLineParam;
import com.hanyi.daily.algorithm.pojo.ShortLineParam;
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
public class AllLineService {

    /**
     * 所有路线集合
     */
    private static final int[][] LINE_GRAPH;

    /**
     * 节点集合
     */
    private static final List<String> NODE_LIST;

    /**
     * 节点路线集合
     */
    private static final Map<String, Set<String>> NODE_LINE_MAP;

    /**
     * 路径长度集合
     */
    private static final Map<String, Integer> LINE_LENGTH_MAP;

    static {
        NODE_LIST = new ArrayList<>();
        NODE_LINE_MAP = new HashMap<>();
        LINE_LENGTH_MAP = new HashMap<>();
        Stream.of("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7").forEach(s -> {
            String start = String.valueOf(s.charAt(0));
            String end = String.valueOf(s.charAt(1));

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
    public static List<String> findResult(String startNode, String endNode) {
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
    public static Pair<Integer, List<String>> findShortLine(String startNode, String endNode) {
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

        List<String> pathList = lineParam.getPathList();
        pathList.add(NODE_LIST.get(startIndex));
        if (startIndex == endIndex) {
            String nodeLine = pathList.stream().map(String::valueOf).collect(Collectors.joining(StrUtil.EMPTY));
            lineParam.getAllLineList().add(nodeLine);
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

    /**
     * 查询小于等于指定距离的路线总数
     *
     * @param maxLineParam 最大路线参数
     * @return 返回路线总数
     */
    public static int maxDfs(MaxLineParam maxLineParam) {
        String start = maxLineParam.getStart();
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(start, Collections.emptySet());

        int weight = maxLineParam.getWeight();
        int maxDistance = maxLineParam.getMaxDistance();
        int routeTotal = 0;
        for (String character : nodeLineSet) {
            String s = start + character;
            int length = LINE_LENGTH_MAP.getOrDefault(s, 0);

            weight += length;
            if (weight <= maxDistance) {
                maxLineParam.setWeight(weight);
                maxLineParam.setStart(character);
                if (character.equals(maxLineParam.getEnd())) {
                    routeTotal++;
                    routeTotal += maxDfs(maxLineParam);
                } else {
                    routeTotal += maxDfs(maxLineParam);
                    weight -= length;
                }
            } else {
                weight -= length;
            }
        }
        return routeTotal;
    }

    /**
     * https://www.itdaan.com/blog/2016/04/30/a853438cd534.html
     *
     * @param lmtStops
     * @param roundCity
     * @return
     */
    public static int solution6(int lmtStops, String roundCity, String endCity) {
        return logicForSolution6(lmtStops, endCity, 0, roundCity, roundCity, 0);
    }

    private static int logicForSolution6(int lmtStops, String roundCtyNm, int count, String nextNode, String logRoute, int result) {
        count++;
        if (count > lmtStops) {
            return result;
        }
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(nextNode, Collections.emptySet());
        for (String character : nodeLineSet) {
            String buffer = logRoute + character;
            if (character.equals(roundCtyNm)) {
                System.out.println(buffer);
                result++;
            } else {
                result = logicForSolution6(lmtStops, roundCtyNm, count, character, buffer, result);
            }
        }
        return result;
    }

    public static int solution7(int exactlyStops, String fromCity, String toCityNm) {
        return logicForSolution7(exactlyStops, toCityNm, 0, fromCity, fromCity, 0);
    }

    private static int logicForSolution7(int exactlyStops, String toCityNm, int count, String city, String logRoute, int result) {
        count++;
        if (count > exactlyStops) {
            return result;
        }
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(city, Collections.emptySet());
        for (String character : nodeLineSet) {
            String buffer = logRoute + character;
            if (character.equals(toCityNm)) {
                if (count == exactlyStops) {
//System.out.println(buffer);
                    result++;
                }
            }
            result = logicForSolution7(exactlyStops, toCityNm, count, character, buffer, result);
        }
        return result;
    }

    /**
     * 符合最大距离的所有路线总和
     *
     * @param maxDistance 最大距离
     * @param fromCity 起点
     * @param toCityNm 终点
     * @return 返回路线总共
     */
    public static int solution10(int maxDistance, String fromCity, String toCityNm) {
        MaxDistanceParam maxDistanceParam = MaxDistanceParam.builder()
                .maxDistance(maxDistance)
                .startNode(fromCity)
                .originNode(toCityNm)
                .bufferDistance(0)
                .bufferLine(fromCity)
                .resultLineList(new ArrayList<>())
                .build();
        logicForSolution10(maxDistanceParam);

        System.out.println(maxDistanceParam.getResultLineList().size()+"---111111");

        return logicForSolution10(maxDistance, toCityNm, 0, fromCity, fromCity, 0);
    }

    private static int logicForSolution10(int maxDistance, String toCityNm, int distance, String fromCity,
                                          String logRoute, int result) {
        if (distance > maxDistance) {
            return result;
        }
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(fromCity, Collections.emptySet());
        for (String nodeName : nodeLineSet) {
            String buffer = logRoute + nodeName;
            //截取后两位的字符串，即当前路线
            String substring = buffer.substring(buffer.length() - 2);
            int distanceBuffer = distance + LINE_LENGTH_MAP.getOrDefault(substring, 0);
            if (nodeName.equals(toCityNm) && distanceBuffer < maxDistance) {
                result++;
                System.out.println(buffer + "," + distanceBuffer + ", " + maxDistance);
            }
            result = logicForSolution10(maxDistance, toCityNm, distanceBuffer, nodeName, buffer, result);
        }
        return result;
    }

    private static int logicForSolution10(MaxDistanceParam maxDistanceParam) {
        int maxDistance = maxDistanceParam.getMaxDistance();
        int distance = maxDistanceParam.getBufferDistance();

        int resultTotal = maxDistanceParam.getResultTotal();
        if (distance > maxDistance) {
            return resultTotal;
        }

        String startNode = maxDistanceParam.getStartNode();
        String originNode = maxDistanceParam.getOriginNode();
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(startNode, Collections.emptySet());
        for (String nodeName : nodeLineSet) {
            String buffer = maxDistanceParam.getBufferLine() + nodeName;
            //截取后两位的字符串，即当前路线
            String substring = buffer.substring(buffer.length() - 2);
            int distanceBuffer = distance + LINE_LENGTH_MAP.getOrDefault(substring, 0);
            if (nodeName.equals(originNode) && distanceBuffer < maxDistance) {
                maxDistanceParam.getResultLineList().add(buffer);
                resultTotal++;
                System.out.println(buffer + "," + distanceBuffer + ", " + maxDistance);
            }
            maxDistanceParam.setBufferLine(buffer).setBufferDistance(distanceBuffer).setStartNode(nodeName);
            resultTotal = logicForSolution10(maxDistanceParam);
        }
        return resultTotal;
    }


    /**
     * 查询最短路径距离
     *
     * @param fromCity 起点
     * @param toCityNm 终点
     * @return 返回最短距离
     */
    public static int solution8_9(String fromCity, String toCityNm) {
        TreeMap<Integer, List<String>> resultMap = new TreeMap<>();
        //logicForSolution8_9(toCityNm, 0, fromCity, fromCity, resultMap);

        ShortLineParam shortLineParam = ShortLineParam.builder()
                .startNode(fromCity)
                .bufferLine(fromCity)
                .originNode(toCityNm)
                .resultMap(resultMap)
                .build();

        logicForSolution8_9(shortLineParam);
        System.out.println(resultMap);
        return resultMap.firstKey();
    }

    private static void logicForSolution8_9(String toCityNm, String fromCity, String logRoute,
                                            Map<Integer, List<String>> resultMap) {
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(fromCity, Collections.emptySet());
        for (String nodeName : nodeLineSet) {
            String buffer = logRoute + nodeName;
            if (nodeName.equals(toCityNm)) {
                resultMap.computeIfAbsent(computeLineLength(buffer), v -> new ArrayList<>()).add(buffer);
            }
            if (!logRoute.contains(nodeName)) {
                logicForSolution8_9(toCityNm, nodeName, buffer, resultMap);
            }
        }
    }

    private static void logicForSolution8_9(ShortLineParam shortLineParam) {
        String startNode = shortLineParam.getStartNode();
        String originNode = shortLineParam.getOriginNode();
        String bufferLine = shortLineParam.getBufferLine();
        Map<Integer, List<String>> resultMap = shortLineParam.getResultMap();

        //获取对应节点的所有邻近节点
        Set<String> nodeLineSet = NODE_LINE_MAP.getOrDefault(startNode, Collections.emptySet());
        for (String nodeName : nodeLineSet) {
            String buffer = bufferLine + nodeName;
            if (nodeName.equals(originNode)) {
                resultMap.computeIfAbsent(computeLineLength(buffer), v -> new ArrayList<>()).add(buffer);
            }
            if (!bufferLine.contains(nodeName)) {
                shortLineParam.setBufferLine(buffer).setStartNode(nodeName);
                logicForSolution8_9(shortLineParam);
            }
        }
    }

}
