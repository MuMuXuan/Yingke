package com.it520.yingke.bean;

import java.util.List;

/*
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-28 16:18 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */
public class ExtraBean {

    /**
     * cl : [0,216,201,1]
     * tab_key : 好声音
     * tab_name : 好声音
     */

    private List<LabelBean> label;

    public List<LabelBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelBean> label) {
        this.label = label;
    }

    public static class LabelBean {
        private String tab_key;
        private String tab_name;
        private List<Integer> cl;

        public String getTab_key() {
            return tab_key;
        }

        public void setTab_key(String tab_key) {
            this.tab_key = tab_key;
        }

        public String getTab_name() {
            return tab_name;
        }

        public void setTab_name(String tab_name) {
            this.tab_name = tab_name;
        }

        public List<Integer> getCl() {
            return cl;
        }

        public void setCl(List<Integer> cl) {
            this.cl = cl;
        }
    }
}
