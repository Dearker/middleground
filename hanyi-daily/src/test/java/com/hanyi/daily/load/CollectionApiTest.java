package com.hanyi.daily.load;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

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

    @Test
    public void concurrentHashMapTest() {

        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1, "aaa");
        concurrentHashMap.put(2, "bbb");
        concurrentHashMap.put(3, "ccc");

        System.out.println(concurrentHashMap.mappingCount());
        concurrentHashMap.forEach(5, (k, v) -> System.out.println(Thread.currentThread().getName() + ":" + k + v));

        Integer reduce = concurrentHashMap.reduce(3, (k, v) -> k, Integer::sum);
        //6
        System.out.println(reduce);
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
        System.out.println(treeMap.subMap("1","3"));

        // 返回此map的部分map，其键的范围从fromKey到toKey(inclusive为true时包括toKey)
        System.out.println(treeMap.subMap("1", Boolean.TRUE, "4", Boolean.TRUE));
    }

}
