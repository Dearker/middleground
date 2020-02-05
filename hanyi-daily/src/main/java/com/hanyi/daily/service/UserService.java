package com.hanyi.daily.service;

import com.hanyi.daily.mapper.one.PrimaryUserMapper;
import com.hanyi.daily.mapper.two.SecondaryUserMapper;
import com.hanyi.daily.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author weiwen
 */
@Service
public class UserService {

    @Autowired
    private PrimaryUserMapper primaryUserMapper;

    @Autowired
    private SecondaryUserMapper secondaryUserMapper;

    public List<User> primary(){
        return primaryUserMapper.findAll();
    }

    public List<User> secondary  (){
        return secondaryUserMapper.findAll();
    }

}
