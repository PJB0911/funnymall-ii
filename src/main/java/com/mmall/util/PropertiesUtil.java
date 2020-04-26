package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取工具类
 *
 * @author PJB
 * @date 2020/4/11
 */
@Slf4j
public class PropertiesUtil {

    private static Properties props;

    static {
        String fileName = "mall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常",e);
        }
    }

    /**
     *  根据键获取值value
     * @param key 键
     * @return 对应的值
     */
    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    /**
     * 根据键获取值Value,如果value不存在，则返回默认值
      * @param key 键
     * @param defaultValue  默认值
     * @return
     */
    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }



}
