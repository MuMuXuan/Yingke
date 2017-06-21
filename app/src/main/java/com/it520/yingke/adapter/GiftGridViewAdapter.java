package com.it520.yingke.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.it520.yingke.bean.GiftBean;

import java.util.ArrayList;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-06-21 16:21 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class GiftGridViewAdapter extends BaseAdapter {

    public ArrayList<GiftBean> mGifts;
    public GiftGridViewAdapter(ArrayList<GiftBean> gifts) {
        mGifts = gifts;
    }

    @Override
    public int getCount() {
        return mGifts.size();
    }

    @Override
    public Object getItem(int position) {
        return mGifts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(mGifts.get(position).getName());
        textView.setTextSize(22);
        return textView;
    }
}
