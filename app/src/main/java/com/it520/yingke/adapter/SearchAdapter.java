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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseAdapter;
import com.it520.yingke.bean.LiveBean;
import com.it520.yingke.bean.SearchTypesAnchorBean;
import com.it520.yingke.bean.SearchUserBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.util.Constant;
import com.it520.yingke.util.UserConstant;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends MyBaseAdapter<TypeBean, SearchHolder> {

    OnSearchClickListener clickListener;

    public void setOnClickListener(OnSearchClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        int type = mList.get(position).getType();
        if (type == TypeBean.TYPE_SEARCH_RECOMMEND) {
            return TypeBean.TYPE_SEARCH_RECOMMEND;
        } else if (type == TypeBean.TYPE_SEARCH_ANCHOR_TYPES) {
            return TypeBean.TYPE_SEARCH_ANCHOR_TYPES;
        }
        return TypeBean.TYPE_RECOMMEND_TITLE;
    }



    public void updateDatas(ArrayList<TypeBean> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (mList == null) {
            mList = datas;
        } else {
            mList.clear();
            mList.addAll(datas);
        }
        //通知更新
        notifyDataSetChanged();
    }

    public interface OnSearchClickListener {
        void onClickSearchItem(int index);
        void onClickSearchOnePicture(View v, int position, LiveBean lives);
    }

    public SearchAdapter(ArrayList<TypeBean> data) {
        super(data);
    }

    @Override
    protected void bindDataToHolder(SearchHolder holder, TypeBean itemData, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TypeBean.TYPE_SEARCH_RECOMMEND) {
            SearchUserBean bean = (SearchUserBean) itemData;
            holder.setText(R.id.name, bean.getUser().getNick());
            holder.setText(R.id.type, bean.getReason());
            int sexResId = bean.getUser().getSex() == 0 ? R.drawable.global_icon_male : R.drawable.global_icon_female;
            holder.setImageRes(R.id.sex, sexResId);
            holder.setImageRes(R.id.rank, UserConstant.getRank(bean.getUser().getLevel()));
            //是否在线
            boolean isLive = TextUtils.isEmpty(bean.getLive_id()) ? false : true;
            if (isLive) {
                holder.getView(R.id.live).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.live).setVisibility(View.GONE);
            }
            //用户头像 50x50
            holder.setImageURI(R.id.icon, Constant.getScaledImgUrl(bean.getUser().getPortrait(), 100, 100));
            //todo 设置点击时跳转到对应的主播直播房间
        } else if (itemViewType == TypeBean.TYPE_SEARCH_ANCHOR_TYPES) {
            SearchTypesAnchorBean recommendBean = (SearchTypesAnchorBean) itemData;
//            initType((TypeHolder) holder,clickListener);
            List<LiveBean> lives = recommendBean.getLives();
            //100x100
            holder.setImageURI(R.id.one, Constant.getScaledImgUrl(lives.get(0).getCreator().getPortrait(), 200, 200));
            //todo 设置点击事件
            holder.setText(R.id.one_number, lives.get(0).getOnline_users() + "人");
//                TextView one_num = (TextView) itemView.findViewById(R.id.one_number);
//                one_num.setText(lives.get(0).getOnline_users()+"人");
            holder.setImageURI(R.id.two, Constant.getScaledImgUrl(lives.get(1).getCreator().getPortrait(), 200, 200));
            //todo 设置点击
            holder.setText(R.id.two_number, lives.get(1).getOnline_users() + "人");
            holder.setImageURI(R.id.three, Constant.getScaledImgUrl(lives.get(2).getCreator().getPortrait(), 200, 200));
            //todo 设置点击
            holder.setText(R.id.three_number, lives.get(2).getOnline_users() + "人");
            //设置标题
            holder.setText(R.id.title, recommendBean.getTitle());
            //todo 设置更多的点击事件
        }else{
            holder.setText(R.id.title,"今日推荐");
        }
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TypeBean.TYPE_SEARCH_ANCHOR_TYPES) {
            //好声音、游戏达人，用户分组类型
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_type, parent,false);
        } else if (viewType == TypeBean.TYPE_SEARCH_RECOMMEND) {
            //每日推荐
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_recommend, parent,false);
        } else {
            //标题（今日推荐）
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_title, parent,false);
        }
        SearchHolder searchHolder = new SearchHolder(view);
        return searchHolder;
    }
}
