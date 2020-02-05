package com.hanyi.daily.mapper.two;

import com.hanyi.daily.common.annotation.DataSource;
import com.hanyi.daily.common.enums.DataType;
import com.hanyi.daily.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author weiwen
 */
@Component
@Mapper
public interface SecondaryUserMapper {

    /**
     * 查询Secondary所有
     * @return
     */
    @DataSource(DataType.SECONDARY)
    List<User> findAll();
}
