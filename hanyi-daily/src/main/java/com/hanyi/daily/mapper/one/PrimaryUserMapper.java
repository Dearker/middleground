package com.hanyi.daily.mapper.one;

import com.hanyi.daily.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author weiwen
 */
@Component
@Mapper
public interface PrimaryUserMapper {

    /**
     * 查询Primary所有
     * @return
     */
    List<User> findAll();
}
