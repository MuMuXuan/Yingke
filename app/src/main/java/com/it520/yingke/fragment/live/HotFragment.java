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

import android.content.Intent;
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

import com.it520.yingke.R;
import com.it520.yingke.activity.LiveShowActivity;
import com.it520.yingke.adapter.HotListAdapter;
import com.it520.yingke.bean.BannerData;
import com.it520.yingke.bean.LiveListBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.http.service.HotBannerService;
import com.it520.yingke.http.service.HotLiveService;
import com.it520.yingke.http.RetrofitCallBackWrapper;
import com.it520.yingke.http.ServiceGenerator;
import com.it520.yingke.util.UIUtil;
import com.it520.yingke.util.imageLoader.ImageLoaderUtil;
import com.jcodecraeer.xrecyclerview.SRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class HotFragment extends Fragment {

    @BindView(R.id.recyclerView)
    SRecyclerView mRecyclerView;
    protected HotListAdapter mHotListAdapter;
    protected View mInflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mInflate==null){
            mInflate = inflater.inflate(R.layout.frag_hot, container, false);
            ButterKnife.bind(this, mInflate);
        }
        return mInflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState==null){
            initData();
        }else{
            Toast.makeText(UIUtil.getContext(), "回显", Toast.LENGTH_SHORT).show();
        }

    }

    private void initData() {
        initAdapter();
        requestBannerData();
        requestLiveData();
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
                        ImageLoaderUtil.getSingleton().pause();
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //ImageLoader.resumeLoader();
                        ImageLoaderUtil.getSingleton().restart();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mRecyclerView.setLoadingListener(new SRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestLiveData();
                requestBannerData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        //设置条目点击
        mHotListAdapter.setOnLiveItemClickListener(new HotListAdapter.OnLiveItemClickListener() {
            @Override
            public void onLiveItemClick(int position, View view) {
                ArrayList<TypeBean> data = mHotListAdapter.getData();
//                LiveBean liveBean = (LiveBean) data.get(position);
                //去掉第一条的轮播图
                ArrayList<TypeBean> liveBeanArrayList = new ArrayList<>(data);
                liveBeanArrayList.remove(0);
                Intent intent = new Intent(UIUtil.getContext(), LiveShowActivity.class);
                intent.putExtra(LiveShowActivity.LIVE_SHOW_DATA,liveBeanArrayList);
                //位置减去轮播图那一条
                intent.putExtra(LiveShowActivity.LIVE_SHOW_INDEX,position-1);
                startActivity(intent);
            }
        });
    }

    private void requestBannerData() {
        HotBannerService service = ServiceGenerator.getSingleton().createService(HotBannerService.class);
        Call<BannerData> bannerDatas = service.getBannerDatas();
        bannerDatas.enqueue(new RetrofitCallBackWrapper<BannerData>() {
            @Override
            public void onResponse(BannerData body) {
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "body"+body);
//                Toast.makeText(getContext(), "获得轮播图成功", Toast.LENGTH_SHORT).show();
                mHotListAdapter.setBannerData(body);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void requestLiveData() {
        HotLiveService service = ServiceGenerator.getSingleton().createService(HotLiveService.class);
        Call<LiveListBean> liveData = service.getLiveData();
        liveData.enqueue(new RetrofitCallBackWrapper<LiveListBean>() {
            @Override
            public void onResponse(LiveListBean body) {
                mHotListAdapter.setLiveDataList(body);
                mRecyclerView.refreshComplete();
//                Toast.makeText(getContext(), "请求成功，准备更新UI界面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
