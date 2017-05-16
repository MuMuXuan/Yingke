package com.it520.yingke.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
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
import com.it520.yingke.fragment.show.ShowFragment;
import com.it520.yingke.http.LiveStatusService;
import com.it520.yingke.http.ServiceGenerator;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveShowActivity extends AppCompatActivity {

    public static final String LIVE_SHOW_DATA = "live_show_data";
    public static final String LIVE_SHOW_INDEX = "live_show_index";
    public static final String TAG_SHOWFRAGMENT = "ShowFragment";
    protected int mCurrentIndex;
    private VerticalViewPager mViewPager;
    protected ArrayList<LiveBean> mLiveBeanList;
    protected RelativeLayout mContainer;
//    protected IjkVideoView mIjkVideoView;
    protected ShowFragment mShowFragment;
    protected PLVideoTextureView mTexture_view;

    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        if(intent!=null){
            mLiveBeanList = (ArrayList<LiveBean>) intent.getSerializableExtra(LIVE_SHOW_DATA);
            mCurrentIndex = intent.getIntExtra(LIVE_SHOW_INDEX,0);
        }
        mViewPager = (VerticalViewPager) findViewById(R.id.view_pager);
        //填充出直播视频的View
        mContainer = (RelativeLayout) LayoutInflater.from(LiveShowActivity.this).inflate(R.layout.view_room_container, null);
//        mIjkVideoView = (IjkVideoView) mContainer.findViewById(R.id.ijkPlayer);
        mTexture_view = (PLVideoTextureView) mContainer.findViewById(R.id.texture_view);

        mTexture_view.setDisplayAspectRatio(mDisplayAspectRatio);
        //初始化IjkPlayer播放器
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        //初始化数据Adapter
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTexture_view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTexture_view.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTexture_view.stopPlayback();
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
    private boolean isInited = false;

    private void loadVideoAndChatRoom(ViewGroup viewGroup) {
        if(!isInited){
            //初始化Fragment
            mShowFragment = new ShowFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_show_frag,mShowFragment,TAG_SHOWFRAGMENT).commit();
            mTexture_view.setOnCompletionListener(mOnCompletionListener);
            mTexture_view.setOnErrorListener(mOnErrorListener);
            isInited = true;
        }else{
            //调用Fragment的更新方法，把数据进行修改

        }
        //如果正在播放在，先停掉
        if(mTexture_view.isPlaying()){
            mTexture_view.stopPlayback();
        }
        String streamUrl = mLiveBeanList.get(mCurrentIndex).getStream_addr();
        mTexture_view.setVideoURI(Uri.parse(streamUrl));
        mTexture_view.start();
        viewGroup.addView(mContainer);
        //记录最近一次播放的地址，方便判断
        mLastRoomIndex = mCurrentIndex;
        checkLiveStatus();
    }

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
//            if (isNeedReconnect) {
//                sendReconnectMessage();
//            } else {
                finish();
//            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };
    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            showToastTips("Play Completed !");
            finish();
        }
    };

    private Toast mToast;

    private void showToastTips(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(getApplicationContext(), tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

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
