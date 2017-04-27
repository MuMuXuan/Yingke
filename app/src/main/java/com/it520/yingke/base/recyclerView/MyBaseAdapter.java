package com.it520.yingke.base.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public abstract class MyBaseAdapter<T> extends RecyclerView.Adapter<MyBaseHolder> {

    protected ArrayList<T> mList;

    public MyBaseAdapter(ArrayList<T> data) {
        mList = data;
    }

    public abstract int getItemLayoutResId(int viewType);

    @Override
    public MyBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutResId(viewType), parent, false);
        MyBaseHolder myViewHolder = new MyBaseHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyBaseHolder holder, int position) {
//        ((MyViewHolder)holder).mTvItem.setText(mList.get(position));
        final int innerPositon = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnRecyclerViewItemClickListener!=null){
                    mOnRecyclerViewItemClickListener.onItemClick(v,innerPositon);
                }
            }
        });
        bindDataToHolder(holder,mList.get(position),innerPositon);
    }

    protected abstract void bindDataToHolder(MyBaseHolder holder, T itemData,int position);

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
//        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public void deleteItem(int position){
        mList.remove(position);
//        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

}
