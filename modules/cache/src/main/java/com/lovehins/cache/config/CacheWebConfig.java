package com.lovehins.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lovedrose on 23/04/2018.
 */
@Configuration("cacheWebConfig")
public class CacheWebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/static/cache/**"})
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
