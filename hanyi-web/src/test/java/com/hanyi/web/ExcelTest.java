package com.hanyi.web;

import com.alibaba.excel.EasyExcel;
import com.hanyi.web.bo.Student;
import com.hanyi.web.common.listener.ExcelListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * excel测试类
 * </p>
 *
 * @author wenchangwei
 * @since 8:44 下午 2020/11/23
 */
public class ExcelTest {

    private static final List<Student> STUDENT_LIST = new ArrayList<>(Byte.SIZE);

    static {
        for (int i = 0; i < 8; i++) {
            STUDENT_LIST.add(new Student(i, "柯基-" + i));
        }
    }

    /**
     * 向Excel中写入数据
     */
    @Test
    public void writeTest() {
        String fileName = "/Volumes/husky/test/1.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Student.class).sheet("写入方法一").doWrite(STUDENT_LIST);
    }

    /**
     * 读取Excel中的数据
     */
    @Test
    public void readTest() {
        String fileName = "/Volumes/husky/test/1.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, Student.class, new ExcelListener()).sheet().doRead();
    }


}
