package com.hanyi.daily.load;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;

/**
 * 集合API测试类
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-21 15:13
 */
public class CollectionApiTest {

    /**
     * 将动态数组中的容量调整为数组中的元素个数
     * ensureCapacity(): 用于指定集合的容量大小，以减少扩容的次数
     */
    @Test
    public void trimToSizeTest() {
        // 创建一个动态数组
        ArrayList<String> sites = new ArrayList<>();

        sites.add("Google");
        sites.add("Runoob");
        sites.add("Taobao");

        System.out.println("ArrayList : " + sites + "---" + sites.size());

        // 调整容量为3
        sites.trimToSize();
        sites.ensureCapacity(3);
        System.out.println("ArrayList 大小: " + sites.size());
    }

    /**
     * 将指定元素插入此列表的开头：addFirst(E e); || offerFirst(E e);
     * 将指定元素添加到此列表的结尾：addLast(E e); || offerLast(E e);
     * <p>
     * 将指定元素添加到此列表的结尾：add(E e); || offer(E e);
     * 获取并移除此列表的头：poll(); || remove();
     * <p>
     * 移除此列表的第一个元素：removeFirst(); || pollFirst();
     * 移除此列表的最后一个元素：removeLast(); || pollLast();
     * Tips：此处带remove的方法表示移除并返回，带poll的方法表示获取并移除
     */
    @Test
    public void linkedListTest() {
        //创建存放int类型的linkedList
        LinkedList<Integer> linkedList = new LinkedList<>();
        /************************** linkedList的基本操作 ************************/
        // 向列表中添加元素
        linkedList.add(1);
        // 添加元素到列表开头
        linkedList.addFirst(0);
        linkedList.add(4);
        // 在指定位置添加元素
        linkedList.add(2, 2);
        // 添加元素到列表结尾
        linkedList.addLast(3);

        // 将此列表中指定位置的元素替换为指定的元素
        linkedList.set(3, 5);
        System.out.println("LinkedList（直接输出的）: " + linkedList);

        // 返回此列表中首次出现的指定元素的索引
        System.out.println("indexOf(3): " + linkedList.indexOf(3));
        // 返回此列表中最后出现的指定元素的索引
        System.out.println("lastIndexOf(3): " + linkedList.lastIndexOf(3));

        /************************** Queue操作 ************************/
        System.out.println("-----------------------------------------");
        System.out.println("peek(): " + linkedList.peek()); // 获取但不移除此列表的头(如果为空，返回null)
        System.out.println("element(): " + linkedList.element()); // 获取但不移除此列表的头(如果为空，抛异常)
        linkedList.poll(); // 获取并移除此列表的头
        System.out.println("After poll():" + linkedList);
        linkedList.remove();
        System.out.println("After remove():" + linkedList); // 获取并移除此列表的头
        linkedList.offer(4);
        System.out.println("After offer(4):" + linkedList); // 将指定元素添加到此列表的末尾
        System.out.println("从此列表所表示的堆栈处弹出一个元素：" + linkedList.pop());

        // 从此列表中移除第一次出现的指定元素
        linkedList.removeFirstOccurrence(3);
        System.out.println("从此列表中移除第一次出现的指定元素：" + linkedList);
        // 从此列表中移除最后一次出现的指定元素
        linkedList.removeLastOccurrence(4);
        System.out.println("从此列表中移除最后一次出现的指定元素：" + linkedList);
    }

    /**
     * mappingCount: 和size方法类似，但在多线程的情况下，使用该方法准确度较高，但任存在偏差
     * <p>
     * forEach：第一个参数parallelismThreshold的值越小并发线程越多，反之亦然，底层使用ForkJoinPool，乱序，
     * 当该值为Long.MAX_VALUE则会顺序执行；第二个参数是对key和value的值进行处理并返回一个新的值，
     * 第三个参数的值就是之前返回的新值
     * <p>
     * search: 执行一定的逻辑，返回不为null的对象，若元素全部遍历后仍没有符合条件的元素，则返回 null
     */
    @Test
    public void concurrentHashMapTest() {
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1, "aaa");
        concurrentHashMap.put(2, "bbb");
        concurrentHashMap.put(3, "ccc");

        System.out.println(concurrentHashMap.mappingCount());
        concurrentHashMap.forEach(0, (k, v) -> System.out.println(Thread.currentThread().getName() + ":" + k + v));
        concurrentHashMap.forEach(0, (k, v) -> {
            k += 1;
            return k + v;
        }, s -> {
            System.out.println("------------");
            System.out.println(s);
        });

        System.out.println("search---------------");
        //如果不返回null，则只会遍历一次并将结果返回，返回null，则全部遍历，如果没有符合条件的数据则最后返回null
        String search = concurrentHashMap.search(Long.MAX_VALUE, (k, v) -> k == 3 ? v : null);

