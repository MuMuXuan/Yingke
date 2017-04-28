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

import com.it520.yingke.R;
import com.it520.yingke.base.recyclerView.MyBaseAdapter;
import com.it520.yingke.base.recyclerView.MyBaseHolder;
import com.it520.yingke.bean.BannerBean;
import com.it520.yingke.bean.BannerData;
import com.it520.yingke.bean.ExtraBean;
import com.it520.yingke.bean.LiveBean;
import com.it520.yingke.bean.LiveListBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.util.Constant;
import com.it520.yingke.widget.banner.FrescoImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HotListAdapter extends MyBaseAdapter<TypeBean>{

    private  ArrayList<String> mImages;
    protected FrescoImageLoader mFrescoImageLoader;

    public HotListAdapter(ArrayList<TypeBean> data) {
        super(data);
    }

    @Override
    public int getItemLayoutResId(int viewType) {
        if(viewType==TypeBean.TYPE_HOT_BANNER){
            return R.layout.view_banner;
        }else if(viewType==TypeBean.TYPE_HOT_LIVE){
            return R.layout.item_hot_live;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }



    @Override
    protected void bindDataToHolder(MyBaseHolder holder, TypeBean itemData, int position) {
        if(itemData.getType()==TypeBean.TYPE_HOT_BANNER){
            //是轮播图  将轮播图数据弄成List<String>
            holder.setBanner(R.id.banner, mImages,mFrescoImageLoader);
        }else if(itemData.getType()==TypeBean.TYPE_HOT_LIVE){
            //直播房间展示
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

    public void setBannerData(BannerData bannerData){
        mList.add(0,bannerData);
        //将传进来的轮播图数据做处理，生成对应的图片地址集合
        List<BannerBean> ticker = bannerData.getTicker();
        mImages = new ArrayList<String>();
        mFrescoImageLoader = new FrescoImageLoader();
        for (int i = 0; i < ticker.size(); i++) {
            BannerBean bannerBean = ticker.get(i);
            mImages.add(bannerBean.getImage());
        }
        notifyDataSetChanged();
    }

    public void setLiveDataList(LiveListBean liveDataList){
        List<LiveBean> lives = liveDataList.getLives();
        mList.addAll(lives);
        notifyDataSetChanged();
    }
}
