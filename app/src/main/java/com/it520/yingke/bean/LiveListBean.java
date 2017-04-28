package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 16:04 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.util.List;

public class LiveListBean {

    int expire_time;
    List<LiveBean> lives;

    @Override
    public String toString() {
        return "LiveData{" +
                "expire_time=" + expire_time +
                ", lives=" + lives +
                '}';
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public List<LiveBean> getLives() {
        return lives;
    }

    public void setLives(List<LiveBean> lives) {
        this.lives = lives;
    }
}
