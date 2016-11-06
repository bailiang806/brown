package com.happydriving.rockets.server.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author mazhiqiang
 */
public class JsonPersistenceUtils {

    public static <T> T readJsonString(String jsonString, Class<T> tClass) {
        return JSON.parseObject(jsonString, tClass);
    }

    public static <T> String writeToString(T object) {
        return JSON.toJSONString(object);
    }
}
