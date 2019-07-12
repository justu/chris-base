package com.chris.base.common.utils;

/**
 * Redis所有Keys
 *
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    public static final String APP_ACCESS_TOKEN = "APP_ACCESS_TOKEN";

    public interface Prefix {
        String VERIFY_CODE = "VERIFY_CODE_";
    }

}
