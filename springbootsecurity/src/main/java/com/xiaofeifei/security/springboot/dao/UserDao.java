package com.xiaofeifei.security.springboot.dao;

import com.xiaofeifei.security.springboot.model.PermissionDto;
import com.xiaofeifei.security.springboot.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserDto getUserByUsername(String username) {
        String sql = "select id,username,password,fullname from t_user where username = ?";
        List<UserDto> list = jdbcTemplate.query(sql, new Object[]{username}, new
                BeanPropertyRowMapper<>(UserDto.class));
        if (list == null && list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    //根据用户id查询用户的权限
    public List<String> findPermissionsByUserId(String userId) {
        String sql = "select * from t_permission WHERE id IN (\n" +
                "\tselect permission_id from t_role_permission WHERE role_id in(\n" +
                "\t\tselect role_id from t_user_role WHERE user_id = ? \n" +
                "\t)\n" +
                ")";
        List<PermissionDto> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDto.class));
        List<String> permissions = new ArrayList<>();
        list.forEach(c -> permissions.add(c.getCode()));
        return permissions;
    }

}