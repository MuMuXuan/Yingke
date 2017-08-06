package com.it520.yingke.event;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-06 12:53 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.it520.yingke.bean.GiftBean;

public class SendGiftEvent {
    GiftBean mGiftBean;

    public SendGiftEvent(GiftBean giftBean) {
        mGiftBean = giftBean;
    }

    public GiftBean getGiftBean() {
        return mGiftBean;
    }

    public void setGiftBean(GiftBean giftBean) {
        mGiftBean = giftBean;
    }
}
