package com.xiaofeifei.security.springmvc.interceptor;

import com.xiaofeifei.security.springmvc.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {


    @Override //在调用所有controller之前我们需要进行拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在这个方法中校验用户请求的url是否在用户的权限范围内
        Object object = request.getSession().getAttribute(UserDto.SESSION_USER_KEY);
        if (object == null) {
            writeContent(response, "请登录");
        }

        UserDto userDto = (UserDto) object;

        String requestURI = request.getRequestURI();

        if (userDto.getAuthorities().contains("p1") && requestURI.contains("/r/r1"))  //拥有p1 权限的可以访问 /r/r1
        {
            return true;
        }

        if (userDto.getAuthorities().contains("p2") && requestURI.contains("/r/r2"))  // 拥有p2权限的可以访问 /r/r2
        {
            return true;
        }

        writeContent(response, "没有权限，拒绝访问");

        return false;
    }

    //响应信息给客户端
    private void writeContent(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(message);
        writer.close();
    }
}
