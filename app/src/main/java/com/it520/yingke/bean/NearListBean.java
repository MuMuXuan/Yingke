package com.it520.yingke.bean;

import java.util.ArrayList;

/**
 * Created by kay on 16/11/14.
 */
public class NearListBean {

    private int expire_time;

    private ArrayList<NearBean> lives;

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public ArrayList<NearBean> getLives() {
        return lives;
    }

    public void setLives(ArrayList<NearBean> lives) {
        this.lives = lives;
    }
}
