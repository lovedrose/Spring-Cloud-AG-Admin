package com.lovehins.cache.parser.impl;

import com.lovehins.cache.parser.ICacheResultParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import com.alibaba.fastjson.JSON;

/**
 * Created by lovedrose on 23/04/2018.
 */
public class DefaultResultParser implements ICacheResultParser {
    @Override
    public Object parse(String value, Type type, Class... origins) {
        Object result = null;
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type rawType = parameterizedType.getRawType();
            if(((Class)rawType).isAssignableFrom(List.class)) {
                result = JSON.parseArray(value, (Class)parameterizedType.getActualTypeArguments()[0]);
            }
        } else if(origins == null) {
            result = JSON.parseObject(value, (Class)type);
        } else {
            result = JSON.parseObject(value, origins[0]);
        }

        return result;
    }
}
