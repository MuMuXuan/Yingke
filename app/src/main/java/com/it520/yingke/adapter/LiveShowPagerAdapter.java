package com.it520.yingke.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.bean.LiveBean;

import java.util.ArrayList;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-12 15:48 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class LiveShowPagerAdapter extends PagerAdapter {

    private ArrayList<LiveBean> mList;

    public LiveShowPagerAdapter(ArrayList<LiveBean> data) {
        mList = data;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_vertical_pager, null);
        //给每个item打上标记，方便后续操作和判断
        inflate.setId(position);
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
