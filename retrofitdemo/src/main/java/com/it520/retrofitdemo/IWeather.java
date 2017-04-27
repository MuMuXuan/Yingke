package com.it520.retrofitdemo;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-26 22:03 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWeather {

    @GET("weather")
    Call<WeatherBean> getWeather();

    @GET("weather/{cityname}")
    Call<WeatherBean> getWeather(@Path("cityname") String cityname);

//    "BASE_URL+weather/forecast3h.php?key=bbae3abd1423140c8a6eb5c4da235208&cityname=北京"
    @GET("weather/forecast3h.php")
    Call<WeatherBean> getWeather(@Query("key") String key,@Query("cityname") String cityname);

    /**
     * 表单提交要加 @FormUrlEncoded
     * Post使用map多参数
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("weather/forecast3h.php")
    Call<WeatherBean> getWeather(@FieldMap Map<String,String>  params);

}
