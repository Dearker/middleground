package com.hanyi.daily.algorithm.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2021/5/30 11:35 上午
 */
@Data
public class Node {

    private String name;

    private List<Node> relationNodes = new ArrayList<>();

}
