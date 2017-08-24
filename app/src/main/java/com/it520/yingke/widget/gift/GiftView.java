package com.it520.yingke.widget.gift;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-08 11:01 
 * 
 * Description: 装载两个礼物的自定义View
 *
 * Version: 1.0
 * ============================================================
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.it520.yingke.R;
import com.it520.yingke.util.imageLoader.ImageLoaderUtil;

import java.lang.ref.WeakReference;


public class GiftView extends LinearLayout {

    protected FrameLayout mContent01;
    protected FrameLayout mContent02;
    protected Animation mAnim_in;
    protected Animation mAnim_scale_num01;
    protected Animation mAnim_scale_num02;
    protected Animation mAnim_out;
    protected SparseArray<View> mSparseArray;
    protected MyHandler mHandler;

    private static final int HIDE_TIME = 5000;
    private static final int LOOP_TIME = 2500;

    public GiftView(Context context) {
        this(context,null);
    }

    public GiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //初始化view，准备出来两个装载礼物view的容器，并准备动画
    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_gift, this);
        mContent01 = (FrameLayout) inflate.findViewById(R.id.flgiftcontent01);
        mContent02 = (FrameLayout) inflate.findViewById(R.id.flgiftcontent02);
        initAnim();
        initGiftView();
        //初始化轮询用的handler（在handler中不断去隐藏礼物）
        initHandler();
    }

    private void initHandler() {
        mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageDelayed(0, LOOP_TIME);
    }

    //初始化内部的两个礼物View的布局，并给两个容器设置高度为礼物View
    private void initGiftView() {
        mSparseArray = new SparseArray<>();
        View inflate01 = LayoutInflater.from(getContext()).inflate(R.layout.item_gift, null);
        View inflate02 = LayoutInflater.from(getContext()).inflate(R.layout.item_gift, null);
        mSparseArray.put(0, inflate01);
        mSparseArray.put(1, inflate02);
        ViewGroup.LayoutParams layoutParams01 = mContent01.getLayoutParams();
        ViewGroup.LayoutParams layoutParams02 = mContent02.getLayoutParams();

        inflate01.measure(0,0);
        layoutParams01.height = inflate01.getMeasuredHeight();
        layoutParams02.height = inflate01.getMeasuredHeight();
        mContent01.setLayoutParams(layoutParams01);
        mContent02.setLayoutParams(layoutParams02);
    }

    private void initAnim() {
        mAnim_in = AnimationUtils.loadAnimation(getContext(), R.anim.gift_in);
        mAnim_out = AnimationUtils.loadAnimation(getContext(), R.anim.gift_out);
        mAnim_scale_num01 = AnimationUtils.loadAnimation(getContext(), R.anim.gift_num_scale);
        mAnim_scale_num01.setInterpolator(new OvershootInterpolator());

        mAnim_scale_num02 = AnimationUtils.loadAnimation(getContext(), R.anim.gift_num_scale);
        mAnim_scale_num02.setInterpolator(new OvershootInterpolator());
    }

    //送礼物的方法
    public void sendGift(GiftSentInfo info) {
        int showIndex = 0;//在哪个位置上开始展示这个礼物信息 0代表第一个。1代表第二个位置
        //发送礼物
        View giftView = mContent01.findViewWithTag(info);
        if(giftView != null){
            showIndex = 0;
        }else{
            giftView = mContent02.findViewWithTag(info);
            showIndex = 1;
        }
        if (giftView != null) {
            //第一种情况：当前容器中正在展示该用户先前所发出了该礼物（根据findViewByTag来找，给各个子控件设置tag）
            //处理方法：直接修改礼物的展示数量即可，并播放文字大小缩放的动画
            Log.e(getClass().getSimpleName() + "xmg", "sendGift: " + "正在展示，直接数量++");
            //更新时间
            GiftSentInfo tag = (GiftSentInfo) giftView.getTag();
            tag.setTempTime(System.currentTimeMillis());
            giftView.setTag(tag);
            updateGiftNum(giftView,showIndex);
        } else {
            //第二种情况：当前容器中没有展示。
            //这里又分两种小情况：
            //1 当前容器中有两个礼物信息在展示了，那么需要移除掉一个，
            // 这里将根据礼物数量价格以及最后展示时间来判断，比较少的则被优先移除掉

            int childCount1 = mContent01.getChildCount();
            int childCount2 = mContent02.getChildCount();
            if (childCount1 == 1 && childCount2 == 1) {
                //都有
                View giftView01 = mContent01.getChildAt(0);
                View giftView02 = mContent02.getChildAt(0);
                long time01 = ((GiftSentInfo) giftView01.getTag()).getTempTime();
                long time02 = ((GiftSentInfo) giftView02.getTag()).getTempTime();
                if (time01 > time02) {
                    //第1个礼物view展示的时间比较短，把展示时间较久的第2个view给移除
                    showIndex = 1;
                } else {
                    showIndex = 0;
                }
                //先暂时不让Handler去移除，将handler中的消息全部清除
                mHandler.removeCallbacksAndMessages(null);
                hideGiftView(showIndex);
                //自己移除掉之后，再让handler继续轮询
                mHandler.sendEmptyMessageDelayed(0, LOOP_TIME);

                Log.e(getClass().getSimpleName() + "xmg", "sendGift: " + "没有空位，此时将第" + showIndex + "个礼物干掉了，并添加");
                /*View view = getGiftView(info, showIndex);
                //给view设置tag
                info.setTempTime(System.currentTimeMillis());
                view.setTag(info);*/
                showGiftView(info, showIndex);
            } else {
                //2 当前容器中只有一个礼物信息在展示，那么只需要在该信息下面展示即可
                //看那个容器是空的
                showIndex = childCount1 > childCount2 ? 1 : 0;
                /*View view = getGiftView(info, showIndex);
                //给view设置tag
                info.setTempTime(System.currentTimeMillis());
                view.setTag(info);*/
                showGiftView(info, showIndex);
            }

        }

    }

    //添加礼物view到容器中，需要有动画
    private void showGiftView(GiftSentInfo info, int index) {
        Log.e(getClass().getSimpleName() + "xmg", "sendGift: " + "添加位置为：" + index);
        View giftView = null;
        //index为0，就添加到第一个容器
        if (index == 0) {
            //第一次的时候，容器中并没有内容，此时需要去new一个view并添加进来
            if(mContent01.getChildCount()==0){
                giftView = generateView(info);
                mContent01.addView(giftView);
            }else{
                //不是第一次，就取出容器中view直接进行更新
                giftView = mContent01.getChildAt(0);
                updateGiftViewInfo(giftView,info);
                giftView.setVisibility(VISIBLE);
            }
        } else {
            //第一次的时候，容器中并没有内容，此时需要去new一个view并添加进来
            if(mContent02.getChildCount()==0){
                giftView = generateView(info);
                mContent02.addView(giftView);
            }else{
                //不是第一次，就取出容器中view直接进行更新
                giftView = mContent02.getChildAt(0);
                updateGiftViewInfo(giftView,info);
                giftView.setVisibility(VISIBLE);
            }
        }
        giftView.startAnimation(mAnim_in);
    }


    public void cleanAll(){
        for (int i = 0; i < mContent01.getChildCount(); i++) {
            View child = mContent01.getChildAt(i);
            child.setTag(null);//清除tag
            hideGiftView(i);
        }
    }

    //移除礼物view，根据位置index来移除，需要有动画
    public void hideGiftView(int index) {
        //如果该view已经在播放动画了的话，就不要再去播放动画
        Log.e(getClass().getSimpleName() + "xmg", "hideGiftView: " + "隐藏了第"+index+"个礼物view");
        if (index == 0) {
            mContent01.getChildAt(0).setVisibility(INVISIBLE);
        } else {
            mContent02.getChildAt(0).setVisibility(INVISIBLE);
        }
    }

    public View updateGiftViewInfo(View view,GiftSentInfo info){
        //更新view的显示内容，还需要更新view的tag中info信息
        TextView tv_gift = (TextView) view.findViewById(R.id.tv_gift);
        TextView tv_user = (TextView) view.findViewById(R.id.tv_user);
        TextView tv_num = (TextView) view.findViewById(R.id.giftNum);
        View iv_giftIcon =  view.findViewById(R.id.iv_giftIcon);
        tv_user.setText(info.getUserName());
        String num = "x"+1;
        tv_num.setText(num);
        String str = "送 一个" + info.getGiftName();
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        int indexOf = str.lastIndexOf("一");
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#E1CD74")),
                indexOf,str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_gift.setText(builder);
        //通过工具类来加载图片
        ImageLoaderUtil.getSingleton().displayImage(info.getGiftIcon(),iv_giftIcon);
        //更新view的tag中info信息
        info.setTempTime(System.currentTimeMillis());
        view.setTag(info);
        return view;
    }

    //让礼物数量+1
    public void updateGiftNum(View giftView, int showIndex) {
        //确保先前的隐藏操作不会影响到
        if(giftView.getVisibility()!=VISIBLE){
            giftView.setVisibility(VISIBLE);
        }
        TextView tv_num = (TextView) giftView.findViewById(R.id.giftNum);
        String s = tv_num.getText().toString();
        int index = s.lastIndexOf("x");
        int integer = Integer.valueOf(s.substring(index+1))+1;
        tv_num.setText("x" + integer);
        Animation animation = tv_num.getAnimation();
        if(animation!=null){
            //把以前的动画先取消掉
            animation.cancel();
        }
        tv_num.startAnimation(showIndex==0?mAnim_scale_num01:mAnim_scale_num02);
    }

    private View generateView(GiftSentInfo info) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gift,null);
        view = updateGiftViewInfo(view,info);
        return view;
    }


    public static class MyHandler extends Handler {
        WeakReference<GiftView> mReference;

        public MyHandler(GiftView giftView) {
            mReference = new WeakReference<>(giftView);
        }

        @Override
        public void handleMessage(Message msg) {
            GiftView giftView = mReference.get();
            if (giftView != null) {
                //遍历
                View child01 = giftView.mContent01.getChildAt(0);
                View child02 = giftView.mContent02.getChildAt(0);
                long tempTime1 = 0;
                long tempTime2 = 0;
                long nowTime = System.currentTimeMillis();
                if (child01 != null&&child01.getTag()!=null) {
                    tempTime1 = ((GiftSentInfo) child01.getTag()).getTempTime();
                }
                if (child02 != null&&child02.getTag()!=null) {
                    tempTime2 = ((GiftSentInfo) child02.getTag()).getTempTime();
                }
                if(child01!=null&&child01.getVisibility()==VISIBLE&&nowTime-tempTime1>HIDE_TIME){
                    giftView.hideGiftView(0);
                    sendEmptyMessageDelayed(0,LOOP_TIME);
                    return;
                }
                if(child02!=null&&child02.getVisibility()==VISIBLE&&nowTime-tempTime2>HIDE_TIME){
                    giftView.hideGiftView(1);
                    sendEmptyMessageDelayed(0,LOOP_TIME);
                    return;
                }
                //只有第二个礼物超过最大显示时间
                //给自己继续发消息
                sendEmptyMessageDelayed(0, LOOP_TIME);
            }
        }
    }

}
