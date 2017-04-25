package com.it520.yingke;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.it520.yingke.fragment.LiveFragment;
import com.it520.yingke.fragment.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.iv_live)
    ImageView mIvLive;
    @BindView(R.id.show)
    ImageView mShow;
    @BindView(R.id.iv_mine)
    ImageView mIvMine;
    protected FragmentManager mFragmentManager;
    protected LiveFragment mLiveFragment;

    private static final String TAG_LIVE = "live";
    private static final String TAG_MINE = "mine";
    protected MineFragment mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        switchLive();
    }

    @OnClick({R.id.iv_live, R.id.show, R.id.iv_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_live:
                switchLive();
                break;
            case R.id.show:
                break;
            case R.id.iv_mine:
                switchMine();
                break;
        }
    }

    //切换到LiveFragment展示
    private void switchLive() {
        //修改控件状态
        mIvMine.setSelected(false);
        mIvLive.setSelected(true);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(mLiveFragment==null){
            mLiveFragment = new LiveFragment();
            fragmentTransaction.add(R.id.fl_frag,mLiveFragment,TAG_LIVE);
        }else{
            //如果"我的"页面Fragment不为空则先隐藏它
            if(mMineFragment!=null){
                fragmentTransaction.hide(mMineFragment);
            }
            //直接show出来
            fragmentTransaction.show(mLiveFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    //切换到MineFragment展示
    private void switchMine() {
        //修改控件状态
        mIvMine.setSelected(true);
        mIvLive.setSelected(false);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(mMineFragment==null){
            mMineFragment = new MineFragment();
            fragmentTransaction.add(R.id.fl_frag,mMineFragment,TAG_MINE);
        }else{
            //如果"直播"页面Fragment不为空则先隐藏它
            if(mLiveFragment!=null){
                fragmentTransaction.hide(mLiveFragment);
            }
            //直接show出来
            fragmentTransaction.show(mMineFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
