package com.hanyi.daily.algorithm.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import java.util.Objects;

/**
 * <p>
 * 单向链表算法
 * </p>
 *
 * @author wenchangwei
 * @since 10:26 下午 2020/7/22
 */
public class SingleLinkedList {

    @Test
    public void demoTest() {
        SingleNode singleNode = new SingleNode(0,"");
        singleNode.add(new SingleNode(1, "1"));
        singleNode.add(new SingleNode(2, "2"));
        singleNode.add(new SingleNode(3, "3"));

        singleNode.list();
        System.out.println("-----------------");

        singleNode.update(new SingleNode(2, "4"));
        singleNode.delete(2);
        singleNode.list();

        System.out.println("-----------------");
        singleNode.reverseList(singleNode);
        singleNode.list();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    static class SingleNode {

        private static final SingleNode head = new SingleNode(0, "");

        @Getter
        private Integer id;

        @Getter
        @Setter
        private String name;

        private SingleNode next;

        public SingleNode(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * 添加元素
         *
         * @param singleNode 元素
         */
        public void add(SingleNode singleNode) {
            SingleNode temp = head;
            //为空则表示以到最后一个元素
            while (Objects.nonNull(temp.next)) {
                temp = temp.next;
            }
            temp.next = singleNode;
        }

        /**
         * 遍历链表
         */
        public void list() {
            //链表为空
            if (Objects.isNull(head.next)) {
                System.out.println("链表为空");
                return;
            }

            SingleNode temp = head.next;
            while (Objects.nonNull(temp)) {
                System.out.println(temp);
                temp = temp.next;
            }
        }

        /**
         * 更新链表中的数据
         *
         * @param singleNode 元素
         */
        public void update(SingleNode singleNode) {
            SingleNode temp = head.next;
            boolean flag = false;
            while (true) {
                if (Objects.isNull(temp)) {
                    break;
                }
                if (temp.id.equals(singleNode.id)) {
                    temp.name = singleNode.getName();
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (!flag) {
                System.out.println("要修改的数据不存在");
            }
        }

        public void delete(int id) {
            SingleNode temp = head.next;
            boolean flag = false;
            while (true) {
                if (Objects.isNull(temp)) {
                    break;
                }
                if (temp.next.id.equals(id)) {
                    temp.next = temp.next.next;
                    flag = true;
                    break;
                }
                temp = temp.next;
            }

            if (!flag) {
                System.out.println("要修改的数据不存在");
            }
        }

        public void reverseList(SingleNode singleNode){
            if(singleNode.next == null || singleNode.next.next == null){
                return;
            }

            SingleNode cur = singleNode.next;
            SingleNode next;

            SingleNode reverseHead = new SingleNode();
            while (cur != null){
                next = cur.next;
                cur.next = reverseHead.next;
                reverseHead.next = cur;
                cur = next;
            }
            singleNode.next = reverseHead.next;
        }

        @Override
        public String toString() {
            return "SingleNode{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
