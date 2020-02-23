package com.hanyi.demo.common.design.builder.impl;

import com.hanyi.demo.common.design.builder.Student;
import com.hanyi.demo.common.design.builder.StudentBuilder;

/**
 * @PackAge: middleground com.hanyi.demo.common.design.builder.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-23 16:50
 * @Version: 1.0
 */
public class ManStudentBuilder implements StudentBuilder {

    private Student student;

    public ManStudentBuilder() {
        student = new Student();
    }

    @Override
    public void builderHead() {
        student.setHead("建造者头部分");
    }

    @Override
    public void builderBody() {
        student.setBody("建造者身体部分");
    }

    @Override
    public void builderFoot() {
        student.setFoot("建造者头四肢部分");
    }

    @Override
    public Student builderStudent() {
        return student;
    }
}
