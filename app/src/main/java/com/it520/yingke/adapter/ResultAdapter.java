package com.it520.yingke.adapter;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-24 15:44 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseAdapter;
import com.it520.yingke.base.recyclerView.MyBaseHolder;
import com.it520.yingke.bean.SearchUser;
import com.it520.yingke.bean.SearchUserBean;
import com.it520.yingke.util.Constant;
import com.it520.yingke.util.UserConstant;

import java.util.ArrayList;

public class ResultAdapter  extends MyBaseAdapter<SearchUserBean,MyBaseHolder> {
    public ResultAdapter(ArrayList<SearchUserBean> data) {
        super(data);
    }

    @Override
    protected void bindDataToHolder(MyBaseHolder holder, SearchUserBean itemData, int position) {
        SearchUser user = itemData.getUser();
        holder.setText(R.id.name,user.getNick());
        holder.setText(R.id.describe,user.getDescription());
        holder.setImageRes(R.id.sex,user.getSex()==0?R.drawable.global_icon_male:R.drawable.global_icon_female);
        holder.setImageRes(R.id.rank, UserConstant.getRank(user.getLevel()));
        //用户头像
        holder.setImageURI(R.id.icon, Constant.getScaledImgUrl(user.getPortrait(), 100, 100));
    }

    @Override
    public MyBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_result, parent, false);
        MyBaseHolder myBaseHolder = new MyBaseHolder(inflate);
        return myBaseHolder;
    }

    public void updateData(ArrayList<SearchUserBean> resultList) {
        if (resultList == null || resultList.size() == 0) {
            return;
        }
        if (mList == null) {
            mList = resultList;
        } else {
            mList.clear();
            mList.addAll(resultList);
        }
        //通知更新
        notifyDataSetChanged();
    }
}
