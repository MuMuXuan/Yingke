package com.it520.yingke.widget;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-25 20:49 
 * 
 * Description: 向下滑动时，隐藏底部布局，反之则显示，
 * 如果滑动距离不够完全隐藏或显示，会自动回滚到完全隐藏或显示
 *
 * Version: 1.0
 * ============================================================
 */

public class AutoHideBottomLayout extends RelativeLayout {

    protected View mBottom;
    protected int mMeasuredHeight;
    protected Scroller mScroller;

    public AutoHideBottomLayout(Context context) {
        this(context, null);

    }

    public AutoHideBottomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoHideBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount > 1) {
            mBottom = getChildAt(1);
        }
        //如果Bottom中设置了高度为wrap_content且设置了clipChildren为false，那这里取高度将不能取到完全的高度
        mMeasuredHeight = mBottom.getMeasuredHeight();
        Log.e(getClass().getSimpleName() + "xmg", "onMeasure: " + "mMeasuredHeight:" + mMeasuredHeight);
    }

    float mStartX = 0;
    float mStartY = 0;

    int[] mBottomLocation = new int[2];
    boolean mShouldIntercept;//是否需要拦截
    boolean mIsNeedAutoHide;//是否需要滚动时进行隐藏

    public void setNeedAutoHide(boolean b){
        mIsNeedAutoHide = b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(!mIsNeedAutoHide){
            //如果不需要自动滚动，则直接返回
            return super.dispatchTouchEvent(event);
        }

        int eventAction = event.getAction();
        int scrollY = mBottom.getScrollY();
        int scrollX = mBottom.getScrollX();
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                //如果点击落在底部BottomView上，后续的touch就不再做各种复杂逻辑，直接返回super
                mShouldIntercept = shouldIntercept(event, scrollY, scrollX);
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(mShouldIntercept){
                    //在移动时，如果需要拦截，直接return super
                    return super.dispatchTouchEvent(event);
                }
                float newX = event.getX();
                float newY = event.getY();
                float dx = newX - mStartX;
                float dy = newY - mStartY;
                //// TODO:根据位移，将底部进行隐藏
                scrollY = mBottom.getScrollY();
                if(scrollY>0&&dy<0||scrollY<-mMeasuredHeight&&dy>0){
                    //早已经超限制了，无需再scroll
                    return super.dispatchTouchEvent(event);
                }
                mBottom.scrollBy(0, (int) dy);
                //判断边界条件
                scrollY = mBottom.getScrollY();
                if(scrollY>0){
                    //不能再往上滚动了
                    mBottom.scrollTo(0,0);
                }
                if(scrollY<-mMeasuredHeight){
                    //已经移动到看不见了
                    mBottom.scrollTo(0,-mMeasuredHeight);
                }
                mStartX = newX;
                mStartY = newY;
                break;
            case MotionEvent.ACTION_UP:
                //如果抬起时，滚动的距离不够，则自动滚动
                scrollY = mBottom.getScrollY();
                if(scrollY==0||scrollY==-mMeasuredHeight){
                    //无需自动滚动
                    return super.dispatchTouchEvent(event);
                }
                if(scrollY<-mMeasuredHeight/2){
                    //隐藏了超过一半，完全隐藏
                    mScroller.startScroll(0,scrollY,0,-mMeasuredHeight-scrollY);
                }else{
                    //完全展示
                    mScroller.startScroll(0,scrollY,0,0-scrollY);
                }
                postInvalidate();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            mBottom.scrollTo(currX,currY);
            postInvalidate();
        }
    }

    private boolean shouldIntercept(MotionEvent event, int scrollY, int scrollX) {
        mBottom.getLocationOnScreen(mBottomLocation);
        RectF rectF = new RectF(mBottomLocation[0] - scrollX,
                mBottomLocation[1] - scrollY,
                mBottomLocation[0] - scrollX + mBottom.getMeasuredWidth(),
                mBottomLocation[1] - scrollY + mBottom.getMeasuredHeight());
        Log.e(getClass().getSimpleName() + "xmg", "dispatchTouchEvent: " + "rectF:" +
                rectF.toString());
        Log.e(getClass().getSimpleName() + "xmg", "dispatchTouchEvent: " + "event.getX():" +
                event.getX() + "event.getY():" + event.getY());
        if(rectF.contains(event.getX(),event.getY())){
            return true;
        }
        return false;
    }

}
