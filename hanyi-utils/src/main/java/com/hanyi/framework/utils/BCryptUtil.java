package com.hanyi.framework.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by hanyi on 2019/10/14
 */
public class BCryptUtil {
    public static String encode(String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPass = passwordEncoder.encode(password);
        return hashPass;
    }
    public static boolean matches(String password,String hashPass){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean f = passwordEncoder.matches(password, hashPass);
        return f;
    }
}
