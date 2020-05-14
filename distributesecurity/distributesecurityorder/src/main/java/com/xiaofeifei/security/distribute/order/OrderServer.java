package com.xiaofeifei.security.distribute.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient //服务发现的客户端
public class OrderServer {

    public static void main(String[] args) {
        SpringApplication.run(OrderServer.class);
    }

}
