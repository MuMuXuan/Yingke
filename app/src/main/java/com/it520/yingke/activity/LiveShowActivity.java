package com.it520.yingke.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.it520.yingke.R;
import com.it520.yingke.adapter.LiveShowPagerAdapter;
import com.it520.yingke.bean.LiveBean;
import com.it520.yingke.bean.LiveStatusBean;
import com.it520.yingke.http.LiveStatusService;
import com.it520.yingke.http.ServiceGenerator;
import com.it520.yingke.media.IjkVideoView;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LiveShowActivity extends AppCompatActivity {

    public static final String LIVE_SHOW_DATA = "live_show_data";
    public static final String LIVE_SHOW_INDEX = "live_show_index";
    protected int mCurrentIndex;
    private VerticalViewPager mViewPager;
    protected ArrayList<LiveBean> mLiveBeanList;
    protected RelativeLayout mContainer;
    protected IjkVideoView mIjkVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        if(intent!=null){
            mLiveBeanList = (ArrayList<LiveBean>) intent.getSerializableExtra(LIVE_SHOW_DATA);
            mCurrentIndex = intent.getIntExtra(LIVE_SHOW_INDEX,0);
        }
        mViewPager = (VerticalViewPager) findViewById(R.id.view_pager);
        //填充出直播视频的View
        mContainer = (RelativeLayout) LayoutInflater.from(LiveShowActivity.this).inflate(R.layout.view_room_container, null);
        mIjkVideoView = (IjkVideoView) mContainer.findViewById(R.id.ijkPlayer);
        //初始化IjkPlayer播放器
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        //初始化数据Adapter
        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIjkVideoView!=null) {
            mIjkVideoView.stopPlayback();
            mIjkVideoView.release(true);
//            mIjkVideoView.stopBackgroundPlay();
            IjkMediaPlayer.native_profileEnd();
        }
    }

    private void initData() {
        Log.e(getClass().getSimpleName() + "xmg", "initData: " + "mCurrentIndex "+mCurrentIndex+ "mData.size()"+mLiveBeanList.size());
        LiveShowPagerAdapter liveShowPagerAdapter = new LiveShowPagerAdapter(mLiveBeanList);
        mViewPager.setAdapter(liveShowPagerAdapter);
        //设置页面切换监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;
                Log.w("xmg", "page.id == " + page.getId() + ", position == " + position+", mCurrentIndex == "+mCurrentIndex
                        +", mLastRoomIndex == "+mLastRoomIndex);

                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentIndex && position == 0 && mCurrentIndex != mLastRoomIndex) {
                    if (mContainer.getParent() != null && mContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mContainer.getParent())).removeView(mContainer);
                        Log.e(getClass().getSimpleName() + "xmg", "transformPage: " + "符合条件2 被完全划出去，先进行移除");
                    }
                    Log.e(getClass().getSimpleName() + "xmg", "符合条件3 " + "进行添加");
                    loadVideoAndChatRoom(viewGroup);
                }
            }
        });
        //让ViewPager刚进来时默认选择的第一页，让其切换到对应的页面
        mViewPager.setCurrentItem(mCurrentIndex,false);
    }

    private int mLastRoomIndex = -1;

    private void loadVideoAndChatRoom(ViewGroup viewGroup) {
        //如果正在播放在，先停掉
        if(mIjkVideoView.isPlaying()){
            mIjkVideoView.stopPlayback();
        }
        String streamUrl = mLiveBeanList.get(mCurrentIndex).getStream_addr();
        mIjkVideoView.setVideoURI(Uri.parse(streamUrl));
        mIjkVideoView.start();
        viewGroup.addView(mContainer);
        //记录最近一次播放的地址，方便判断
        mLastRoomIndex = mCurrentIndex;
        checkLiveStatus();
    }

    /*if(body.getAlive()!=1){
        //主播没有在直播了，应该提示用户说该主播已经结束直播了，这里简单处理，直接弹出吐司
        finish();
        Toast.makeText(LiveShowActivity.this, "该主播已经结束直播", Toast.LENGTH_SHORT).show();
    }*/

    //检查主播的状态
    private void checkLiveStatus() {
        LiveStatusService service = ServiceGenerator.getSingleton().createService(LiveStatusService.class);
        final LiveBean liveBean = mLiveBeanList.get(mCurrentIndex);
        Call<LiveStatusBean> statusBeanCall = service.getLiveStatus(liveBean.getId());
        statusBeanCall.enqueue(new Callback<LiveStatusBean>() {
            @Override
            public void onResponse(Call<LiveStatusBean> call, Response<LiveStatusBean> response) {
                if(response.body().getAlive()!=1){
                    //主播没有在直播了，应该提示用户说该主播已经结束直播了，这里简单处理，直接弹出吐司
                    Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + liveBean.getName()+" id: "+liveBean.getId());
                    finish();
                    Toast.makeText(LiveShowActivity.this, "该主播已经结束直播", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LiveStatusBean> call, Throwable throwable) {

            }
        });
    }
}
