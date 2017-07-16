package com.it520.yingke.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.it520.yingke.R;
import com.it520.yingke.bean.GiftBean;
import com.it520.yingke.util.Constant;

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
//        mGifts = new ArrayList<>(gifts);
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

    public GiftBean updateSelected(int position) {
        GiftBean selectedGiftBean = null ;
        for (int i = 0; i < mGifts.size(); i++) {
            GiftBean giftBean = mGifts.get(i);
            giftBean.setSelected(i==position);
            if(i==position){
                selectedGiftBean = giftBean;
            }
        }
        notifyDataSetChanged();
        return selectedGiftBean;
    }

    public void cancelAllSelected() {
        boolean needNotify = false;
        for (int i = 0; i < mGifts.size(); i++) {
            GiftBean giftBean = mGifts.get(i);
            if (giftBean.isSelected()) {
                giftBean.setSelected(false);
                needNotify = true;
            }
        }
        if (needNotify) {
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GiftViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_shop, null);
            viewHolder = new GiftViewHolder();
            viewHolder.ivPopLian = (ImageView) convertView.findViewById(R.id.iv_pop_lian);
            viewHolder.ivPay = (ImageView) convertView.findViewById(R.id.iv_pay);
            viewHolder.sdvGift = (SimpleDraweeView) convertView.findViewById(R.id.sdv_gift);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GiftViewHolder) convertView.getTag();
        }
        GiftBean giftBean = mGifts.get(position);
        if (TextUtils.isEmpty(giftBean.getName())) {
            //全部不可见
            viewHolder.tvName.setVisibility(View.INVISIBLE);
            viewHolder.tvPrice.setVisibility(View.INVISIBLE);
            viewHolder.sdvGift.setVisibility(View.INVISIBLE);
            viewHolder.ivPopLian.setVisibility(View.INVISIBLE);
            viewHolder.ivPay.setVisibility(View.INVISIBLE);
            return convertView;
        } else {
            viewHolder.tvName.setVisibility(View.VISIBLE);
            viewHolder.tvPrice.setVisibility(View.VISIBLE);
            viewHolder.sdvGift.setVisibility(View.VISIBLE);
            viewHolder.ivPopLian.setVisibility(View.VISIBLE);
            viewHolder.ivPay.setVisibility(View.VISIBLE);
        }
        viewHolder.tvName.setText(String.valueOf(giftBean.getName()));
        viewHolder.tvPrice.setText(String.valueOf(giftBean.getGold()));
        String scaledImgUrl = Constant.getScaledImgUrl(giftBean.getIcon(), 150, 150);
        Log.e(getClass().getSimpleName() + "xmg", "getView: " + "scaledImgUrl:" + scaledImgUrl);
        viewHolder.sdvGift.setImageURI(scaledImgUrl);

        boolean selected = giftBean.isSelected();
//        if (selected || giftBean.getType() == 1) {
        if (giftBean.getType() == 1) {
            viewHolder.ivPopLian.setVisibility(View.VISIBLE);
//            if (selected) {
//                viewHolder.ivPopLian.setImageResource(R.drawable.pop_gift_choose);
//            } else {
            viewHolder.ivPopLian.setImageResource(R.drawable.pop_gift_lian);
//            }
        } else {
            viewHolder.ivPopLian.setVisibility(View.INVISIBLE);
        }
        if (selected) {
            viewHolder.ll_item.setBackgroundResource(R.drawable.bg_gridview_item_selected);
        } else {
            viewHolder.ll_item.setBackgroundResource(R.drawable.bg_gridview_item);
        }
        return convertView;
    }

//    public void setIsSelect(int position) {
//        GiftBean giftBean = mGifts.get(position);
//        boolean isSelect = giftBean.isSelected();
//        giftBean.setSelected(!isSelect);
//        notifyDataSetChanged();
//    }

    private static class GiftViewHolder {
        ImageView ivPopLian;
        ImageView ivPay;
        SimpleDraweeView sdvGift;
        TextView tvPrice;
        TextView tvName;
        LinearLayout ll_item;

    }
}
