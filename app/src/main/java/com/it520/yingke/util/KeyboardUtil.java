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
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
    public static void showInputKeyboard(Activity activity, View currentFocusedView){
        if(activity==null){
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(currentFocusedView, InputMethodManager.SHOW_FORCED);
    }

    public static void closeInputKeyboard(Activity activity){
        if(activity==null){
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager)activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken()
                ,InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
