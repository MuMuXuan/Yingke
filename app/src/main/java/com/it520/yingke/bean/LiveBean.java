package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 16:05 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class LiveBean {

    private String city;
    private int group;
    private String id;
    private int link;
    private int multi;
    //外层的底部
    private String name;
    private int online_users;
    private int optimal;
    private int rotate;
    private String share_addr;
    private int slot;
    private String stream_addr;
    private int version;
    private CreatorBean creator;

    @Override
    public String toString() {
        return "LiveBean{" +
                "city='" + city + '\'' +
                ", group=" + group +
                ", id='" + id + '\'' +
                ", link=" + link +
                ", multi=" + multi +
                ", name='" + name + '\'' +
                ", online_users=" + online_users +
                ", optimal=" + optimal +
                ", rotate=" + rotate +
                ", share_addr='" + share_addr + '\'' +
                ", slot=" + slot +
                ", stream_addr='" + stream_addr + '\'' +
                ", version=" + version +
                ", creator=" + creator +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOnline_users() {
        return online_users;
    }

    public void setOnline_users(int online_users) {
        this.online_users = online_users;
    }

    public int getOptimal() {
        return optimal;
    }

    public void setOptimal(int optimal) {
        this.optimal = optimal;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public String getShare_addr() {
        return share_addr;
    }

    public void setShare_addr(String share_addr) {
        this.share_addr = share_addr;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getStream_addr() {
        return stream_addr;
    }

    public void setStream_addr(String stream_addr) {
        this.stream_addr = stream_addr;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }
}
