package com.hanyi.demo.client.impl;

import com.hanyi.demo.client.UserServiceClient;
import org.springframework.stereotype.Component;

/**
 * @author weiwenchang
 * @since 2020-02-12
 */
@Component
public class UserServiceImpl implements UserServiceClient {
    @Override
    public String getUserName() {
        return "fail";
    }
}
