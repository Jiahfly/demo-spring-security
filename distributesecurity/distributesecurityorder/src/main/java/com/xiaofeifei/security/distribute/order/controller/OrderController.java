package com.xiaofeifei.security.distribute.order.controller;

import com.xiaofeifei.security.distribute.order.model.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAnyAuthority('p1')") //拥有p1权限方可访问此url
    public String r1() {
        return "访问资源r1";
    }

    @GetMapping(value = "/r2")
    @PreAuthorize("hasAnyAuthority('p1')") //拥有p1权限方可访问此url
    public String r2() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal != null) {
            UserDto userDto = (UserDto)principal;  //我们在filter中我们把principal设置为userDto
            return userDto.getUsername() + "访问资源r1";
        }
        return "";
    }
}
