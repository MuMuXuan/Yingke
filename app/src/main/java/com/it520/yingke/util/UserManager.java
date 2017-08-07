package com.it520.yingke.util;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-05 15:25 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.util.Random;

public class UserManager {

    protected static String sUserName;

    private UserManager() {
        sUserName = "U"+(int)(100000*new Random().nextDouble());
    }

    public String getCurrentUserId() {
        return sUserName;
    }

    private static volatile UserManager sInstance;

    public static UserManager getSingleton() {
        if (sInstance == null) {
            synchronized (UserManager.class) {
                if (sInstance == null) {
                    sInstance = new UserManager();
                }
            }
        }
        return sInstance;
    }
}
