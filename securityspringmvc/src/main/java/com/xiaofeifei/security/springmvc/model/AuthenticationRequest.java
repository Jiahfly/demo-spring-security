package com.xiaofeifei.security.springmvc.model;

import lombok.Data;

/**
 * 请求认证参数：账号和密码
 */
@Data //用lombok插件的Data注解，然后会在编译的时候生成get和set方法
public class AuthenticationRequest {

    private String username;

    private String password;

}
