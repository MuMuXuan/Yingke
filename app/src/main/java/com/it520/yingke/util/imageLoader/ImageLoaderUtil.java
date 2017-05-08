package com.it520.yingke.util.imageLoader;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-03 14:59 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.Executors;

public class ImageLoaderUtil {
    private static volatile ImageLoaderUtil sInstance;

    public static ImageLoaderUtil getSingleton() {
        if (sInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoaderUtil();
                }
            }
        }
        return sInstance;
    }

    public void pause(){
        if (!Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().pause();
            Executors.newSingleThreadExecutor();
        }
    }

    public void restart(){
        if (Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().resume();
        }
    }
}
