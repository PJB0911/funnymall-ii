package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**单个redis连接池的工具类，该类后期架构演进被废除
 * @author PJB
 * @date 2020/4/23
 */
@Slf4j
@Deprecated
public class RedisPoolUtil {
    /**
     * 设置key的有效期，单位是秒
     * @param key 键
     * @param exTime  有效期
     * @return  设置返回1，未设置返回0
     */
    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("setExpire key:{} expires:{}", key, exTime, e);
        } finally {
            release(jedis);
        }
        return result;
    }

    /**
     *  增加字符串（如果 key 已存在,覆写旧值），并设置有效期
     * @param key 键
     * @param value 值
     * @param exTime 有效期，单位秒
     * @return
     */
    public static String setEx(String key,String value,int exTime){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} error", key, value, e);
        } finally {
            release(jedis);
        }
        return result;
    }
    /**
     *  增加字符串（如果 key 已存在,覆写旧值），
     * @param key 键
     * @param value 值
     * @return
     */
    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{}", key, value, e);
        } finally {
            release(jedis);
        }
        return result;
    }

    /**
     * 根据键获取值
     * @param key 键
     * @return 值
     */
    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{}", key, e);
        } finally {
            release(jedis);
        }
        return result;
    }

    /**
     * 删除键
     * @param key
     * @return
     */
    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{}", key, e);
        } finally {
            release(jedis);
        }
        return result;
    }

    /**
     *  释放redis连接
     * @param jedis jedis
     */
    private static void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
