package com.lovehins.gateway.server;


import com.lovehins.auth.client.EnableAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by lovedrose on 2017/6/2.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthClient
@EnableFeignClients({"com.lovehins.auth.client.feign","com.lovehins.gateway.server.feign"})
public class GatewayServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerBootstrap.class, args);
    }
}