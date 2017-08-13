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

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

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
        }
    }

    public void restart(){
        if (Fresco.getImagePipeline().isPaused()) {
            Fresco.getImagePipeline().resume();
        }
    }

    public void displayImage(String url, View view){
        if(view instanceof SimpleDraweeView){
            //该控件是Fresco中图片控件
            ((SimpleDraweeView) view).setImageURI(url);
        }else if (view instanceof ImageView){
            Glide.with(view.getContext().getApplicationContext())
                    .load(url)
                    .into((ImageView) view);
        }
    }

}
