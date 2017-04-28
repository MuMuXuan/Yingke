package com.it520.yingke.base.recyclerView;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.it520.yingke.R;
import com.it520.yingke.util.UIUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class MyBaseHolder extends RecyclerView.ViewHolder {

    public MyBaseHolder(View itemView) {
        super(itemView);
        mSparseArray = new SparseArray<>();
    }

    private SparseArray<View> mSparseArray;

    public <T extends View> T getView(int id) {
        View viewById;
        viewById = mSparseArray.get(id);
        if (viewById == null) {
            viewById = itemView.findViewById(id);
        }
        return (T) viewById;
    }

    public void setText(int viewId, String itemData) {
        TextView view = getView(viewId);
        view.setText(itemData);
    }

    public void setBanner(int viewId, List<String> images, ImageLoader imageLoader) {
        Banner banner = getView(viewId);
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(0,0, UIUtil.dip2px(10),0);

        for (int i = 0; i < tags.size(); i++) {
            String s = tags.get(i);
            TextView textView = new TextView(ll_tag.getContext());
            textView.setBackgroundResource(R.drawable.bg_tag);
            textView.setTextSize(10);
            textView.setTextColor(ll_tag.getResources().getColor(R.color.tag_gray));
//            textView.setPadding(0,UIUtil.dip2px(5),0,UIUtil.dip2px(5));
            textView.setText(s);
            textView.setGravity(Gravity.CENTER);
            TextPaint textViewPaint = textView.getPaint();
            float textWidth = textViewPaint.measureText(s);
            Paint.FontMetrics fontMetrics = textViewPaint.getFontMetrics();
            layoutParams.width = (int) (textWidth+UIUtil.dip2px(24));
            layoutParams.height = (int) ((fontMetrics.bottom-fontMetrics.top)+UIUtil.dip2px(5));

            ll_tag.addView(textView,layoutParams);
        }
    }
}