package com.hanyi.demo.common.design.builder;

import com.hanyi.demo.common.design.builder.impl.ManStudentBuilder;

/**
 * @PackAge: middleground com.hanyi.demo.common.design.builder.impl
 * @Author: weiwenchang
 * @Description: java类作用描述
 * @CreateDate: 2020-02-23 16:54
 * @Version: 1.0
 */
public class StudentDirector {

    private Student constructStudent(StudentBuilder studentBuilder) {
        studentBuilder.builderHead();
        studentBuilder.builderBody();
        studentBuilder.builderFoot();
        return studentBuilder.builderStudent();
    }

    public static void main(String[] args) {
        StudentDirector studentDirector = new StudentDirector();
        Student student = studentDirector.constructStudent(new ManStudentBuilder());
        System.out.println(student);
    }

}
