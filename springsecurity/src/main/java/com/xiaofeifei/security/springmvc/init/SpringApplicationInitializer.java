package com.xiaofeifei.security.springmvc.init;

import com.xiaofeifei.security.springmvc.config.ApplicationConfig;
import com.xiaofeifei.security.springmvc.config.WebConfig;
import com.xiaofeifei.security.springmvc.config.WebSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//继承的类的一个父接口有WebApplicationInitializer ，作用是相当于在web.xml中配置的servlet和listener
public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //这个方法的作用是加载spring容器,即加载applicationContext.xml
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class, WebSecurityConfig.class};  //配置容器
    }

    //作用是加载servletContext,相当于加载springmvc.xml
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};    // 配置servlet
    }

    //作用是加载url-mapping
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};            //配置访问路径，这里是根路径
    }
}
