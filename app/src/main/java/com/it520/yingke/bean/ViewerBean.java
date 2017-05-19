package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-17 17:01 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class ViewerBean {

    /**
     * birth : 1996-01-01
     * description :
     * emotion : 单身
     * ext : {"cl":"#FFE042","light":"http://img2.inke.cn/MTQ4ODg3MTU3NTA4MCM4NTEjanBn.jpg"}
     * gender : 0
     * gmutex : 0
     * hometown : 广东省&深圳市
     * id : 285809480
     * inke_verify : 0
     * level : 55
     * location :
     * nick : 萍寶貝家喵喵🐈🙆
     * portrait : http://img2.inke.cn/MTQ5NDc2MDQ2NTM1MiMxNDgjanBn.jpg
     * rank_veri : 8
     * sex : 0
     * third_platform : 0
     * veri_info : 辣妹
     * verified : 0
     * verified_reason :
     */

    private String birth;
    private String emotion;
    /**
     * cl : #FFE042
     * light : http://img2.inke.cn/MTQ4ODg3MTU3NTA4MCM4NTEjanBn.jpg
     */

    private ExtBean ext;
    private int gender;
    private String hometown;
    private int id;
    private int level;
    private String location;
    private String nick;
    private String portrait;
    private int sex;
    private String third_platform;
    private String veri_info;
    private int verified;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public ExtBean getExt() {
        return ext;
    }

    public void setExt(ExtBean ext) {
        this.ext = ext;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getThird_platform() {
        return third_platform;
    }

    public void setThird_platform(String third_platform) {
        this.third_platform = third_platform;
    }

    public String getVeri_info() {
        return veri_info;
    }

    public void setVeri_info(String veri_info) {
        this.veri_info = veri_info;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public static class ExtBean {
        private String cl;
        private String light;

        public String getCl() {
            return cl;
        }

        public void setCl(String cl) {
            this.cl = cl;
        }

        public String getLight() {
            return light;
        }

        public void setLight(String light) {
            this.light = light;
        }
    }
}
