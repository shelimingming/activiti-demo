package com.sheliming.controller;

import com.sheliming.domain.UserDO;
import com.sheliming.service.UserService;
import com.sheliming.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo result = new ResultInfo();
        String username = request.getParameter("username");
        UserDO user = userService.login(username);
        if (user != null) {
            result.setCode(200);
            result.setMsg("登录成功");
            result.setInfo(user);
            //用户信息存放在Cookie中，实际情况下保存在Redis更佳
            Cookie cookie = new Cookie("userInfo", user.getId().toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            result.setCode(300);
            result.setMsg("登录名不存在，登录失败");
        }
        return result;
    }

    @PostMapping("/logout")
    public ResultInfo logout(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo result = new ResultInfo();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("userInfo".equals(cookie.getName())) {
                cookie.setValue(null);
                // 立即销毁cookie
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        result.setCode(200);
        return result;
    }


}
