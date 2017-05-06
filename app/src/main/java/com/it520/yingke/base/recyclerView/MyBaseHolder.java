package com.it520.yingke.base.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

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

}