package com.it520.yingke.http.service;

import com.it520.yingke.bean.NearListBean;
import com.it520.yingke.util.Constant;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kay on 16/11/15.
 */
public interface INearService {
    @GET(Constant.NEAR_ALL_DATE)
    Call<NearListBean> getNearInfo();
}
