package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-06-21 15:41 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class GiftBean {


    /**
     * cl : [255,255,255]
     * dyna : 0
     * exp : 12000
     * gold : 1200
     * gold_type : 1
     * icon : MzA5NTIxNDQ2MDg4ODU5.jpg
     * id : 65
     * image : MjkyNzgxNDQ2MjA0NTYz.jpg
     * name : 保时捷
     * second_currency : 0
     * type : 2
     */

    private int exp;//经验
    private int gold;//货币的对应价格
    private int gold_type;//货币类型
    private String icon;//图标
    private int id;
    private String image;
    private String name;//礼物名称
    private int second_currency;//第二类型货币的对应价格
    private int type;

    private boolean isSelected;//是否被选中

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGold_type() {
        return gold_type;
    }

    public void setGold_type(int gold_type) {
        this.gold_type = gold_type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecond_currency() {
        return second_currency;
    }

    public void setSecond_currency(int second_currency) {
        this.second_currency = second_currency;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
