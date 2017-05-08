package com.it520.yingke.fragment.live;

/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-04-26 17:34 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;

public class NearFragment extends Fragment {

    protected View mInflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mInflate==null){
            mInflate = inflater.inflate(R.layout.frag_foucs, container, false);
        }
        return mInflate;
    }

}
