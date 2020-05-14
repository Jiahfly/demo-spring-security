package com.xiaofeifei.security.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 定义用户信息服务（查询用户信息）
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("123456").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("123456").authorities("p2").build());

        return manager;
    }
    //密码编辑器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //
    }

    //安全拦截机制

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1")  //设置访问资源r1 必须有P1资源
                .antMatchers("/r/r2").hasAuthority("p2")  //设置访问资源r1 必须有P2资源
                .antMatchers("/r/**").authenticated()  //这里表示的是所有的 /r/** 的请求都需要验证通过
                .anyRequest().permitAll()        //其他的请求都可以认证通过
                .and()
                .formLogin()                  //允许可以通过表单的方式登录
                .successForwardUrl("/login-success"); //自定义登录成功的页面地址
    }
}