        System.out.println("search 结果: " + search);
    }

    /**
     * reduce会通过提供的积累函数，将所有的键或指结合起来
     */
    @Test
    public void concurrentHashMapReduceTest() {
        ConcurrentHashMap<Integer, Long> longConcurrentHashMap = new ConcurrentHashMap<>();
        LongStream.range(0, 10).forEach(s -> longConcurrentHashMap.put(Math.toIntExact(s), s));

        Integer reduce = longConcurrentHashMap.reduce(3, (k, v) -> k, Integer::sum);
        //45
        System.out.println(reduce);

        int reduceKeysToInt = longConcurrentHashMap.reduceKeysToInt(0, k -> k > 2 ? k : 0, 0, Integer::sum);
        System.out.println("reduceKeysToInt 结果：" + reduceKeysToInt);

        Integer reduceKeys = longConcurrentHashMap.reduceKeys(0, k -> k > 5 ? k : null, Integer::sum);
        System.out.println("reduceKeys 结果" + reduceKeys);

        //第二个参数为转换器函数可以作为一个过滤器，通过返回null来排除不想要的输入
        Long reduceValues = longConcurrentHashMap.reduceValues(0, v -> v > 5 ? v : null, Long::sum);
        System.out.println("reduceValues 结果：" + reduceValues);
    }

    @Test
    public void treeMapTest() {
        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("1", "aaa");
        treeMap.put("2", "bbb");
        treeMap.put("3", "ccc");
        treeMap.put("4", "ddd");

        // 返回此map的逆序map
        System.out.println(treeMap.descendingMap());

        // 返回一个大于等于指定键的键值映射关系，若不存在这样的键则返回null
        System.out.println(treeMap.ceilingEntry("2"));

        //返回一个小于等于指定键的键值映射关系，若不存在这样的键则返回null
        System.out.println(treeMap.floorEntry("1"));

        //返回大于等于给定键的最小键，若不存在这样的键则返回null
        System.out.println(treeMap.ceilingKey("0"));

        //返回小于等于给定键的最小键，若不存在这样的键则返回null
        System.out.println(treeMap.floorKey("1"));

        System.out.println("--------------------------------------");

        //返回键值小于toKey的部分map,不包含toKey
        System.out.println(treeMap.headMap("4"));
        //返回键值小于toKey的部分map(inclusive为true时包括toKey)
        System.out.println(treeMap.headMap("4", true));

        System.out.println("--------------------------------------");

        //返回此map的部分map，其键大于等于fromKey
        System.out.println(treeMap.tailMap("2"));
        //返回此map的部分map，其键大于fromKey
        System.out.println(treeMap.tailMap("2", false));

        System.out.println("--------------------------------------");

        //返回一个entry，它大于给定键的最小键关联，若不存在这样的键则返回null
        System.out.println(treeMap.higherEntry("1"));

        //返回一个entry，它小于给定键的最小键关联，若不存在这样的键则返回null
        System.out.println(treeMap.lowerEntry("2"));

        //返回大于给定键的最小键，若不存在这样的键则返回null
        System.out.println(treeMap.higherKey("1"));

        //返回小于给定键的最小键，若不存在这样的键则返回null
        System.out.println(treeMap.lowerKey("2"));

        System.out.println("--------------------------------------");

        // 返回此map的部分map，其键值范围[fromKey, toKey)
        System.out.println(treeMap.subMap("1", "3"));

        // 返回此map的部分map，其键的范围从fromKey到toKey(inclusive为true时包括toKey)
        System.out.println(treeMap.subMap("1", Boolean.TRUE, "4", Boolean.TRUE));
    }

    @Test
    public void otherTreeMapTest() {
        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("1", "aaa");
        treeMap.put("2", "bbb");
        treeMap.put("3", "ccc");
        treeMap.put("4", "ddd");

        System.out.println("第一个key：" + treeMap.firstKey());
        System.out.println("最后一个key值：" + treeMap.lastKey());

        System.out.println("删除并返回第一个entry对象：" + treeMap.pollFirstEntry());
        System.out.println("删除并返回最后一个entry对象：" + treeMap.pollLastEntry());
    }

    /**
     * 以下对元素的操作，如果没有匹配的元素都会返回null
     */
    @Test
    public void treeSetTest() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("aaa");
        treeSet.add("ccc");
        treeSet.add("bbb");

        System.out.println("默认升序：" + treeSet);
        System.out.println("降序的set集合：" + treeSet.descendingSet());
        System.out.println("返回大于等于该元素的第一个集合：" + treeSet.ceiling("bbb"));
        System.out.println("获取第一个元素：" + treeSet.first());
        System.out.println("获取最后一个元素：" + treeSet.last());

        System.out.println("返回最大的元素小于或等于给定元素:" + treeSet.floor("bbb"));

        System.out.println("返回小于指定元素的所有元素:" + treeSet.headSet("bbb"));
        System.out.println("返回小于等于指定元素的所有元素:" + treeSet.headSet("bbb", true));

        System.out.println("返回大于给定元素的该集合中的最小元素：" + treeSet.higher("bbb"));
        System.out.println("返回这个集合中最大的元素小于给定的元素:" + treeSet.lower("bbb"));

        System.out.println("返回大于等于开始值并小于最后的值的所有集合：" + treeSet.subSet("aaa", "bbb"));
        System.out.println("返回大于等于开始值并小于等于截止值的所有集合：" +
                treeSet.subSet("aaa", true, "bbb", true));

        System.out.println("返回大于等于指定元素的所有元素：" + treeSet.tailSet("bbb"));
        System.out.println("返回大于指定元素的所有元素：" + treeSet.tailSet("bbb", false));
    }

}
