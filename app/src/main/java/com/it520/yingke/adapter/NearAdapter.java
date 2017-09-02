package com.it520.yingke.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseAdapter;
import com.it520.yingke.bean.NearBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.util.Constant;
import com.it520.yingke.util.UserConstant;

import java.util.ArrayList;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-28 16:06 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class NearAdapter extends MyBaseAdapter<TypeBean, NearHolder> {
    public NearAdapter(ArrayList<TypeBean> data) {
        super(data);
    }

    @Override
    public int getItemViewType(int position) {
        if(position>=mList.size()||position<0){
            return 10089;
        }
        return mList.get(position).getType();
    }

    @Override
    protected void bindDataToHolder(NearHolder holder, TypeBean itemData, int position) {
        if (getItemViewType(position) == TypeBean.TYPE_NEAR_ITEM) {
            NearBean nearBean = (NearBean) itemData;
            holder.setText(R.id.distance, nearBean.getDistance());
            holder.setImageRes(R.id.rank, UserConstant.getRank(nearBean.getCreator().getLevel()));
            Log.e(getClass().getSimpleName() + "xmg", "bindDataToHolder: w" + holder.itemView.getMeasuredWidth());
            holder.setImageURI(R.id.photo, Constant.getScaledImgUrl(nearBean.getCreator().getPortrait(), 220, 220));
        }
    }

    @Override
    public NearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate;
        if(viewType==TypeBean.TYPE_NEAR_HEAD){
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_near_head, parent, false);
        }else{
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_near, parent, false);
        }
        NearHolder nearHolder = new NearHolder(inflate);
        return nearHolder;
    }

    public void updateData(ArrayList<TypeBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (mList == null) {
            mList = list;
        } else {
            mList.clear();
            mList.addAll(list);
        }
        //通知更新
        notifyDataSetChanged();
    }
}
