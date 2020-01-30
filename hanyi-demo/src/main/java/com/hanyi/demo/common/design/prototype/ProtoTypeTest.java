package com.hanyi.demo.common.design.prototype;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.prototype ProtoTypeTest
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:38
 * @Version: 1.0
 */
public class ProtoTypeTest {

    public static void main(String[] args) {

        Book book1 = new Book();
        book1.setTitle("书1");
        book1.addImage("图1");
        book1.showBook();
        //以原型方式拷貝一份
        System.out.println("-------------");
        Book book2 = book1.clone();
        book2.showBook();
        book2.setTitle("书2");
        book2.addImage("圖2");
        book2.showBook();
        //再次还原打印书本
        book1.showBook();

    }

}
