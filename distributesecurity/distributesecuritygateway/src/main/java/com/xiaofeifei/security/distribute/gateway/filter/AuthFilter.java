package com.xiaofeifei.security.distribute.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xiaofeifei.security.distribute.gateway.utils.EncryptUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过zuul过滤器的方式实现，目的是让下游服务能够方便的获取到当前的登录用户信息（明文token）
 */
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";  //请求之前进行拦截
    }

    @Override
    public int filterOrder() {
        return 0;  // 在zuulfiler中数值越小越优先
    }

    @Override
    public boolean shouldFilter() {
        return true;  //应该拦截
    }

    //我们要做的就是解析token和转发token
    @Override
    public Object run() throws ZuulException {
        //拿到request的上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //拿到认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //如果不是OAuth2 的认证信息则不对其进行处理
        System.out.println("*******************this is gateway pre");
        if(!(authentication instanceof OAuth2Authentication)) {
            return null;
        }
        System.out.println("*******************this is gateway");
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)authentication;
        //拿到用户认证信息
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        //取出用户身份
        String principal = userAuthentication.getName();

        //取出用户权限
        List<String> authorities = new ArrayList<>();
        userAuthentication.getAuthorities().stream().forEach(c -> authorities.add(((GrantedAuthority)c).getAuthority()));

        //拿到认证的其他信息
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();

        //将string转为object，我们后期可能存对象
        Map<String, Object> jsonToken = new HashMap<>(requestParameters);

        //把身份信息和权限放到json中
        if(userAuthentication != null) {
            jsonToken.put("principal", principal);
            jsonToken.put("authorities", authorities);
        }

        // 把身份信息和权限放在json中，加入http的header中，转发给微服务
        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));
        System.out.println("**************************");
        return null;
    }
}
