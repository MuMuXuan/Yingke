package com.it520.yingke.widget.gift;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-07 16:26 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class GiftSentInfo {
    String giftName;
    String userName;
    String giftIcon;//礼物的图片资源url
    long tempTime;

    public String getGiftIcon() {
        return giftIcon;
    }

    public void setGiftIcon(String giftIcon) {
        this.giftIcon = giftIcon;
    }

    public long getTempTime() {
        return tempTime;
    }

    public void setTempTime(long tempTime) {
        this.tempTime = tempTime;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GiftSentInfo(String userName, String giftName) {
        this.giftName = giftName;
        this.userName = userName;
    }

    //重写hashCode和equals方法，判断是否为同一个人送的同样的礼物，
    // 如果是，就认定这是相同的事件，只要修改礼物数量即可
    @Override
    public int hashCode() {
        return userName.hashCode()*37+giftName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        GiftSentInfo info = (GiftSentInfo) obj;
        if(info!=null){
            String userName = info.getUserName();
            String giftName = info.getGiftName();
            if(this.giftName.equals(giftName)&&this.userName.equals(userName)){
                return true;
            }
        }
        return false;
    }
}
