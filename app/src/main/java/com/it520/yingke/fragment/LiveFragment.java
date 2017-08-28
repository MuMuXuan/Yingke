package com.it520.yingke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.it520.yingke.R;
import com.it520.yingke.activity.SearchActivity;
import com.it520.yingke.adapter.LiveFragmentAdapter;
import com.it520.yingke.fragment.live.FoucsFragment;
import com.it520.yingke.fragment.live.HotFragment;
import com.it520.yingke.fragment.live.NearFragment;
import com.it520.yingke.fragment.live.OtherFragment;
import com.it520.yingke.util.UIUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-25 16:11 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class LiveFragment extends Fragment {
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.slideTabLayout)
    SlidingTabLayout mSlideTabLayout;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    protected String[] mTitles;
    protected View mOldView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.frag_live, container, false);
        ButterKnife.bind(this, inflate);
        mTitles = getResources().getStringArray(R.array.title);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState==null){
            //在内存不足时页面被干掉了，此时再次进入Fragment可能会重叠，这里做判断
            initViewPager();
        }else{
            int index = savedInstanceState.getInt("index");
            Toast.makeText(UIUtil.getContext(), "恢复现场"+index, Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(index);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",mViewPager.getCurrentItem());
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Fragment fragment;
            switch (i) {
                case 0:
                    fragment = new FoucsFragment();
                    break;
                case 1:
                    fragment = new HotFragment();
                    break;
                case 2:
                    fragment = new NearFragment();
                    break;
                default:
                    fragment = new OtherFragment();
                    break;
            }
            fragments.add(fragment);
        }
        LiveFragmentAdapter liveFragmentAdapter = new LiveFragmentAdapter(getChildFragmentManager(),fragments,mTitles);
        mViewPager.setAdapter(liveFragmentAdapter);
        mSlideTabLayout.setViewPager(mViewPager);
        initListener();
        //默认指向第2页，热门
        mViewPager.setCurrentItem(1);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                updateTabTextSize(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTabTextSize(int position) {
        int tabCount = mSlideTabLayout.getTabCount();
        TextView titleView;
        for (int i = 0; i < tabCount; i++) {
            titleView = mSlideTabLayout.getTitleView(i);
            if(i==position){
                titleView.setTextSize(18);
                //字体加粗
                titleView.getPaint().setFakeBoldText(true);
            }else{
                titleView.setTextSize(16);
                titleView.getPaint().setFakeBoldText(false);
            }
        }
    }



    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                //开启搜索页面
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.iv_right:
                break;
        }
    }
}
