package com.lovehins.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by lovedrose on 23/04/2018.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({AutoConfiguration.class})
@Documented
@Inherited
public @interface EnableCache {
}
