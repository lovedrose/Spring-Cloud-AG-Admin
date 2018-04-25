package com.lovehins.cache.api.impl;

import com.alibaba.fastjson.JSON;
import com.lovehins.cache.api.CacheAPI;
import com.lovehins.cache.config.RedisConfig;
import com.lovehins.cache.constants.CacheConstants;
import com.lovehins.cache.entity.CacheBean;
import com.lovehins.cache.service.IRedisService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lovedrose on 24/04/2018.
 */
@Service
public class CacheRedis implements CacheAPI {

    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private IRedisService redisCacheService;

    @Override
    public String get(String key) {
        if (!isEnabled()) {
            return null;
        } else if (StringUtils.isBlank(key)) {
            return null;
        } else {
            CacheBean cacheBean = this.getCacheBean(key);
            if (null != cacheBean) {
                if (cacheBean.getExpireTime().getTime() > (new Date()).getTime()) {
                    return redisCacheService.get(cacheBean.getKey());
                }

                redisCacheService.del(addSys(key));
                redisCacheService.del(cacheBean.getKey());

            }
        }
        return null;
    }

    @Override
    public void set(String key, Object value, int expireMin) {
        set(key, value, expireMin, "");
    }

    @Override
    public void set(String key, Object value, int expireMin, String desc) {
        if (!StringUtils.isBlank(key) && null != value && !StringUtils.isBlank(value.toString())) {
            if (isEnabled()) {
                String realValue = "";
                if (value instanceof String) {
                    realValue = value.toString();
                } else {
                    realValue = JSON.toJSONString(value, false);
                }

                String realKey = CacheConstants.PRE + addSys(key);
                Date time = (new DateTime(redisCacheService.getExpireDate(realKey))).plusMinutes(expireMin).toDate();
                CacheBean bean = new CacheBean(realKey, desc, time);
                String result = JSON.toJSONString(bean, false);
                redisCacheService.set(addSys(key), result, expireMin * 60);
                redisCacheService.set(realKey, realValue, expireMin * 60);
            }
        }
    }

    @Override
    public Long remove(String key) {
        if (!isEnabled()) {
            return Long.valueOf(0L);
        } else if (StringUtils.isBlank(key)) {
            return Long.valueOf(0L);
        } else {
            try {
                CacheBean bean = getCacheBean(key);
                if (bean != null) {
                    redisCacheService.del(addSys(key));
                    redisCacheService.del(bean.getKey());
                }
            } catch (Exception e) {
                return Long.valueOf(0L);
            }
            return Long.valueOf(1L);
        }
    }

    @Override
    public Long remove(String... keys) {
        if(!this.isEnabled()) {
            return null;
        } else {
            try {
                for(int e = 0; e < keys.length; ++e) {
                    this.remove(keys[e]);
                }
            } catch (Exception var3) {
                return Long.valueOf(0L);
            }

            return Long.valueOf(1L);
        }
    }

    @Override
    public Long removeByPre(String pre) {
        if(!this.isEnabled()) {
            return Long.valueOf(0L);
        } else if(StringUtils.isBlank(pre)) {
            return Long.valueOf(0L);
        } else {
            try {
                Set e = this.redisCacheService.getByPre(this.addSys(pre));
                ArrayList list = new ArrayList();
                Iterator i$ = e.iterator();

                while(i$.hasNext()) {
                    String key = (String)i$.next();
                    CacheBean cache = this.getCacheBean(key);
                    list.add(cache.getKey());
                }

                this.redisCacheService.del((String[])list.toArray(new String[0]));
                this.redisCacheService.delPre(this.addSys(pre));
                return Long.valueOf(1L);
            } catch (Exception var7) {
                return Long.valueOf(0L);
            }
        }
    }

    @Override
    public List<CacheBean> getCacheBeanByPre(String pre) {
        if(StringUtils.isBlank(pre)) {
            return new ArrayList();
        } else {
            Set result = this.redisCacheService.getByPre(pre);
            Iterator it = result.iterator();
            ArrayList caches = new ArrayList();
            String key = "";
            CacheBean cache = null;

            while(it.hasNext()) {
                key = (String)it.next();

                try {
                    cache = (CacheBean)JSON.parseObject(this.redisCacheService.get(key), CacheBean.class);
                } catch (Exception var8) {
                    cache = new CacheBean();
                    cache.setKey(key);
                    cache.setExpireTime(this.redisCacheService.getExpireDate(key));
                }

                cache.setKey(key);
                caches.add(cache);
            }

            return caches;
        }
    }

    @Override
    public List<CacheBean> listAll() {
        Set set = redisCacheService.getByPre(addSys(""));
        List<CacheBean> result = new ArrayList<>();
        if (set == null) {
            return result;
        } else {
            Iterator it = set.iterator();
            String key = "";
            CacheBean bean = null;
            while(it.hasNext()) {
                bean = null;
                key = String.valueOf(it.next());
                try {
                    bean = JSON.parseObject(redisCacheService.get(key), CacheBean.class);
                } catch (Exception e) {
                    bean = new CacheBean();
                    bean.setKey(key);
                    bean.setExpireTime(redisCacheService.getExpireDate(key));
                }

                if (bean != null) {
                    bean.setKey(key);
                    result.add(bean);
                }
            }
            return result;
        }
    }

    @Override
    public boolean isEnabled() {
        return Boolean.parseBoolean(redisConfig.getEnable());
    }

    @Override
    public String addSys(String key) {
        String sys = redisConfig.getSysName();
        String result;
        if(key.startsWith(sys)) {
            result = key;
        } else {
            result = sys + ":" + key;
        }

        return result;
    }

    private CacheBean getCacheBean(String key) {
        key = this.addSys(key);
        CacheBean bean;

        try {
            bean = (CacheBean)JSON.parseObject(this.redisCacheService.get(key), CacheBean.class);
        } catch (Exception var4) {
            bean = new CacheBean();
            bean.setExpireTime(this.redisCacheService.getExpireDate(key));
            bean.setKey(key);

        }

        return bean;
    }
}
