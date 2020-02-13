package com.hanyi.privoder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiwenchang
 * @since 2020-02-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/name")
    public String getUserName(){
        return "柯基";
    }


}
