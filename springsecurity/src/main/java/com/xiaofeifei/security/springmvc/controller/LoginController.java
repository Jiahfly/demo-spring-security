package com.xiaofeifei.security.springmvc.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {



    @RequestMapping(value = "/login", produces = "text/plain;charset=utf-8") //text/plain;charset=utf-8 的作用是高速浏览器返回的是文本类型
    public String loginSuccess() {


        return "登录成功";
    }

    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=UTF-8"})
    public String r1() {
        return "访问资源1";
    }

    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=UTF-8"})
    public String r2() {
        return "访问资源2";
    }



}
