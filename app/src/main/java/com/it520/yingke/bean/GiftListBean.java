package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-06-21 15:40 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.util.List;

public class GiftListBean {
    List<GiftBean> gifts;
    String error_msg;

    public List<GiftBean> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftBean> gifts) {
        this.gifts = gifts;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
