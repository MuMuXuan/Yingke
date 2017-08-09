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


    private int mHeight = 0;//控件正常的高度

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(getClass().getSimpleName() + "xmg", "onMeasure: " + " widthSize " + widthSize
                + " heightSize " + heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(getClass().getSimpleName() + "xmg", "onLayout: " + " changed " + changed
                + " l " + l + " t " + t + " r " + r + " b " + b);
        if(mHeight==0){
            mHeight = b;
        }
        if(mHeight>b){
            int widthSpec = MeasureSpec.makeMeasureSpec(r, MeasureSpec.EXACTLY);
            int heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
            measure(widthSpec,heightSpec);
            layout(0,0,r,mHeight);
        }
    }

}
