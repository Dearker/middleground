package com.hanyi.daily.service;

import com.hanyi.daily.mapper.one.PrimaryUserMapper;
import com.hanyi.daily.mapper.two.SecondaryUserMapper;
import com.hanyi.daily.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author weiwen
 */
@Service
public class UserService {

    @Resource
    private PrimaryUserMapper primaryUserMapper;

    @Resource
    private SecondaryUserMapper secondaryUserMapper;

    public List<User> primary() {
        return primaryUserMapper.findAll();
    }

    public List<User> secondary() {
        return secondaryUserMapper.findAll();
    }

}
