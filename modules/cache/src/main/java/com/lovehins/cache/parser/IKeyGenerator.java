package com.lovehins.cache.parser;

import com.lovehins.cache.constants.CacheScope;

/**
 * Created by lovedrose on 23/04/2018.
 */
public abstract class IKeyGenerator {

    public static final String LINK = "_";

    public abstract IUserKeyGenerator getUserKeyGenerator();

    public abstract String buildKey(String var1, CacheScope var2, Class<?>[] var3, Object[] var4);

    public String getKey(String key, CacheScope scope, Class<?>[] parameterTypes, Object[] arguments) {
        StringBuilder sb = new StringBuilder();
        key = this.buildKey(key, scope, parameterTypes, arguments);
        sb.append(key);
        if (CacheScope.user.equals(scope) && this.getUserKeyGenerator() != null) {
            sb.append(LINK).append(this.getUserKeyGenerator().getCurrerntUserAccount());
        }
        return sb.toString();
    }

}
