package com.lovehins.auth.server;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by lovedrose on 2017/6/2.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.lovehins.auth.server.mapper")
@RemoteApplicationEventScan(basePackages = "com.lovehins.auth.common.event")
@EnableAutoConfiguration
@Slf4j
public class AuthBootstrap {
    public static void main(String[] args) {
        log.info(" >>>>>>>>>>>>>>>>>>>>>>>>> start Auth Server.");
        SpringApplication.run(AuthBootstrap.class, args);
        log.info(" <<<<<<<<<<<<<<<<<<<<<<<<< start Auth Server.");
    }
}
