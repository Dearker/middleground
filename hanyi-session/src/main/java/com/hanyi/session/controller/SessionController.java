package com.hanyi.session.controller;

import com.hanyi.session.bo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 11:17 下午 2020/11/14
 */
@Slf4j
@RestController
public class SessionController {

    @GetMapping("/")
    public UserInfo buildSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo userInfo = new UserInfo("柯基", "123456");
        session.setAttribute("loginUser", userInfo);
        return userInfo;
    }

}
