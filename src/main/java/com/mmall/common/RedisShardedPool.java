package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.util.Hashing;
import redis.clients.jedis.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**从RedisPool迁移到ShardedRedisPool
 * 单个redis节点 -> 分布式redis架构
 * @author PJB
 * @date 2020/4/24
 */
public class RedisShardedPool {
    private static ShardedJedisPool pool;//sharded jedis连接池

    /**
     * 最大连接数
     */
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
    /**
     * 在jedispool中最大的idle状态(空闲的)的jedis实例的个数
     */
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "20"));
    /**
     * 在jedispool中最小的idle状态(空闲的)的jedis实例的个数
     */
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "20"));
    /**
     * 在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
     */
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    /**
     * 在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。
     */
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));
    /**
     * 连接超时时间
     */
    private static Integer timeout = Integer.parseInt(PropertiesUtil.getProperty("redis.timeout", "1000"));




    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。

        JedisShardInfo nodeInfo1 = new JedisShardInfo(redis1Ip,redis1Port,timeout);
//        nodeInfo1.setPassword();
        JedisShardInfo nodeInfo2 = new JedisShardInfo(redis2Ip,redis2Port,timeout);
//        nodeInfo2.setPassword();
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>(2);

        jedisShardInfoList.add(nodeInfo1);
        jedisShardInfoList.add(nodeInfo1);

        pool = new ShardedJedisPool(config,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static{
        initPool();
    }

    public static ShardedJedis getJedis(){
        return pool.getResource();
    }


    /**
     * 验证redis一致性hash算法key的分布，最终两个redis节点上key的分布：
     * redis-6379：key4、key9、key7、key6
     * redis-6380：key1、key5、key8、key0、key3、key2
     */

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();

        for(int i =0;i<10;i++){
            jedis.set("key"+i,"value"+i);
            String key = "test"+i;
            System.out.println(key+":"+jedis.getShard(key).getClient().getHost()+":"+jedis.getShard(key).getClient().getPort());
        }
       jedis.close();
        System.out.println("program is end");


    }
}
