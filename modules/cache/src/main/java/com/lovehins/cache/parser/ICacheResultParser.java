package com.lovehins.cache.parser;

import java.lang.reflect.Type;

/**
 * Created by lovedrose on 23/04/2018.
 */
public interface ICacheResultParser {
    Object parse(String var1, Type var2, Class... var3);
}
