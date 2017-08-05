package com.it520.yingke.util;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-05 14:33 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import de.tavendo.autobahn.WebSocketConnection;

public class WebSocketManager {
    private static WebSocketConnection sConnection;

    private WebSocketManager() {
        sConnection = new WebSocketConnection();
    }

    private static volatile WebSocketManager sInstance;

    public static WebSocketManager getSingleton() {
        if (sInstance == null) {
            synchronized (WebSocketManager.class) {
                if (sInstance == null) {
                    sInstance = new WebSocketManager();
                }
            }
        }
        return sInstance;
    }

    public WebSocketConnection getConnect(){
        return sConnection;
    }

    //发送消息
    public void sendMessageToServer(String s) {
        if(sConnection!=null&&sConnection.isConnected()){
            sConnection.sendTextMessage(s);
        }
    }

}
