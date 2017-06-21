package com.it520.yingke.fragment.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it520.yingke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-06-21 14:35 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class GiftShopFragment extends Fragment {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.ll_dots)
    LinearLayout mLlDots;
    @BindView(R.id.ll_golds)
    LinearLayout mLlGolds;
    @BindView(R.id.tv_send_store)
    TextView mTvSendStore;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.back)
    View mBack;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    protected Animation mAnimIn;
    protected Animation mAnimOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_gift_shop, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        showContent();
    }

    private void init() {
        mAnimIn = AnimationUtils.loadAnimation(getContext(), R.anim.gift_shop_in);
        mAnimOut = AnimationUtils.loadAnimation(getContext(), R.anim.gift_shop_out);
        mAnimOut.setAnimationListener(new MyOutAnimListener());
    }

    public void showContent() {
//        Toast.makeText(getContext(), "TEST", Toast.LENGTH_SHORT).show();
        boolean b = mRlContent.getVisibility() == View.VISIBLE;
        if(b)
            return;
        //不可见时，开始可见并播放动画
        mRlContent.setVisibility(View.VISIBLE);
        mRlContent.startAnimation(mAnimIn);
    }

    public void hideContent() {
        mRlContent.startAnimation(mAnimOut);
    }

    public boolean backPressed() {
        boolean b = mRlContent.getVisibility() == View.VISIBLE;
        if(b){
            hideContent();
            return false;
        }
        //可以关闭当前Activity
        return true;
    }

    @OnClick({R.id.tv_send_store, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_store:
                break;
            case R.id.back:
                hideContent();
                break;
        }
    }

    private class MyOutAnimListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mRlContent.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
