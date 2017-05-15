package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 16:10 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.io.Serializable;

public class CreatorBean implements Serializable{

    private int id;
    private int level;
    private int gender;
    private String nick;
    private String portrait;

    @Override
    public String toString() {
        return "CreatorBean{" +
                "id=" + id +
                ", level=" + level +
                ", gender=" + gender +
                ", nick='" + nick + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
