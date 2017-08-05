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

import com.it520.yingke.bean.socket.UserBean;

import java.util.Random;

public class UserManager {
    private static UserBean sCurrentUser;

    private UserManager() {
        sCurrentUser = new UserBean();
        String userName = "U"+(int)(100000*new Random().nextDouble());
        sCurrentUser.setUserName(userName);
    }

    public UserBean getCurrentUser() {
        return sCurrentUser;
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
