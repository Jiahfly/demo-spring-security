package com.xiaofeifei.security.springmvc.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username=" + username);
        //根据账号去数据库查询
        //这里暂时使用静态数据
        UserDetails userDetails = User.withUsername(username).password("123").authorities("p1").build();

        return userDetails;
    }
}
