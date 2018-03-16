package com.lovehins.gateway.server;


import com.lovehins.auth.client.EnableAuthClient;
import com.lovehins.gate.ratelimit.EnableGateRateLimit;
import com.lovehins.gate.ratelimit.config.IUserPrincipal;
import com.lovehins.gateway.server.config.UserPrincipal;
import com.lovehins.gateway.server.utils.DBLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by lovedrose on 2017/6/2.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.lovehins.auth.client.feign","com.lovehins.gateway.server.feign"})
@EnableZuulProxy
@EnableScheduling
@EnableAuthClient
//@EnableGateRateLimit
public class GateBootstrap {
    public static void main(String[] args) {
        DBLog.getInstance().start();
        SpringApplication.run(GateBootstrap.class, args);
    }

    @Bean
    @Primary
    IUserPrincipal userPrincipal(){
        return new UserPrincipal();
    }
}
