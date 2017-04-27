package com.it520.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        //第一步，准备OKHttp
        OkHttpClient.Builder httpCLient = new OkHttpClient.Builder();
        //第二步，准备Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpCLient.build()).build();
        //第三步，开始生成接口对应实例
        IWeather iWeather = retrofit.create(IWeather.class);
        //调用接口中的方法来生成Call，利用Call来请求数据
        Call<WeatherBean> weather = iWeather.getWeather();
        weather.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                WeatherBean body = response.body();
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body "+body);
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable throwable) {

            }
        });
    }

    public void click2(View view) {
        //第一步，准备OKHttp
        OkHttpClient.Builder httpCLient = new OkHttpClient.Builder();
        //第二步，准备Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpCLient.build()).build();
        //第三步，开始生成接口对应实例
        IWeather iWeather = retrofit.create(IWeather.class);
        //调用接口中的方法来生成Call，利用Call来请求数据
        Call<WeatherBean> weatherCall = iWeather.getWeather(Constant.KEY,"广州");
        weatherCall.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                WeatherBean body = response.body();
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body "+body);
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable throwable) {

            }
        });
    }

    public void click3(View view) {
        //第一步，准备OKHttp
        OkHttpClient.Builder httpCLient = new OkHttpClient.Builder();
        //第二步，准备Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpCLient.build()).build();
        //第三步，开始生成接口对应实例
        IWeather iWeather = retrofit.create(IWeather.class);
        //调用接口中的方法来生成Call，利用Call来请求数据
        Call<WeatherBean> weather = iWeather.getWeather("citys");
        weather.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                WeatherBean body = response.body();
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body "+body);
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable throwable) {

            }
        });
    }

    public void click4(View view) {
        //第一步，准备OKHttp
        OkHttpClient.Builder httpCLient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            httpCLient.addInterceptor(loggingInterceptor);
        }

        //第二步，准备Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpCLient.build()).build();

        //第三步，开始生成接口对应实例
        IWeather iWeather = retrofit.create(IWeather.class);
        //调用接口中的方法来生成Call，利用Call来请求数据
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key",Constant.KEY);
        hashMap.put("cityname","苏州");
        Call<WeatherBean> weather = iWeather.getWeather(hashMap);
        weather.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                WeatherBean body = response.body();
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body "+body);
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable throwable) {

            }
        });
    }
}
