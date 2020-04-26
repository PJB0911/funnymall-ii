package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Redisson初始化类
 * @author PJB
 * @date 2020/4/26
 */
@Slf4j
@Component
public class RedissonManager {
    private Config config = new Config();
    private Redisson redisson;


    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
     // 这里使用的是useSingleServer()
    //private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    //private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    @PostConstruct
    private void init(){
        try {
            config.useSingleServer().setAddress(redis1Ip+":"+redis1Port)
                    /*.setPassword()*/;

            redisson = (Redisson) Redisson.create(config);

            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson init error",e);
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }

    public void setRedisson(Redisson redisson) {
        this.redisson = redisson;
    }
}
