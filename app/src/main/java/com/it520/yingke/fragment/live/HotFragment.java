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
import android.support.v7.widget.RecyclerView;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.bean.LiveDatas;
import com.it520.yingke.http.HotLiveService;
import com.it520.yingke.http.ServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_hot, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        initLiveData();
    }

    private void initLiveData() {
        HotLiveService service = ServiceGenerator.getSingleton().createService(HotLiveService.class);
        Call<LiveDatas> liveDatas = service.getLiveDatas();
        liveDatas.enqueue(new Callback<LiveDatas>() {
            @Override
            public void onResponse(Call<LiveDatas> call, Response<LiveDatas> response) {
                if(!response.isSuccessful()){
                    onFailure(null,new AndroidException("请求失败"));
                }
                LiveDatas body = response.body();
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body "+body);
            }

            @Override
            public void onFailure(Call<LiveDatas> call, Throwable throwable) {
                Log.e(getClass().getSimpleName() + "xmg", "onFailure: " + "请求失败"+throwable.getMessage());
            }
        });
    }
}
