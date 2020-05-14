package com.xiaofeifei.security.distribute.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    // 定义用户信息服务（查询用户信息）
    /*@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("$2a$10$CwwagzM3CItSyTE8sw1pHeJy4E9ql0.jrRUA/g8Pdd1ry5DDe1bhq").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("123456").authorities("p2").build());

        return manager;
    }*/

    //认证管理器
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //密码编辑器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 这个类中作用是比较的时候直接比较，即没有加密，由于这个类陪
    }

    //安全拦截机制

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //注意下面的放行规则是有逻辑顺序的，即只要一个访问通过了，后面的就不会再执行了
        //所以我们通常将细节放在前面，范围大的规则放到后面，范围小的放在前面

        /*http.csrf().disable() //关闭scrf 的请求，因为spring为了防止跨站伪造访问，禁止除了get意外的请求
                .authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1")  //设置访问资源r1 必须有P1资源
                .antMatchers("/r/r2").hasAuthority("p2")  //设置访问资源r1 必须有P2资源
                .antMatchers("/r/**").authenticated()  //这里表示的是所有的 /r/** 的请求都需要验证通过
                .anyRequest().permitAll()        //其他的请求都可以认证通过
                .and()
                .formLogin()                  //允许可以通过表单的方式登录
                .loginPage("/login-view")//配置登录的页面,当没有权限就会跳转到这个url
                .loginProcessingUrl("/login")  //制定点击登录页面的登录时，表单提交的url
                .successForwardUrl("/login-success")//自定义登录成功后跳转的url
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //session创建的策略，如果需要就创建，是默认的策略，
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-view?logout")
                ;*/
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/r/r1").hasAnyAuthority("p1")
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }
}
