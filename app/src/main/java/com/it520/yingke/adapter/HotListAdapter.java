package com.it520.yingke.adapter;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-26 21:08 
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
import com.it520.yingke.bean.BannerBean;
import com.it520.yingke.bean.BannerData;
import com.it520.yingke.bean.ExtraBean;
import com.it520.yingke.bean.LiveBean;
import com.it520.yingke.bean.LiveListBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class HotListAdapter extends MyBaseAdapter<TypeBean,HotListViewHolder>{

    private  ArrayList<String> mImages;

    public HotListAdapter(ArrayList<TypeBean> data) {
        super(data);
    }

    @Override
        protected void bindDataToHolder(HotListViewHolder holder, TypeBean itemData, int position) {
        if(itemData.getType()==TypeBean.TYPE_HOT_BANNER){
            //type为轮播图  将轮播图数据弄成List<String>
            holder.setBanner(R.id.banner, mImages);
        }else if(itemData.getType()==TypeBean.TYPE_HOT_LIVE){
            //type为直播房间展示
            LiveBean liveBean = (LiveBean) itemData;
            //id  icon
            //id  from
            //id  title
            holder.setText(R.id.name,liveBean.getCreator().getNick());
            holder.setText(R.id.viewCount,liveBean.getOnline_users()+"");
            List<ExtraBean.LabelBean> labelBeanList = liveBean.getExtra().getLabel();
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < labelBeanList.size(); i++) {
                ExtraBean.LabelBean labelBean = labelBeanList.get(i);
                String tab_name = labelBean.getTab_name();
                tags.add(tab_name);
            }
            holder.setTags(R.id.ll_tag,tags);

            String portraitImgUrl = liveBean.getCreator().getPortrait();
            String scaledImgUrl = Constant.getScaledImgUrl(portraitImgUrl, 100, 100);
            holder.setFrescoImage(R.id.src, portraitImgUrl);
            holder.setFrescoImage(R.id.icon,scaledImgUrl);
        }
    }

    @Override
    public HotListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据viewType来返回不同布局资源文件id
        int resId = -1;
        if(viewType==TypeBean.TYPE_HOT_BANNER){
            resId = R.layout.view_banner;
        }else if(viewType==TypeBean.TYPE_HOT_LIVE){
            resId = R.layout.item_hot_live;
        }
        View inflate = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        HotListViewHolder hotListViewHolder = new HotListViewHolder(inflate);
        return hotListViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        //判断当前位置的item
        return mList.get(position).getType();
    }

    public void setBannerData(BannerData bannerData){
        if(mList!=null&&mList.size()>0){
            //已经有数据时，且第一条数据是轮播图，先将其移除
            TypeBean typeBean = mList.get(0);
            if(typeBean.getType()==TypeBean.TYPE_HOT_BANNER){
                mList.remove(0);
            }
        }
        mList.add(0,bannerData);
        //将传进来的轮播图数据做处理，生成对应的图片地址集合
        List<BannerBean> ticker = bannerData.getTicker();
        mImages = new ArrayList<String>();
        for (int i = 0; i < ticker.size(); i++) {
            BannerBean bannerBean = ticker.get(i);
            String imageUrl = bannerBean.getImage();
            if(!imageUrl.contains("http")){
                imageUrl = String.format("http://img2.inke.cn/%s",imageUrl);
            }
            mImages.add(imageUrl);
        }
        notifyDataSetChanged();
    }

    public void setLiveDataList(LiveListBean liveDataList){
        if(mList!=null&&mList.size()>0){
            //已经有旧数据时，需要全部移除，但是需要保留轮播图数据，这里我们只更新直播房间的数据
            TypeBean typeBean = mList.get(0);
            if(typeBean.getType()==TypeBean.TYPE_HOT_BANNER){
                mList.clear();
                mList.add(typeBean);
            }else{
                mList.clear();
            }
        }
        List<LiveBean> lives = liveDataList.getLives();
        mList.addAll(lives);
        notifyDataSetChanged();
    }
}
