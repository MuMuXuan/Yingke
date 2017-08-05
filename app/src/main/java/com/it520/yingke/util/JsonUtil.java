package com.it520.yingke.util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * @author 小码哥Android
 * @time 2017/3/3  16:22
 * @desc ${TODD}
 */
public class JsonUtil {

    private static Gson sGson = null;

    public static <T> T  parseJson(String cacheJson,Class<T> clazz) {
        T bean = null;
        if (sGson == null) {
            sGson = new Gson();
        }
        if(!TextUtils.isEmpty(cacheJson)){
            bean = sGson.fromJson(cacheJson, clazz);
        }
        return bean;
    }

    public static String toJson(Object obj){
        if (sGson == null) {
            sGson = new Gson();
        }
        String s = sGson.toJson(obj);
        return s;
    }

}
