package com.lovehins.cache.annotation;

import com.lovehins.cache.constants.CacheScope;
import com.lovehins.cache.parser.ICacheResultParser;
import com.lovehins.cache.parser.IKeyGenerator;
import com.lovehins.cache.parser.impl.DefaultKeyGenerator;
import com.lovehins.cache.parser.impl.DefaultResultParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lovedrose on 24/04/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Cache {
    String key() default "";

    CacheScope scope() default CacheScope.application;

    int expire() default 720;

    String desc() default "";

    Class[] result() default {Object.class};

    Class<? extends ICacheResultParser> parser() default DefaultResultParser.class;

    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;

}
