package com.it520.yingke.bean;

import java.util.List;

/**
 * Created by kay on 16/12/8.
 */
//各类型主播
public class SearchTypesAnchorBean implements TypeBean{
    private  String title;
    private List<LiveBean> lives;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LiveBean> getLives() {
        return lives;
    }

    public void setLives(List<LiveBean> lives) {
        this.lives = lives;
    }


    @Override
    public int getType() {
        return TypeBean.TYPE_SEARCH_ANCHOR_TYPES;
    }
}
