package com.it520.verticalviewpagerdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.it520.verticalviewpagerdemo.media.IjkVideoView;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class TwoActivity extends AppCompatActivity {

    private VerticalViewPager mViewPager;
    protected View mContainerView;
    private IjkVideoView mIjkPlayerView;
    protected ArrayList<String> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        mViewPager = (VerticalViewPager) findViewById(R.id.viewPager);
        mContainerView = View.inflate(getApplicationContext(), R.layout.view_container, null);
        mIjkPlayerView = (IjkVideoView) mContainerView.findViewById(R.id.ijkPlayerView);

        //配置mIjkPlayerView的渲染方式
        mIjkPlayerView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        initListener();
        initData();

    }


    private int mLastloadIndex = -1;

    private void initListener() {
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int currentItem = mViewPager.getCurrentItem();
                Log.e(getClass().getSimpleName() + "xmg", "transformPage: " + "page.id: " + page.getId()
                        + " position " + position + "  currentItem " + currentItem);
                //当某个先前未被选中展示的页面的位置变为0时，就该添加一个播放器在它身上了
                if (position == 0 && currentItem != mLastloadIndex) {
                    mLastloadIndex = currentItem;
                    //加载视频
                    Log.e(getClass().getSimpleName() + "xmg", "transformPage: " + "家在视频");
                    ViewParent parent = mContainerView.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(mContainerView);
                    }
                    //添加
                    ((ViewGroup) page).addView(mContainerView);
                    //// TODO: 2017/8/29  加载视频等数据
                    loadRoomAndVideo();
                }


            }
        });
    }

    private void loadRoomAndVideo() {
        //加载视频
        if(mIjkPlayerView.isPlaying()){
            mIjkPlayerView.stopPlayback();
        }
        mIjkPlayerView.setVideoURI(Uri.parse(mList.get(mLastloadIndex)));
        mIjkPlayerView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIjkPlayerView.stopPlayback();
        mIjkPlayerView.release(true);
        mIjkPlayerView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("http://qqpull99.inke.cn/live/1503961161370400.flv?ikHost=tx&ikOp=0&codecInfo=8192");
        mList.add("http://qqpull99.inke.cn/live/1503960997660562.flv?ikHost=tx&ikOp=0&codecInfo=8192");
        mList.add("http://qqpull99.inke.cn/live/1503962066232438.flv?ikHost=tx&ikOp=0&codecInfo=8192");
        mList.add("http://qqpull99.inke.cn/live/1503961333562653.flv?ikHost=tx&ikOp=0&codecInfo=8192");
        mList.add("http://qqpull99.inke.cn/live/1503960807780626.flv?ikHost=tx&ikOp=0&codecInfo=8192");
        mList.add("http://qqpull99.inke.cn/live/1503961834440887.flv?ikHost=tx&ikOp=0&codecInfo=8192");
        ArrayList<RelativeLayout> viewGroups = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
            relativeLayout.setBackgroundResource(R.drawable.room_change_bg);
            viewGroups.add(relativeLayout);
        }
        MyAdapter myAdapter = new MyAdapter(viewGroups);
        mViewPager.setAdapter(myAdapter);


    }

    private class MyAdapter extends PagerAdapter {

        ArrayList<RelativeLayout> mRelativeLayouts;

        public MyAdapter(ArrayList<RelativeLayout> viewGroups) {
            mRelativeLayouts = viewGroups;
        }

        @Override
        public int getCount() {
            return mRelativeLayouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RelativeLayout relativeLayout = mRelativeLayouts.get(position);
            relativeLayout.setId(position);
            container.addView(relativeLayout);
            return relativeLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
