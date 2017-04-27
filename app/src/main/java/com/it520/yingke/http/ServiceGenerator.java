package com.it520.yingke.http;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 15:35 
 * 
 * Description: 简易封装Retrofit的使用
 *
 * Version: 1.0
 * ============================================================
 */

import com.it520.yingke.BuildConfig;
import com.it520.yingke.util.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static volatile ServiceGenerator sInstance;
    protected Retrofit mRetrofit;

    public static ServiceGenerator getSingleton() {
        if (sInstance == null) {
            synchronized (ServiceGenerator.class) {
                if (sInstance == null) {
                    sInstance = new ServiceGenerator();
                }
            }
        }
        return sInstance;
    }

    private ServiceGenerator(){
        //第一步，准备OKHttp
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //给OKHttp新增一个拦截器
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            httpClient.addInterceptor(loggingInterceptor);
        }
        //第二步，准备Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_IP)
                .addConverterFactory(GsonConverterFactory.create());
        mRetrofit = builder.client(httpClient.build()).build();
        //第三步，开始生成接口对应实例
//        HotLiveService hotLiveService = mRetrofit.create(HotLiveService.class);
//        Call<LiveDatas> liveDatas = hotLiveService.getLiveDatas();
    }

     public <T> T createService(Class<T> clazz){
         return mRetrofit.create(clazz);
     }
}
