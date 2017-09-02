package com.it520.yingke.bean.socket;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-05 15:02 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.it520.yingke.bean.GiftBean;

public class UserBean {

    public static final int LOGIN_TYPE =1;//上线
    public static final int SEND_MSG_TYPE =2;//消息
    public static final int SEND_GIFT_TYPE =3;//礼物

    private int type;// 1.代表返回的是首次登录 2.代表是聊天信息 3.代表的是礼物

    private String group;// 哪一个直播间

    private String userId;//自己用户

    private String msg;//

    private GiftBean gift;

    public UserBean(String userId) {
        this.userId = userId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GiftBean getGift() {
        return gift;
    }

    public void setGift(GiftBean gift) {
        this.gift = gift;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
