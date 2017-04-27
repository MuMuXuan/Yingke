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

import java.util.List;

public class WeatherBean {
    int error_code;
    String resultcode;
    String reason;
    List<WeatherInfo> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }
}
