package com.it520.yingke.adapter;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-07 09:41 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseAdapter;
import com.it520.yingke.base.recyclerView.MyBaseHolder;
import com.it520.yingke.bean.socket.UserBean;

import java.util.ArrayList;

public class RoomMsgListAdapter extends MyBaseAdapter<UserBean,MyBaseHolder> {

    public RoomMsgListAdapter(ArrayList<UserBean> data) {
        super(data);
    }

    @Override
    protected void bindDataToHolder(MyBaseHolder holder, UserBean itemData, int position) {
        int type = itemData.getType();
        String userName = itemData.getUserId();
//        String userName = itemData.getUserName();
//        List<GiftBean> gifts = itemData.getGifts();
        String msg = itemData.getMsg();
        String text = "";
        if(type==UserBean.LOGIN_TYPE){
            text = userName +":上线";
        }else if(type==UserBean.SEND_GIFT_TYPE){
            text = userName+String.format(":我送了1个%s",itemData.getGift().getName());
        }else if(type==UserBean.SEND_MSG_TYPE){
            text = userName+String.format(":%s",msg);
        }
        holder.setText(R.id.tv_msg,text);
    }

    @Override
    public MyBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg, parent, false);
        MyBaseHolder myBaseHolder = new MyBaseHolder(inflate);
        return myBaseHolder;
    }

    public void clearAll(){
        getData().clear();
        notifyDataSetChanged();
    }

}
