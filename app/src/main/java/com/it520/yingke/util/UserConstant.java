package com.it520.yingke.util;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-08-24 16:21 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import com.it520.yingke.R;

public class UserConstant {

    //获得等级对应的图片资源
    public static int getRank(int level) {
        //超过范围统统设置为等级1的图片
        if (level < 0 || level > 127) {
            return R.drawable.rank_1;
        }
        return UserConstant.sRank[level - 1];
    }

    public static final int[] sRank = {
            R.drawable.rank_1,
            R.drawable.rank_2,
            R.drawable.rank_3,
            R.drawable.rank_4,
            R.drawable.rank_5,
            R.drawable.rank_6,
            R.drawable.rank_7,
            R.drawable.rank_8,
            R.drawable.rank_9,
            R.drawable.rank_10,
            R.drawable.rank_11,
            R.drawable.rank_12,
            R.drawable.rank_13,
            R.drawable.rank_14,
            R.drawable.rank_15,
            R.drawable.rank_16,
            R.drawable.rank_17,
            R.drawable.rank_18,
            R.drawable.rank_19,
            R.drawable.rank_20,
            R.drawable.rank_21,
            R.drawable.rank_22,
            R.drawable.rank_23,
            R.drawable.rank_24,
            R.drawable.rank_25,
            R.drawable.rank_26,
            R.drawable.rank_27,
            R.drawable.rank_28,
            R.drawable.rank_29,
            R.drawable.rank_30,
            R.drawable.rank_31,
            R.drawable.rank_32,
            R.drawable.rank_33,
            R.drawable.rank_34,
            R.drawable.rank_35,
            R.drawable.rank_36,
            R.drawable.rank_37,
            R.drawable.rank_38,
            R.drawable.rank_39,
            R.drawable.rank_40,
            R.drawable.rank_41,
            R.drawable.rank_42,
            R.drawable.rank_43,
            R.drawable.rank_44,
            R.drawable.rank_45,
            R.drawable.rank_46,
            R.drawable.rank_47,
            R.drawable.rank_48,
            R.drawable.rank_49,
            R.drawable.rank_50,
            R.drawable.rank_51,
            R.drawable.rank_52,
            R.drawable.rank_53,
            R.drawable.rank_54,
            R.drawable.rank_55,
            R.drawable.rank_56,
            R.drawable.rank_57,
            R.drawable.rank_58,
            R.drawable.rank_59,
            R.drawable.rank_60,
            R.drawable.rank_61,
            R.drawable.rank_62,
            R.drawable.rank_63,
            R.drawable.rank_64,
            R.drawable.rank_65,
            R.drawable.rank_66,
            R.drawable.rank_67,
            R.drawable.rank_68,
            R.drawable.rank_69,
            R.drawable.rank_70,
            R.drawable.rank_71,
            R.drawable.rank_72,
            R.drawable.rank_73,
            R.drawable.rank_74,
            R.drawable.rank_75,
            R.drawable.rank_76,
            R.drawable.rank_77,
            R.drawable.rank_78,
            R.drawable.rank_79,
            R.drawable.rank_80,
            R.drawable.rank_81,
            R.drawable.rank_82,
            R.drawable.rank_83,
            R.drawable.rank_84,
            R.drawable.rank_85,
            R.drawable.rank_86,
            R.drawable.rank_87,
            R.drawable.rank_88,
            R.drawable.rank_89,
            R.drawable.rank_90,
            R.drawable.rank_91,
            R.drawable.rank_92,
            R.drawable.rank_93,
            R.drawable.rank_94,
            R.drawable.rank_95,
            R.drawable.rank_96,
            R.drawable.rank_97,
            R.drawable.rank_98,
            R.drawable.rank_99,
            R.drawable.rank_100,
            R.drawable.rank_101,
            R.drawable.rank_102,
            R.drawable.rank_103,
            R.drawable.rank_104,
            R.drawable.rank_105,
            R.drawable.rank_106,
            R.drawable.rank_107,
            R.drawable.rank_108,
            R.drawable.rank_109,
            R.drawable.rank_110,
            R.drawable.rank_111,
            R.drawable.rank_112,
            R.drawable.rank_113,
            R.drawable.rank_114,
            R.drawable.rank_115,
            R.drawable.rank_116,
            R.drawable.rank_117,
            R.drawable.rank_118,
            R.drawable.rank_119,
            R.drawable.rank_120,
            R.drawable.rank_121,
            R.drawable.rank_122,
            R.drawable.rank_123,
            R.drawable.rank_124,
            R.drawable.rank_125,
            R.drawable.rank_126,
            R.drawable.rank_127,
            R.drawable.rank_128
    };
}
