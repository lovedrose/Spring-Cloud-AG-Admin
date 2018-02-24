package com.lovehins.auth.client.configuration;

import com.lovehins.auth.client.config.ServiceAuthConfig;
import com.lovehins.auth.client.config.UserAuthConfig;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置
 */
@Configuration
@ComponentScan({"com.lovehins.auth.client","com.lovehins.auth.common.event"})
@RemoteApplicationEventScan(basePackages = "com.lovehins.auth.common.event")
public class AutoConfiguration {
    @Bean
    ServiceAuthConfig getServiceAuthConfig(){
        return new ServiceAuthConfig();
    }

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }

}
