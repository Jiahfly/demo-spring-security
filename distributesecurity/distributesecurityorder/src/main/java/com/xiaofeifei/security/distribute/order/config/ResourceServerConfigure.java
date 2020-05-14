package com.xiaofeifei.security.distribute.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.HashMap;

@Configuration
@EnableResourceServer // 提供的资源配置
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "res1"; //资源的名称

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID)  //资源id，即授权认证服务中允许的资源
                .tokenStore(tokenStore)   // 自己验证token是否正确，不需要远程了
             //   .tokenServices(tokenServices()) // 当我们没有jwt 时需要配置远程 令牌认证服务,即请求资源你的客户端过来，我们需要验证令牌权限是否正确
                .stateless(true);             //
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")  //判断授权范围是不是all，这个令牌就不能用
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //由于我们这里是基于token，所以不需要记录session

    }

    // 资源服务令牌解析服务
    public ResourceServerTokenServices tokenServices() {
        //使用远程服务请求授权服务校验token，必须指定校验token的url，client_id ，client_secret
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token");
        services.setClientId("c1");
        services.setClientSecret("secret");
        return services;
    }


}
