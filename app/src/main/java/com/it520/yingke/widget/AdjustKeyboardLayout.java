package com.it520.yingke.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-07-16 17:44 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class AdjustKeyboardLayout extends RelativeLayout {
    public AdjustKeyboardLayout(Context context) {
        super(context);
    }

    public AdjustKeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private boolean mIsShowKeyboard = false;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.e(getClass().getSimpleName() + "xmg", "onSizeChanged: " + "w "+w
        +" h "+h+" oldw "+oldw+"  oldh "+oldh);
        int reduceSize = oldh - h;
        if(reduceSize>200){
            //认为弹出了软键盘，导致了产生了较大的高度差
            //将尺寸重新改为原来的大小
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(oldh, MeasureSpec.EXACTLY);
            measure(widthMeasureSpec,heightMeasureSpec);
            layout(0,0,w,oldh);
            if(mOnShowKeyboardListener!=null){
                mOnShowKeyboardListener.onShowKeyboard(reduceSize);
            }
            mIsShowKeyboard = true;
        }
    }

    private OnShowKeyboardListener mOnShowKeyboardListener;

    public void setOnResizeListener(OnShowKeyboardListener listener){
        mOnShowKeyboardListener = listener;
    }


    public interface  OnShowKeyboardListener{
        void onShowKeyboard(int keyboardSize);
        void onHideKeyboard();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(getClass().getSimpleName() + "xmg", "onMeasure: " + " widthSize "+widthSize
        +" heightSize "+heightSize);
        if(mIsShowKeyboard){
            mIsShowKeyboard = false;
            if(mOnShowKeyboardListener!=null){
                mOnShowKeyboardListener.onHideKeyboard();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(getClass().getSimpleName() + "xmg", "onLayout: " + " changed "+changed
        +" l "+l+" t "+t+" r "+r+" b "+b);
    }
}
