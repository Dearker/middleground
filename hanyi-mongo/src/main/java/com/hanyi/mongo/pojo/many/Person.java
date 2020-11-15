package com.hanyi.mongo.pojo.many;

import com.hanyi.mongo.pojo.many.incloud.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * @author weiwen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = -3810333858489188719L;

    private ObjectId id;
    private String name;
    private int age;
    private Address address;
}