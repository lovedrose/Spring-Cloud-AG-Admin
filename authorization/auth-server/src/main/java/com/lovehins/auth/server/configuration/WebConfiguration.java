package com.lovehins.auth.server.configuration;

import com.lovehins.auth.server.interceptor.ServiceAuthRestInterceptor;
import com.lovehins.auth.server.interceptor.UserAuthRestInterceptor;
import com.lovehins.base.sdk.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
<<<<<<< HEAD:authorization/auth-server/src/main/java/com/lovehins/auth/server/configuration/WebConfiguration.java
 * Created by lovedrose
 * web 配置
=======
 *
 * @author ace
 * @date 2017/9/8
>>>>>>> upstream/master:ace-auth/ace-auth-server/src/main/java/com/github/wxiaoqi/security/auth/configuration/WebConfiguration.java
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getServiceAuthRestInterceptor()).addPathPatterns("/service/**");
        registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/service/**");
    }

    @Bean
    ServiceAuthRestInterceptor getServiceAuthRestInterceptor() {
        return new ServiceAuthRestInterceptor();
    }

    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

}
