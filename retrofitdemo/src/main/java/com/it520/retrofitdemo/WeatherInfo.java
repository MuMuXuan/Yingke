package com.it520.retrofitdemo;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-26 22:47 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class WeatherInfo {

    /**
     * date : 20170502
     * efdate : 20170503050000
     * eh : 05
     * sfdate : 20170502230000
     * sh : 23
     * temp1 : 19
     * temp2 : 21
     * weather : 阴
     * weatherid : 02
     */

    private String date;
    private String temp1;
    private String temp2;
    private String weather;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
