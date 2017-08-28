package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 23:03 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public interface TypeBean {
    int getType();


    public static int TYPE_HOT_BANNER = 0;
    public static int TYPE_HOT_LIVE = 1;

    public static int TYPE_SEARCH_RECOMMEND = 2;//今日推荐类型
    public static int TYPE_RECOMMEND_TITLE = 3;//今日推荐 标题
    public static int TYPE_SEARCH_ANCHOR_TYPES = 4;//好声音、游戏达人，用户分组类型
}
