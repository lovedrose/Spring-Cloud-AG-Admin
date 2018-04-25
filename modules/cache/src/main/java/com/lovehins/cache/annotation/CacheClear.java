package com.lovehins.cache.annotation;

import com.lovehins.cache.parser.IKeyGenerator;
import com.lovehins.cache.parser.impl.DefaultKeyGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lovedrose on 24/04/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CacheClear {

    String pre() default "";

    String key() default "";

    String[] keys() default {""};

    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
