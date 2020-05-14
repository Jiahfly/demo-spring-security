package com.xiaofeifei.security.distribute.uaa.service;


import com.alibaba.fastjson.JSON;
import com.xiaofeifei.security.distribute.uaa.dao.UserDao;
import com.xiaofeifei.security.distribute.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringDataUserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userDao.getUserByUsername(username);

        System.out.println("nihao");
        if (userDto != null)
        {
            List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
            String[] permissionsArray = new String[permissions.size()];
            permissions.toArray(permissionsArray);

            //这里将user转为json，将整体user存入userDetails
            String principal = JSON.toJSONString(userDto);

            UserDetails userDetails = User.withUsername(principal).password(userDto.getPassword()).authorities(permissionsArray).build();
            return userDetails;
        }

        return null; //如果为空，不用管，因为是provider来调用的，所以会有provider来抛异常
    }
}
