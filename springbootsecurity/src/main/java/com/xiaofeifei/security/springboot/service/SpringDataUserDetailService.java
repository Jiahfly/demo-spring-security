package com.xiaofeifei.security.springboot.service;

import com.xiaofeifei.security.springboot.dao.UserDao;
import com.xiaofeifei.security.springboot.model.UserDto;
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

        if (userDto != null)
        {
            List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
            String[] permissionsArray = new String[permissions.size()];
            permissions.toArray(permissionsArray);

            UserDetails userDetails = User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities(permissionsArray).build();
            return userDetails;
        }

        return null; //如果为空，不用管，因为是provider来调用的，所以会有provider来抛异常
    }
}
