package com.it520.yingke.bean;

import java.util.List;

/**
 * Created by kay on 16/12/8.
 */

public class SearchUserWarper implements TypeBean{
    //设置给今日推荐用的
    private List<SearchUserBean> users;

    public List<SearchUserBean> getUsers() {
        return users;
    }

    public void setUsers(List<SearchUserBean> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SearchUserWarper{" +
                ", users=" + users +
                '}';
    }

    @Override
    public int getType() {
        return 10086;
    }
}
