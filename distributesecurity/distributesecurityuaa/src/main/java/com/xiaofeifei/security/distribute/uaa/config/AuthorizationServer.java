package com.xiaofeifei.security.distribute.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStroe;

    @Autowired
    private ClientDetailsService clientDetailsService;


    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    @Bean //令牌管理
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true); // 是否刷新令牌
        services.setTokenStore(tokenStroe);

        // 令牌增强，我们配置了jwt令牌时需要配置这个
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);


        services.setAccessTokenValiditySeconds(72000); //令牌默认有效时间2个小时
        services.setRefreshTokenValiditySeconds(259200); //令牌默认刷新时间3天
        return services;
    }

   /* @Bean //配置授权码服务基于内存的方式
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }*/

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);//设置授权码模式的授权码如何存取
    }


    @Override //令牌访问端点的访问策略
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")    //  oauth/token_key 公开,即不需要登录就可以得到公开的密钥
                .checkTokenAccess("permitAll()")   // oauth/checkToken来公开
                .allowFormAuthenticationForClients(); //允许通过表单的方式来认证令牌
    }

    @Override //配置客户端的连接信息，即那些客户端可以访问
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService); // 这个是基于数据库的方式，即客户端的相关信息在数据库中国
        // 浏览器访问 地址 /uaa/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=http://www.baidu.com  其中所参数要和授权的一致，response_type=code 是协议规定写死了
        //下面这里采用内存的方式
       /* clients.inMemory()
                .withClient("c1") //client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))  //配置客户端连接密钥
                .resourceIds("res1") //制定客户端可以访问那些资源
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token") //该client允许的授权类型authorization_code（这个是授权码模式申请令牌）,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围，还有read 是只读
                .autoApprove(false)//false 表示会跳转到授权页面，如果是true就会直接发令牌
                .redirectUris("http://www.baidu.com")//加上验证回调地址
                .and()  //可以配置多个
                .withClient("c2") //client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))  //配置客户端连接密钥
                .resourceIds("res2") //制定客户端可以访问那些资源
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token") //该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .scopes("all")// 允许的授权范围，还有read 是只读
                .autoApprove(false)//false 表示会跳转到授权页面，如果是true就会直接发令牌
                .redirectUris("http://www.baidu.com");//加上验证回调地址*/


    }

    @Override //配置令牌访问端点和令牌的服务
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager) //密码模式所需要的
                .authorizationCodeServices(authorizationCodeServices) // 授权码模式所需要的
                .tokenServices(tokenServices()) // 令牌的管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);  // 允许post方式提交令牌
    }






}
