package com.lovehins.cache.aspect;

import com.lovehins.cache.annotation.CacheClear;
import com.lovehins.cache.api.CacheAPI;
import com.lovehins.cache.constants.CacheScope;
import com.lovehins.cache.parser.IKeyGenerator;
import com.lovehins.cache.parser.impl.DefaultKeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lovedrose on 25/04/2018.
 */
@Aspect
@Service
public class CacheClearAspect {
    private IKeyGenerator keyParser;
    private CacheAPI cacheAPI;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ConcurrentHashMap<String, IKeyGenerator> generatorMap = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.lovehins.cache.annotation.CacheClear)")
    public void aspect() {
    }

    @Around("aspect()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint invocation, CacheClear anno) throws Throwable {
        MethodSignature sign = (MethodSignature) invocation.getSignature();
        Method method = sign.getMethod();
        Class[] parameterTypes = method.getParameterTypes();
        Object[] args = invocation.getArgs();
        String key = "";
        if(StringUtils.isNotBlank(anno.key())) {
            key = this.getKey(anno, anno.key(), CacheScope.application, parameterTypes, args);
            this.cacheAPI.remove(key);
        } else if(StringUtils.isNotBlank(anno.pre())) {
            key = this.getKey(anno, anno.pre(), CacheScope.application, parameterTypes, args);
            this.cacheAPI.removeByPre(key);
        } else if(anno.keys().length > 1) {
            String[] arr$ = anno.keys();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String tmp = arr$[i$];
                tmp = this.getKey(anno, tmp, CacheScope.application, parameterTypes, args);
                this.cacheAPI.removeByPre(tmp);
            }
        }

        return invocation.proceed();
    }

    private String getKey(CacheClear anno, String key, CacheScope scope, Class<?>[] parameterTypes, Object[] arguments) throws InstantiationException, IllegalAccessException {
        String generatorClsName = anno.generator().getName();
        IKeyGenerator keyGenerator = null;
        if(anno.generator().equals(DefaultKeyGenerator.class)) {
            keyGenerator = this.keyParser;
        } else if(this.generatorMap.containsKey(generatorClsName)) {
            keyGenerator = (IKeyGenerator)this.generatorMap.get(generatorClsName);
        } else {
            keyGenerator = (IKeyGenerator)anno.generator().newInstance();
            this.generatorMap.put(generatorClsName, keyGenerator);
        }

        return  keyGenerator.getKey(key, scope, parameterTypes, arguments);
    }
}
