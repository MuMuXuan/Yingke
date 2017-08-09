package com.it520.yingke.util;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-07-16 21:15 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    /**展示输入法软键盘
     *
     * @param activity
     * @param currentFocusedView    当前获得焦点了的view
     */
    public static void showInputKeyboard(Activity activity, View currentFocusedView){
        if(activity==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(currentFocusedView, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法键盘
     * @param activity
     */
    public static void closeInputKeyboard(Activity activity){
        if(activity==null){
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager)activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken()
                ,InputMethodManager.HIDE_NOT_ALWAYS);
    }



    private int rootViewVisibleHeight;/*记录根视图的显示高度*/
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    private KeyboardUtil() {
    }

    private static KeyboardUtil sKeyboardUtil = new KeyboardUtil();

    public static KeyboardUtil getInstance(){
        return sKeyboardUtil;
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);
        void keyBoardHide(int height);
    }

    /**
     * 监听软键盘是否打开，通过设置回调用以监听
     * @param activity
     * @param onSoftKeyBoardChangeListener
     */
    public void setListener(Activity activity, final OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        /*获取activity的根视图*/
       final View rootView;/*activity的根视图*/
        rootView = activity.getWindow().getDecorView();
        /*监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变*/
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /*获取当前根视图在屏幕上显示的大小*/
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                /*根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变*/
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }
                /*根视图显示高度变小超过200，可以看作软键盘显示了*/
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                /*根视图显示高度变大超过200，可以看作软键盘隐藏了*/
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        });
        setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }


}
