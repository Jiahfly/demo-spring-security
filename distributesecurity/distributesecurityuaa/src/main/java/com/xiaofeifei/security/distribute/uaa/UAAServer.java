package com.xiaofeifei.security.distribute.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //服务发现的客户端
@EnableHystrix //熔断
@EnableFeignClients(basePackages = {"com.xiaofeifei.security.distribute.uaa"}) //远程调用
public class UAAServer {

    public static void main(String[] args) {
        SpringApplication.run(UAAServer.class, args);
    }

}
