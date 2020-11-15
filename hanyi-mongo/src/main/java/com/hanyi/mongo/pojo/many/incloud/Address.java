package com.hanyi.mongo.pojo.many.incloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author weiwen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = -2987074634893936920L;

    private String street;
    private String city;
    private String zip;
}