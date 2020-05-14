package com.xiaofeifei.security.distribute.gateway.config;

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

/**
 * 描述某个接入客户端需要什么样的权限才能访问某个服务
 */
@Configuration
public class ResourceServerConfigure{

    public static final String RESOURCE_ID = "res1"; //资源的名称


    //uaa资源服务，用户统一认证服务
    @Configuration
    @EnableResourceServer // 提供的资源配置
    public class UAAServerConfigure extends ResourceServerConfigurerAdapter {

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources  //资源id，即授权认证服务中允许的资源
                    .tokenStore(tokenStore)
                    .resourceId(RESOURCE_ID) // 自己验证token是否正确，不需要远程了
                    //   .tokenServices(tokenServices()) // 当我们没有jwt 时需要配置远程 令牌认证服务,即请求资源你的客户端过来，我们需要验证令牌权限是否正确
                    .stateless(true);             //
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            System.out.println("aaaaaaaaaaaaaaaaa gateway");
            http.authorizeRequests()
                    .antMatchers("/uaa/**").permitAll(); //如果是uaa服务全部放行
        }


    }

    //order资源服务
    @Configuration
    @EnableResourceServer // 提供的资源配置
    public class OrderServerConfigure extends ResourceServerConfigurerAdapter {

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenStore(tokenStore) // 自己验证token是否正确，不需要远程了
                    //   .tokenServices(tokenServices()) // 当我们没有jwt 时需要配置远程 令牌认证服务,即请求资源你的客户端过来，我们需要验证令牌权限是否正确
                    .resourceId(RESOURCE_ID)  //资源id，即授权认证服务中允许的资源

                    .stateless(true);             //
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");

        }


    }








}
