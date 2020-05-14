package com.xiaofeifei.security.springmvc.service.impl;

import com.xiaofeifei.security.springmvc.model.AuthenticationRequest;
import com.xiaofeifei.security.springmvc.model.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthenticationService implements com.xiaofeifei.security.springmvc.service.AuthenticationService {

    @Override
    public UserDto authentication(AuthenticationRequest authenticationRequest) {
        // 校验参数是否为空
        if (authenticationRequest == null
                || StringUtils.isEmpty(authenticationRequest.getUsername())
                || StringUtils.isEmpty(authenticationRequest.getPassword())) {
            throw new RuntimeException("账号和密码为空");
        }

        // 用户我们就不从数据库中查询，直接在map中拿
        UserDto user = getUserDto(authenticationRequest.getUsername());


        if (user == null) {
            throw new RuntimeException("查询不到该用户");
        }

        //校验密码
        if (!authenticationRequest.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }

        //认证通过返回用户信息
        return user;
    }

    private UserDto getUserDto(String username)
    {
        return userMap.get(username);
    }

    //用户信息
    private Map<String, UserDto> userMap = new HashMap<>();
    {
        Set<String> authorities1 = new HashSet<>();
        authorities1.add("p1"); //用户1的权限，我们让权限p1 和 r/r1资源对应
        Set<String> authorities2 = new HashSet<>();
        authorities2.add("p2"); //用户2的权限， 我们让权限p2 和r/r2资源对应
        userMap.put("zhangsan", new UserDto("1010", "zhangsan", "123", "张三", "123456789", authorities1));
        userMap.put("lisi", new UserDto("1011", "lisi", "123", "李四", "123456789", authorities1));
    }

}
