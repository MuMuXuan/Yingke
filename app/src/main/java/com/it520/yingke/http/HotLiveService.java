package com.it520.yingke.http;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 15:36 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */


import com.it520.yingke.bean.LiveListBean;
import com.it520.yingke.util.Constant;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HotLiveService {

    @GET(Constant.INDEX_LIVE_ALL_DATE)
    Call<LiveListBean> getLiveData();

}
