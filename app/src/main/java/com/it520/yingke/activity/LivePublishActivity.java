package com.it520.yingke.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.it520.yingke.R;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePublishActivity extends AppCompatActivity {

    private TXCloudVideoView mVideoView;
    private ImageView mIvIcoFocus;
    private Button mIvFlashState;
    private Button mIvEffectState;
    private Button mBtnStreamingAction;
    private TextView mTvStreamingAction;
    protected TXLivePusher mLivePusher;
    protected TXLivePushConfig mLivePushConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_live_publish);
        int[] sdkver = TXLivePusher.getSDKVersion();
        if (sdkver != null && sdkver.length >= 3) {
            Log.e("rtmpsdk","rtmp sdk version is:" + sdkver[0] + "." + sdkver[1] + "." + sdkver[2]);
        }

        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        //修正码率
        fixOrAdjustBitrate();
        mLivePusher.setConfig(mLivePushConfig);
        initView();
    }

    private void fixOrAdjustBitrate() {
        mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION,
                true,true);
    }

    private void initView() {
        mVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mIvIcoFocus = (ImageView) findViewById(R.id.iv_ico_focus);
        mIvFlashState = (Button) findViewById(R.id.iv_flash_state);
        mIvEffectState = (Button) findViewById(R.id.iv_effect_state);
        mBtnStreamingAction = (Button) findViewById(R.id.btn_streaming_action);
        mTvStreamingAction = (TextView) findViewById(R.id.tv_streaming_action);
    }


    public void onClickQuit(View view) {
        mTouchFocus = !mTouchFocus;
        mLivePushConfig.setTouchFocus(mTouchFocus);
        mLivePusher.setConfig(mLivePushConfig);
    }

    public void onClickSwitchFlash(View view) {
        mFlashTurnOn = !mFlashTurnOn;
        //mFlashTurnOn为true表示打开，否则表示关闭
        boolean b = mLivePusher.turnOnFlashLight(mFlashTurnOn);

        if (!b) {
            Toast.makeText(getApplicationContext(),
                    "打开闪光灯失败:绝大部分手机不支持前置闪光灯!", Toast.LENGTH_SHORT).show();
        }else{
            mIvFlashState.setBackgroundResource(mFlashTurnOn?R.drawable.btn_flash_off:R.drawable.btn_flash_on);
        }
    }

    public void onClickSwitchCamera(View view) {
        mLivePusher.switchCamera();
    }

    private boolean mIsBeauty = false;
    private boolean mFlashTurnOn = false;
    private boolean mTouchFocus = true;
    public void onClickSwitchBeautyEffect(View view) {
        if(!mIsBeauty){
            mLivePusher.setBeautyFilter(7, 3);
        }else{
            mLivePusher.setBeautyFilter(0, 0);
        }
        mIsBeauty = !mIsBeauty;
    }

    public void onClickStreamingButton(View view) {
        if(mVideoPublish&&mLivePusher.isPushing()){
            stopPublishRtmp();
        }else{
           startPublishRtmp();
        }

    }

    private void startPublishRtmp() {
        String rtmpUrl = "rtmp://2157.livepush.myqcloud.com/live/xxxxxx";
        mLivePusher.startPusher(rtmpUrl);
        mLivePusher.startCameraPreview(mVideoView);
        mBtnStreamingAction.setBackgroundResource(R.drawable.btn_stop_streaming);
        mTvStreamingAction.setText("结束直播");
        mVideoPublish = true;

    }

    private boolean mVideoPublish = false;

    private void stopPublishRtmp() {
        mLivePusher.stopBGM();
        mLivePusher.stopCameraPreview(true);
        mLivePusher.stopScreenCapture();
        mLivePusher.setPushListener(null);
        mLivePusher.stopPusher();
        mBtnStreamingAction.setBackgroundResource(R.drawable.btn_start_streaming);
        mTvStreamingAction.setText("开始直播");
        mVideoPublish = false;
    }

}
