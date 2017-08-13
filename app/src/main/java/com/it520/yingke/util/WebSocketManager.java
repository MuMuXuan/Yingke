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

import android.util.Log;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class WebSocketManager {

    private static boolean sIsInited = false;
    private static WebSocketConnection sConnection;

    private WebSocketManager() {
    }

    private static volatile WebSocketManager sInstance = new WebSocketManager();

    public static WebSocketManager getInstance(){
        return sInstance;
    }

    public void init(){
        if(sIsInited){
            return;
        }
        sIsInited = true;
        try {
            sConnection = new WebSocketConnection();
            sConnection.connect(Constant.WSURL,new WebSocketHandler(){
                @Override
                public void onOpen() {
                    Log.e(getClass().getSimpleName() + "xmg", "onOpen: " + "WebSocket打开连接");
                    if(mSocketHandler!=null){
                        mSocketHandler.onOpen();
                    }
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.e(getClass().getSimpleName() + "xmg", "onClose: " + "连接关闭");
                    if(mSocketHandler!=null){
                        mSocketHandler.onClose(code,reason);
                    }
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.e(getClass().getSimpleName() + "xmg", "onTextMessage: " + "接收到消息: "+payload);
                    if(mSocketHandler!=null){
                        mSocketHandler.onTextMessage(payload);
                    }
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName() + "xmg", "init: " +e.getMessage());
        }
    }

    private SocketHandler mSocketHandler;
    public void setHandler(SocketHandler handler){
        mSocketHandler = handler;
    }

    //发送消息
    public static void sendMessageToServer(String s) {
        if(!sIsInited){
            return;
        }
        if(sConnection!=null&&sConnection.isConnected()){
            Log.e( "xmg", "sendMessageToServer: " + s);
            sConnection.sendTextMessage(s);
        }
    }

    public static boolean isConnect(){
        if(sConnection!=null&&sConnection.isConnected()){
            return true;
        }
        return false;
    }

    public interface SocketHandler{
         void onOpen();
         void onClose(int code, String reason);
         void onTextMessage(String payload);
    }
}
