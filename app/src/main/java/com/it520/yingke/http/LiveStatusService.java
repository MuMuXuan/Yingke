package com.it520.yingke.http;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-15 17:01 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.it520.yingke.bean.LiveStatusBean;
import com.it520.yingke.util.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiveStatusService {
    @GET(Constant.STATUS_LIVE)
    Call<LiveStatusBean> getLiveStatus(@Query("id") String id);
}
