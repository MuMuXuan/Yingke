package com.it520.yingke.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseAdapter;
import com.it520.yingke.bean.ViewerBean;
import com.it520.yingke.util.Constant;

import java.util.ArrayList;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-17 16:49 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class ViewerIconAdapter extends MyBaseAdapter<ViewerBean,ViewerIconListHolder> {


    public ViewerIconAdapter(ArrayList<ViewerBean> data) {
        super(data);
    }

    @Override
    protected void bindDataToHolder(ViewerIconListHolder holder, ViewerBean itemData, int position) {
        ViewerBean.ExtBean ext = itemData.getExt();
        if(ext!=null&&position<3){
            holder.setFrescoImage(R.id.sDraweeView_bg,ext.getLight());
        }else{
            holder.setFrescoImageTransparent(R.id.sDraweeView_bg);
        }
        String portrait = itemData.getPortrait();
        portrait = Constant.getScaledImgUrl(portrait,100,100);
        holder.setFrescoImage(R.id.sDraweeView_viewer_icon,portrait);
    }

    @Override
    public ViewerIconListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewer_icon, parent, false);
        ViewerIconListHolder viewerIconListHolder = new ViewerIconListHolder(inflate);
        return viewerIconListHolder;
    }

    public void setViewerData(ArrayList<ViewerBean> data){
        mList.clear();
        if(data==null||data.size()==0){
            return;
        }
        mList.addAll(data);
        notifyDataSetChanged();
    }
}
