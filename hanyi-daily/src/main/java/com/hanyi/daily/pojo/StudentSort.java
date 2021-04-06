package com.hanyi.daily.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生排序实体类
 *
 * @author wcwei@iflytek.com
 * @since 2021-04-06 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSort implements Comparable<StudentSort>{

    private Integer id;

    private String name;

    @Override
    public int compareTo(StudentSort studentSort) {
        return this.id.compareTo(studentSort.getId());
    }
}
