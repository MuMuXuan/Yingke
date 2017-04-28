package com.it520.yingke;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 22:53 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

public class YKApplication extends Application {

    //获取到主线程的上下文，使整个程序都具有了上下文
    private static YKApplication mContext = null;
    //获取主线程的handler
    private static Handler mMainThreadHandler = null;
    //获取主线程的looper
    private static Looper mMainThreadLooper = null;
    //获取主线程
    private static Thread mMainThread = null;
    //获取主线程的id
    private static int mMainThreadId;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThread = Thread.currentThread();//当前类时运行在主线程的
        //android.os.Process.myUid()获取到用户id
        //android.os.Process.myPid()获取到进程id
        //android.os.Process.myTid()获取到调用线程的id
        this.mMainThreadId = android.os.Process.myTid();
        File cache = getExternalCacheDir();

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
               .setMaxCacheSize(50*50*1024)
                .setBaseDirectoryPath(cache).build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        Fresco.initialize(this, config);
    }

//    private File getCacheFile() {
//        File sd = Environment.getExternalStorageDirectory();
//        File cache = new File(sd, "mm");
//        if (!cache.exists()) {
//            cache.mkdirs();
//        }
//        return cache;
//    }

    public static YKApplication getBaseApplication(){
        return mContext;
    }
    public static Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }
    public static Looper getMainThreadLooper(){
        return mMainThreadLooper;
    }
    public static Thread getMainThread(){
        return mMainThread;
    }
    public static int getMainThreadId(){
        return mMainThreadId;
    }

}
