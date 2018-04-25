package com.lovehins.cache.service.impl;

import com.lovehins.cache.api.CacheAPI;
import com.lovehins.cache.config.RedisConfig;
import com.lovehins.cache.entity.CacheBean;
import com.lovehins.cache.service.ICacheManager;
import com.lovehins.cache.vo.CacheTree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lovedrose on 24/04/2018.
 */
@Service
public class CacheManagerImpl implements ICacheManager {

    @Autowired
    private Environment env;
    @Autowired
    private CacheAPI cacheAPI;
    @Autowired
    private RedisConfig redisConfig;

    @Override
    public void removeAll() {
        cacheAPI.removeByPre(redisConfig.getSysName());
    }

    @Override
    public void remove(String var1) {
        cacheAPI.remove(var1);
    }

    @Override
    public void remove(List<CacheBean> var1) {
        String[] keys = new String[var1.size()];
        int i = 0;
        for(Iterator it = var1.iterator(); it.hasNext(); i++) {
            CacheBean bean = (CacheBean) it.next();
            keys[i] = bean.getKey();
        }
        cacheAPI.remove(keys);
    }

    @Override
    public void removeByPre(String pre) {
        if(!pre.contains(this.redisConfig.getSysName())) {
            pre = this.redisConfig.getSysName() + ":" + pre + "*";
        }

        this.cacheAPI.removeByPre(pre);
    }

    @Override
    public List<CacheTree> getAll() {
        List list = cacheAPI.listAll();
        return toTree(list);
    }



    @Override
    public List<CacheTree> getByPre(String pre) {
        if(StringUtils.isBlank(pre)) {
            return this.getAll();
        } else {
            if(!pre.contains(this.redisConfig.getSysName())) {
                pre = this.redisConfig.getSysName() + "*" + pre;
            }

            return this.toTree(this.cacheAPI.getCacheBeanByPre(pre));
        }
    }

    @Override
    public void update(String key, int hour) {
        String value = this.cacheAPI.get(key);
        this.cacheAPI.set(key, value, hour * 60);
    }

    @Override
    public void update(List<CacheBean> caches, int hour) {
        Iterator i$ = caches.iterator();

        while(i$.hasNext()) {
            CacheBean cache = (CacheBean)i$.next();
            String value = this.cacheAPI.get(cache.getKey());
            this.cacheAPI.set(cache.getKey(), value, hour);
        }
    }

    @Override
    public String get(String var1) {
        return cacheAPI.get(var1);
    }

    private List<CacheTree> toTree(List list) {
        ArrayList result = new ArrayList();
        HashSet cts = new HashSet();
        new CacheTree();
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            CacheBean cache = (CacheBean)i$.next();
            String[] split = cache.getKey().split(":");

            for(int i = split.length - 1; i > 0; --i) {
                CacheTree ct;
                if(i == split.length - 1) {
                    ct = new CacheTree(cache);
                } else {
                    ct = new CacheTree();
                }

                if(i - 1 >= 0) {
                    ct.setId(split[i]);
                    ct.setParentId(split[i - 1].endsWith(this.redisConfig.getSysName())?"-1":split[i - 1]);
                }

                if(split.length == 2) {
                    cts.remove(ct);
                }

                cts.add(ct);
            }
        }

        result.addAll(cts);
        return result;
    }
}
