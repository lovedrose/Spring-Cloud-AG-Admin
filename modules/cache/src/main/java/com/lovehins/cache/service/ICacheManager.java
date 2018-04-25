package com.lovehins.cache.service;

import com.lovehins.cache.entity.CacheBean;
import com.lovehins.cache.vo.CacheTree;

import java.util.List;

/**
 * Created by lovedrose on 23/04/2018.
 */
public interface ICacheManager {

    void removeAll();

    void remove(String var1);

    void remove(List<CacheBean> var1);

    void removeByPre(String var1);

    List<CacheTree> getAll();

    List<CacheTree> getByPre(String var1);

    void update(String var1, int var2);

    void update(List<CacheBean> var1, int var2);

    String get(String var1);
}
