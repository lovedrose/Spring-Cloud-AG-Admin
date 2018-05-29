package com.lovehins.register.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动一个服务注册中心提供给其他应用进行对话
 *
 * @author lovehins
 * @create 2017-05-25 12:44
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class CenterBootstrap {
    public static void main(String[] args) {
        log.info(" >>>>>>>>>>>>>>>>>>>>>>>>> start Register.");
        SpringApplication.run(CenterBootstrap.class, args);
        log.info(" <<<<<<<<<<<<<<<<<<<<<<<<< start Register.");
    }
}
