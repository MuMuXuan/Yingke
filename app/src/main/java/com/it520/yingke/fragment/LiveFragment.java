package com.it520.yingke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.it520.yingke.R;
import com.it520.yingke.adapter.LiveFragmentAdapter;
import com.it520.yingke.fragment.live.FoucsFragment;
import com.it520.yingke.fragment.live.HotFragment;
import com.it520.yingke.fragment.live.NearFragment;
import com.it520.yingke.fragment.live.OtherFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_live, container, false);
        ButterKnife.bind(this, inflate);
        mTitles = getResources().getStringArray(R.array.title);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewPager();
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
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.iv_right:
                break;
        }
    }
}
