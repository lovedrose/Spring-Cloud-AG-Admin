package com.lovehins.cache.aspect;

import com.lovehins.cache.annotation.Cache;
import com.lovehins.cache.api.CacheAPI;
import com.lovehins.cache.parser.ICacheResultParser;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lovedrose on 24/04/2018.
 */
@Aspect
@Service
public class CacheAspect {

    @Autowired
    private IKeyGenerator keyParser;
    @Autowired
    private CacheAPI cacheAPI;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ConcurrentHashMap<String, ICacheResultParser> parserMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, IKeyGenerator> generatorMap = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.lovehins.cache.annotation.Cache)")
    public void aspect() {
    }

    @Around("aspect()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint invocation, Cache anno) throws Throwable {
        MethodSignature sign = (MethodSignature) invocation.getSignature();
        Method method = sign.getMethod();
        Class[] parameterTypes = method.getParameterTypes();
        Object[] args = invocation.getArgs();
        String key = "";
        String value = "";
        Object result = null;

        try {
            key = getKey(anno, parameterTypes, args);
            value = cacheAPI.get(key);
            Type type = method.getGenericReturnType();
            result = getResult(anno, result, value, type);
        } catch (Exception e) {
            log.error("获取缓存失败：" + key, e);
        } finally {
            if (result == null) {
                result = invocation.proceed();
                if(StringUtils.isNoneBlank(key)) {
                    cacheAPI.set(key, result, anno.expire());
                }
            }
        }

        return result;
    }

    private String getKey(Cache anno, Class<?>[] ParameterTypes, Object[] args) throws IllegalAccessException, InstantiationException {
        String genClsName = anno.generator().getName();
        IKeyGenerator keyGenerator = null;
        if (anno.generator().equals(DefaultKeyGenerator.class)) {
            keyGenerator = keyParser;
        } else if (generatorMap.contains(genClsName)) {
            keyGenerator = generatorMap.get(genClsName);
        } else {
            keyGenerator = anno.generator().newInstance();
            generatorMap.put(genClsName, keyGenerator);
        }

        return keyGenerator.getKey(anno.key(), anno.scope(), ParameterTypes, args);
    }

    private Object getResult(Cache anno, Object result, String value, Type returnType) throws IllegalAccessException, InstantiationException {
        String parserClsName = anno.parser().getName();
        ICacheResultParser parser = null;
        if (parserMap.containsKey(parserClsName)) {
            parser = parserMap.get(parserClsName);
        } else {
            parser = anno.parser().newInstance();
            parserMap.put(parserClsName, parser);
        }

        if (parser != null) {
            if (anno.result()[0].equals(Object.class)) {
                result = parser.parse(value, returnType, (Class[])null);
            } else {
                result = parser.parse(value, returnType, anno.result());
            }
        }
        return result;
    }
}
