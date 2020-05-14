package com.xiaofeifei.security.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 用户信息插件
 */
@Data  //用lombok插件的Data注解，然后会在编译的时候生成get和set方法
@AllArgsConstructor //作用是将所有的成员变量都作为一个构造方法的参数
public class UserDto {

    public static final String SESSION_USER_KEY = "_user";

    //用户身份信息
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;

    /**
     * 用户权限
     */

    private Set<String> authorities;
}
