package com.lovehins.cache.service.impl;

import com.lovehins.cache.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * Created by lovedrose on 23/04/2018.
 */
@Service
@Slf4j
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private JedisPool pool;

    @Override
    public String get(String var1) {
        Jedis jedis = null;
        String value = null;

        try {
            jedis = pool.getResource();
            value = jedis.get(var1);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return value;
    }

    @Override
    public Set<String> getByPre(String var1) {

        Jedis jedis = null;
        Set value = null;

        try {
            jedis = pool.getResource();
            value = jedis.keys(var1 + "*");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            returnResource(pool, jedis);
        }
        return value;
    }

    @Override
    public String set(String var1, String var2) {
        Jedis jedis = null;

        String var5;
        try {
            jedis = this.pool.getResource();
            var5 = jedis.set(var1, var2);
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = "0";
        } finally {
            returnResource(this.pool, jedis);
        }

        return var5;
    }

    @Override
    public String set(String key, String value, int expire) {
        Jedis jedis = null;

        String result;
        try {
            jedis = this.pool.getResource();
            int e = jedis.ttl(key).intValue() + expire;
            result = jedis.set(key, value);
            jedis.expire(key, e);
            String var7 = result;
            return var7;
        } catch (Exception var11) {
            log.error(var11.getMessage());
            result = "0";
        } finally {
            returnResource(this.pool, jedis);
        }

        return result;
    }

    @Override
    public Long delPre(String key) {
        Jedis jedis = null;

        Long result;
        try {
            jedis = this.pool.getResource();
            Set e = jedis.keys(key + "*");
            int result1 = e.size();
            Iterator it = e.iterator();

            while(it.hasNext()) {
                String keyStr = (String)it.next();
                jedis.del(keyStr);
            }

            Long keyStr1 = new Long((long)result1);
            return keyStr1;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            result = Long.valueOf(0L);
        } finally {
            returnResource(this.pool, jedis);
        }

        return result;
    }

    @Override
    public Long del(String... keys) {
        Jedis jedis = null;

        Long var4;
        try {
            jedis = this.pool.getResource();
            Long e = jedis.del(keys);
            return e;
        } catch (Exception var8) {
            log.error(var8.getMessage());
            var4 = Long.valueOf(0L);
        } finally {
            returnResource(this.pool, jedis);
        }

        return var4;
    }

    @Override
    public Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;

        Long var6;
        try {
            jedis = this.pool.getResource();
            res = jedis.append(key, str);
            return res;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = Long.valueOf(0L);
        } finally {
            returnResource(this.pool, jedis);
        }

        return var6;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = null;

        Boolean var4;
        try {
            jedis = this.pool.getResource();
            Boolean e = jedis.exists(key);
            return e;
        } catch (Exception var8) {
            log.error(var8.getMessage());
            var4 = Boolean.valueOf(false);
        } finally {
            returnResource(this.pool, jedis);
        }

        return var4;
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = null;

        Long var5;
        try {
            jedis = this.pool.getResource();
            Long e = jedis.setnx(key, value);
            return e;
        } catch (Exception var9) {
            log.error(var9.getMessage());
            var5 = Long.valueOf(0L);
        } finally {
            returnResource(this.pool, jedis);
        }

        return var5;
    }

    @Override
    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long setrange(String key, String str, int offset) {
        Jedis jedis = null;

        Long var6;
        try {
            jedis = this.pool.getResource();
            Long e = jedis.setrange(key, (long)offset, str);
            return e;
        } catch (Exception var10) {
            log.error(var10.getMessage());
            var6 = Long.valueOf(0L);
        } finally {
            returnResource(this.pool, jedis);
        }

        return var6;
    }

    @Override
    public List<String> mget(String... keys) {
        Jedis jedis = null;
        List values = null;

        try {
            jedis = this.pool.getResource();
            values = jedis.mget(keys);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return values;
    }

    @Override
    public String mset(String... keysvalues) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.mset(keysvalues);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long msetnx(String... keysvalues) {
        Jedis jedis = null;
        Long res = Long.valueOf(0L);

        try {
            jedis = this.pool.getResource();
            res = jedis.msetnx(keysvalues);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String getset(String key, String value) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.getSet(key, value);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String getrange(String key, int startOffset, int endOffset) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.getrange(key, (long)startOffset, (long)endOffset);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.incr(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.incrBy(key, integer.longValue());
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long decr(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.decr(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.decrBy(key, integer.longValue());
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long serlen(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.strlen(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hset(key, field, value);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hsetnx(key, field, value);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hmset(key, hash);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hget(key, field);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hmget(key, fields);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long hincrby(String key, String field, Long value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hincrBy(key, field, value.longValue());
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean res = Boolean.valueOf(false);

        try {
            jedis = this.pool.getResource();
            res = jedis.hexists(key, field);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long hlen(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hlen(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long hdel(String key, String... fields) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hdel(key, fields);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hkeys(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public List<String> hvals(String key) {
        Jedis jedis = null;
        List res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hvals(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.hgetAll(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long lpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.lpush(key, strs);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long rpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.rpush(key, strs);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.linsert(key, where, pivot, value);
        } catch (Exception var11) {
            log.error(var11.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String lset(String key, Long index, String value) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.lset(key, index.longValue(), value);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.lrem(key, count, value);
        } catch (Exception var11) {
            log.error(var11.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.ltrim(key, start, end);
        } catch (Exception var12) {
            log.error(var12.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public synchronized String lpop(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.lpop(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public synchronized String rpop(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.rpop(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.rpoplpush(srckey, dstkey);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    public String lindex(String key, long index) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.lindex(key, index);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long llen(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.llen(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.lrange(key, start, end);
        } catch (Exception var12) {
            log.error(var12.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sadd(key, members);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long srem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.srem(key, members);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String spop(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.spop(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> sdiff(String... keys) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sdiff(keys);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sdiffstore(dstkey, keys);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> sinter(String... keys) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sinter(keys);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sinterstore(dstkey, keys);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> sunion(String... keys) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sunion(keys);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sunionstore(dstkey, keys);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.smove(srckey, dstkey, member);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long scard(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.scard(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.sismember(key, member);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String srandmember(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.srandmember(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.smembers(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zadd(key, score, member);
        } catch (Exception var11) {
            log.error(var11.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zrem(key, members);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        Double res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zincrby(key, score, member);
        } catch (Exception var11) {
            log.error(var11.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zrank(key, member);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zrevrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zrevrank(key, member);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zrevrange(key, start, end);
        } catch (Exception var12) {
            log.error(var12.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> zrangebyscore(String key, String max, String min) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> zrangeByScore(String key, double max, double min) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception var12) {
            log.error(var12.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zcount(String key, String min, String max) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zcount(key, min, max);
        } catch (Exception var10) {
            log.error(var10.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zcard(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zcard(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        Double res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zscore(key, member);
        } catch (Exception var9) {
            log.error(var9.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zremrangeByRank(key, start, end);
        } catch (Exception var12) {
            log.error(var12.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.zremrangeByScore(key, start, end);
        } catch (Exception var12) {
            log.error(var12.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.keys(pattern);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    @Override
    public String type(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = this.pool.getResource();
            res = jedis.type(key);
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }

    private static void returnResource(JedisPool pool, Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }

    }

    @Override
    public Date getExpireDate(String key) {
        Jedis jedis = null;
        Date res = new Date();

        try {
            jedis = this.pool.getResource();
            res = (new DateTime()).plusSeconds(jedis.ttl(key).intValue()).toDate();
        } catch (Exception var8) {
            log.error(var8.getMessage());
        } finally {
            returnResource(this.pool, jedis);
        }

        return res;
    }
}
