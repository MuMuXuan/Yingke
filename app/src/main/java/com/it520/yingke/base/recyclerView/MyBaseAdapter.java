package com.it520.yingke.base.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-10 21:29 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public abstract class MyBaseAdapter<T,V extends MyBaseHolder> extends RecyclerView.Adapter<V> {

    protected ArrayList<T> mList;

    public MyBaseAdapter(ArrayList<T> data) {
        mList = data;
    }

    public ArrayList<T> getData() {
        return mList;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        final int innerPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnRecyclerViewItemClickListener!=null){
                    mOnRecyclerViewItemClickListener.onItemClick(v,innerPosition);
                }
            }
        });
        bindDataToHolder(holder,mList.get(position),innerPosition);
    }

    protected abstract void bindDataToHolder(V holder, T itemData,int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        mOnRecyclerViewItemClickListener = listener;
    }

    public void addItem(T itemText){
        mList.add(itemText);
        notifyDataSetChanged();
    }

    public void addItem(T itemText,int position){
        mList.add(position,itemText);
        notifyItemInserted(position);
    }

    public void deleteItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

}
