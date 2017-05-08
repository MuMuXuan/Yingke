package com.it520.yingke.adapter;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseHolder;
import com.it520.yingke.util.UIUtil;
import com.it520.yingke.util.imageLoader.FrescoImageLoader;
import com.it520.yingke.util.imageLoader.ImageLoaderFactory;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-03 14:48 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class HotListViewHolder extends MyBaseHolder{


    public HotListViewHolder(View itemView) {
        super(itemView);
    }

    public void setBanner(int viewId, List<String> images) {
        Banner banner = getView(viewId);
        ImageLoader imageLoader = ImageLoaderFactory.createImageLoader(FrescoImageLoader.class);
        banner.setImages(images)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(imageLoader)
                .start();
    }

    public void setFrescoImage(int viewId,String imgUri){
        SimpleDraweeView simpleDraweeView = getView(viewId);
        simpleDraweeView.setImageURI(imgUri);

    }

    public void setTags(int viewId,List<String> tags){
        LinearLayout ll_tag = getView(viewId);
        //设置右侧间距
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(0,0, UIUtil.dip2px(10),0);
        for (int i = 0; i < tags.size(); i++) {
            String s = tags.get(i);
            TextView textView = new TextView(ll_tag.getContext());
            textView.setBackgroundResource(R.drawable.bg_tag);
            textView.setTextSize(10);
            textView.setTextColor(ll_tag.getResources().getColor(R.color.tag_gray));
            textView.setText(s);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(UIUtil.dip2px(10),UIUtil.dip2px(3),UIUtil.dip2px(10),UIUtil.dip2px(3));
            ll_tag.addView(textView,layoutParams);
        }
    }
}
