package com.it520.yingke.http;

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

import com.it520.yingke.util.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ViewerServices {

    //尝试：返回一般的响应体来自己做解析
    @GET(Constant.GET_ROOM_VIEWERS)
    Call<ResponseBody> getViewerData(@Query("id") String roomId);
}
