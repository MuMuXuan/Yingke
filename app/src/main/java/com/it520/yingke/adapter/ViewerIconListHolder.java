package com.it520.yingke.adapter;

import android.graphics.BitmapFactory;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseHolder;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-17 17:05 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class ViewerIconListHolder extends MyBaseHolder {

    public ViewerIconListHolder(View itemView) {
        super(itemView);
    }

    public void setFrescoImage(int viewId,String imgUri){
        SimpleDraweeView simpleDraweeView = getView(viewId);
        simpleDraweeView.setImageURI(imgUri);
    }

    public void setFrescoImageTransparent(int viewId){
        SimpleDraweeView simpleDraweeView = getView(viewId);
        simpleDraweeView.setImageBitmap(BitmapFactory.decodeResource(simpleDraweeView.getResources(),R.drawable.bg_transparent));
    }

}
