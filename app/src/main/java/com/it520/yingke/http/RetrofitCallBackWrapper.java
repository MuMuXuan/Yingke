package com.it520.yingke.http;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 22:51 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.util.AndroidException;

import com.it520.yingke.util.UIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitCallBackWrapper <T> implements Callback<T>  {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(!response.isSuccessful()){
            onFailure(null,new AndroidException("请求失败"));
        }
        final T body = response.body();
        UIUtil.runInMainThread(new Runnable() {
            @Override
            public void run() {
                T body2 = body;
                onResponse(body2);
            }
        });
    }

    @Override
    public void onFailure(Call<T> call, final Throwable throwable) {
        UIUtil.runInMainThread(new Runnable() {
            @Override
            public void run() {
                onFailure(throwable);
            }
        });
    }

    public abstract void onResponse(T body);
    public abstract void onFailure(Throwable throwable);
}
