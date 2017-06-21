package com.it520.yingke.http.service;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-17 17:04 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.it520.yingke.bean.GiftListBean;
import com.it520.yingke.util.Constant;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GiftShopService {

    //尝试：返回一般的响应体来自己做解析
    @GET(Constant.GIFT_ALL)
    Call<GiftListBean> getGiftsData();
}
