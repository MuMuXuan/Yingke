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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.it520.yingke.R;
import com.it520.yingke.adapter.HotListAdapter;
import com.it520.yingke.bean.BannerData;
import com.it520.yingke.bean.LiveListBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.http.HotBannerService;
import com.it520.yingke.http.HotLiveService;
import com.it520.yingke.http.RetrofitCallBackWrapper;
import com.it520.yingke.http.ServiceGenerator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class HotFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    protected HotListAdapter mHotListAdapter;

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
        initAdapter();
        initBannerData();
        initLiveData();
    }

    private void initAdapter() {
        ArrayList<TypeBean> list = new ArrayList<>();
        mHotListAdapter = new HotListAdapter(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(mHotListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //ImageLoader.pauseLoader();
                        if (!Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().pause();
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //ImageLoader.resumeLoader();
                        if (Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().resume();
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initBannerData() {
        HotBannerService service = ServiceGenerator.getSingleton().createService(HotBannerService.class);
        Call<BannerData> bannerDatas = service.getBannerDatas();
        bannerDatas.enqueue(new RetrofitCallBackWrapper<BannerData>() {
            @Override
            public void onResponse(BannerData body) {
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body"+body);
                Toast.makeText(getContext(), "获得轮播图成功", Toast.LENGTH_SHORT).show();
                mHotListAdapter.setBannerData(body);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void initLiveData() {
        HotLiveService service = ServiceGenerator.getSingleton().createService(HotLiveService.class);
        Call<LiveListBean> liveData = service.getLiveData();
        liveData.enqueue(new RetrofitCallBackWrapper<LiveListBean>() {
            @Override
            public void onResponse(LiveListBean body) {
                mHotListAdapter.setLiveDataList(body);
                Toast.makeText(getContext(), "请求成功，准备更新UI界面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
