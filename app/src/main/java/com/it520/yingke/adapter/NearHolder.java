package com.it520.yingke.adapter;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-24 16:37 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.base.recyclerView.MyBaseHolder;

public class NearHolder extends MyBaseHolder {

    public NearHolder(View itemView) {
        super(itemView);
    }

    public void setHeightAsWidth(int photoId) {
        View view = getView(photoId);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int width = layoutParams.width;
        int measuredWidth = view.getMeasuredWidth();
        Log.e(getClass().getSimpleName() + "xmg", "setHeightAsWidth: " + measuredWidth);
//        layoutParams.height = width;
//        view.setLayoutParams(layoutParams);
    }
}
