package com.it520.yingke.bean;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-27 22:42 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import static com.it520.yingke.bean.TypeBean.TYPE_HOT_BANNER;

public class BannerBean {

    /**
     * atom : 0
     * image : http://img2.inke.cn/MTQ5MzAwMzI2MDY0MSM1NDUjanBn.jpg
     * link : http://act.inke.cn/banner/201704/cherry-anchor/cherry-anchor.html?from=banner&n_e=1&web_entrance_id=1
     */

    private int atom;
    private String image;
    private String link;

    public int getAtom() {
        return atom;
    }

    public void setAtom(int atom) {
        this.atom = atom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
