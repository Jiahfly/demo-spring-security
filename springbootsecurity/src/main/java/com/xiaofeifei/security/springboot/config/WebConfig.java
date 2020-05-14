package com.xiaofeifei.security.springboot.config;



import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration //这个配置相当于springmvc.xml
public class WebConfig implements WebMvcConfigurer {


    //将/ 直接导向login.jsp
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/login-view"); //我们将根路径定向到login-view
        registry.addViewController("/login-view").setViewName("login");   //我们将login-view 定向到login的视图
    }


}
