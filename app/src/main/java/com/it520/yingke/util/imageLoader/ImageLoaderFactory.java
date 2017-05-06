package com.it520.yingke.util.imageLoader;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-03 14:39 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.youth.banner.loader.ImageLoader;

import java.util.HashMap;

public class ImageLoaderFactory {

    private static HashMap<Class,ImageLoader> sLoaderHashMap = new HashMap<>();

    public static synchronized <T extends ImageLoader> ImageLoader createImageLoader(Class<T> imageLoaderClass){
        ImageLoader imageLoader = sLoaderHashMap.get(imageLoaderClass);
        if(imageLoader==null){
            try {
                imageLoader = imageLoaderClass.newInstance();
                sLoaderHashMap.put(imageLoaderClass,imageLoader);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return imageLoader;
    }
}
