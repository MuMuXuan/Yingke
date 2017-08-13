package com.it520.yingke.util;

import android.text.TextUtils;
import android.util.Log;

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
        try{
            if (sGson == null) {
                sGson = new Gson();
            }
            if(!TextUtils.isEmpty(cacheJson)){
                bean = sGson.fromJson(cacheJson, clazz);
            }
            return bean;
        }catch (Exception e){
            Log.e("JsonUtil xmg", "parseJson: " + "解析json为"+clazz.getSimpleName()
                    +"异常，json字符串为："+cacheJson);
            return null;
        }

    }

    public static String toJson(Object obj){
        if (sGson == null) {
            sGson = new Gson();
        }
        String s = sGson.toJson(obj);
        return s;
    }

}
