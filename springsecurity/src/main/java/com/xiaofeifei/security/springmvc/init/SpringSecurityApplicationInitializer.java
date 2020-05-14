package com.xiaofeifei.security.springmvc.init;

import com.xiaofeifei.security.springmvc.config.WebSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer { //AbstractSecurityWebApplicationInitializer 中的父接口中有WebApplicationInitializer的接口，spring容器都会进行加载

    // security 的初始化
    public  SpringSecurityApplicationInitializer() {
        //super(WebSecurityConfig.class); 这行代码的作用：环境中没有使用Spring或者SpringMVC即没有spring context时，需要去掉这行代码，这样会创建context
        // 当环境中已经创建了springcontext，spring security 只需要在context中注册就可以了
    }



}
