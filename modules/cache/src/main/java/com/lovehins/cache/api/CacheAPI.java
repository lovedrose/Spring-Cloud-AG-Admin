package com.lovehins.cache.api;

import com.lovehins.cache.entity.CacheBean;

import java.util.List;

/**
 * Created by lovedrose on 24/04/2018.
 */
public interface CacheAPI {
    String get(String var1);

    void set(String var1, Object var2, int var3);

    void set(String var1, Object var2, int var3, String var4);

    Long remove(String var1);

    Long remove(String... var1);

    Long removeByPre(String var1);

    List<CacheBean> getCacheBeanByPre(String var1);

    List<CacheBean> listAll();

    boolean isEnabled();

    String addSys(String var1);
}
