package com.xiaofeifei.security.springboot.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.SecurityConstants;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启基于方法的访问
public class LoginController {



    @RequestMapping(value = "/login-success", produces = "text/plain;charset=utf-8") //text/plain;charset=utf-8 的作用是高速浏览器返回的是文本类型
    public String loginSuccess() {
        System.out.println(Thread.currentThread().getName()+"    " +Thread.currentThread().getId());
        return getUsername() + "登录成功";
    }

    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=UTF-8"})
    public String r1() {
        return getUsername() + "访问资源r1";
    }

    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=UTF-8"})
    public String r2() {
        return getUsername() + "访问资源r2";
    }

    @GetMapping(value = "/r/r3", produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("hasAuthority='p3'")  //拥有p2的权限才可以访问
    public String r3() {
        return getUsername() + "访问资源r3";
    }

    @GetMapping(value = "/r/r4", produces = {"text/plain;charset=UTF-8"})
    public String r4() {
        return getUsername() + "访问资源r4";
    }

    @GetMapping(value = "/g/g1", produces = {"text/plain;charset=UTF-8"})
    public String g1() {
        return getUsername() + "访问资源g1";
    }

    private String getUsername() {
        //拿到认证通过的身份认证消息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        String username = null;
        if (principal == null)
        {
            username = "匿名";
        }
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            username =
                    ((org.springframework.security.core.userdetails.UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;

    }

}
