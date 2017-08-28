package com.it520.yingke.base.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.it520.yingke.util.imageLoader.ImageLoaderUtil;

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

    public void setImageRes(int viewId, int imgResId) {
        View view = getView(viewId);
        if(view instanceof ImageView){
            ((ImageView)view).setImageResource(imgResId);
        }else if(view instanceof SimpleDraweeView){
            ((SimpleDraweeView)view).setImageResource(imgResId);
        }
    }

    public void setImageURI(int viewId, String url){
        View view = getView(viewId);
        ImageLoaderUtil.getSingleton().displayImage(url,view);
    }

}