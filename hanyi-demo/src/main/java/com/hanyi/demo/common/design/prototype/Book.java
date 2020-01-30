package com.hanyi.demo.common.design.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: middleground com.hanyi.demo.common.design.prototype Book
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-01-23 16:23
 * @Version: 1.0
 */
public class Book implements Cloneable {

    /**
     * 标题
     */
    private String title;
    /**
     * 图片名列表
     */
    private ArrayList<String> imageList = new ArrayList<>();

    public Book() {
        super();
    }

    @Override
    protected Book clone() {
        try {
            Book book = (Book) super.clone();
            //深复制
            book.imageList = (ArrayList<String>) this.imageList.clone();
            return book;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getImage() {
        return imageList;
    }

    public void addImage(String img) {
        this.imageList.add(img);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void showBook() {

        System.out.println("----------------------Start--------------------");

        System.out.println("title：" + title);
        imageList.forEach(System.out::println);

        System.out.println("----------------------End----------------------");
    }


}
