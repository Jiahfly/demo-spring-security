package com.xiaofeifei.security.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration  //相当于applicationContext.xml ,servlet3.0后，配置可以放到class中
@ComponentScan(basePackages = "com.xiaofeifei.security.springmvc"
        ,excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
        //在此配置除了Controller的其它bean，比如：数据库链接池、事务管理器、业务bean等。这里配置springcontext
        //controller在servletcontext中会配置扫描
public class ApplicationConfig {

}
