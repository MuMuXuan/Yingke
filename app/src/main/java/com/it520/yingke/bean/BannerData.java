package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 22:42 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.util.List;

public class BannerData implements TypeBean{
    private String error_msg;
    private int dm_error;
    private List<BannerBean> ticker;

    @Override
    public String toString() {
        return "BannerData{" +
                "error_msg='" + error_msg + '\'' +
                ", dm_error=" + dm_error +
                ", ticker=" + ticker +
                '}';
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getDm_error() {
        return dm_error;
    }

    public void setDm_error(int dm_error) {
        this.dm_error = dm_error;
    }

    public List<BannerBean> getTicker() {
        return ticker;
    }

    public void setTicker(List<BannerBean> ticker) {
        this.ticker = ticker;
    }

    @Override
    public int getType() {
        return TYPE_HOT_BANNER;
    }
}
