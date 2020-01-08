package com.hanyi.demo.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    String createToken();

    void checkToken(HttpServletRequest request);

}
