package com.xiaofeifei.security.distribute.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

    private String SIGNING_KEY = "uaa123"; //

   /* @Bean
    public TokenStore tokenStore() {
        //内存方式生成普通令牌，这里普通相对jwt令牌而言
        return new InMemoryTokenStore();
    }*/

   @Bean // 用jwt令牌
    public TokenStore tokenStore() {
        //采用jwt令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
       JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       converter.setSigningKey(SIGNING_KEY);  //对称秘钥，资源服务器使用该秘钥来验证将来资源服务器，也需要这个秘钥来进行解密
       return converter;
    }


}
