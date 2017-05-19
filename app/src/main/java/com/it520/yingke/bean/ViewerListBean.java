package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-17 16:59 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import java.util.ArrayList;

public class ViewerListBean {
    String dm_error;
    String error_msg;
    String total;
    ArrayList<ViewerBean> users;

    public String getDm_error() {
        return dm_error;
    }

    public void setDm_error(String dm_error) {
        this.dm_error = dm_error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<ViewerBean> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<ViewerBean> users) {
        this.users = users;
    }
}
