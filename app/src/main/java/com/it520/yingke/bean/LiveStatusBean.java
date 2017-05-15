package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-15 17:01 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class LiveStatusBean {

    @Override
    public String toString() {
        return "LiveStatusBean{" +
                "alive=" + alive +
                ", error_msg='" + error_msg + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * alive : 1
     * dm_error : 0
     * error_msg : 操作成功
     * mker : []
     * status : 1
     */



    private int alive;
    private String error_msg;
    private int status;

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
