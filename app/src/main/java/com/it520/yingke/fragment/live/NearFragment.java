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
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it520.yingke.R;
import com.it520.yingke.adapter.NearAdapter;
import com.it520.yingke.bean.NearListBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.http.ServiceGenerator;
import com.it520.yingke.http.service.INearService;
import com.it520.yingke.widget.DividerGridItemDecoration;
import com.jcodecraeer.xrecyclerview.SRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearFragment extends Fragment {

    protected View mInflate;
    @BindView(R.id.recyclerView)
    SRecyclerView mRecyclerView;
    protected NearAdapter mNearAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.frag_near, container, false);
        }
        ButterKnife.bind(this, mInflate);

        return mInflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext(),
                getResources().getDrawable(R.drawable.bg_divider)));
        mNearAdapter = new NearAdapter(new ArrayList<TypeBean>());
        mRecyclerView.setAdapter(mNearAdapter);
        requestData();
        mRecyclerView.setLoadingListener(new SRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                requestData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void requestData() {
        INearService service = ServiceGenerator.getSingleton().createService(INearService.class);
        Call<NearListBean> nearInfo = service.getNearInfo();
        nearInfo.enqueue(new Callback<NearListBean>() {
            @Override
            public void onResponse(Call<NearListBean> call, Response<NearListBean> response) {
                mRecyclerView.refreshComplete();
                NearListBean nearListBean = response.body();
                ArrayList<TypeBean> list = new ArrayList<>();
                list.add(new TypeBean() {
                    @Override
                    public int getType() {
                        return TypeBean.TYPE_NEAR_HEAD;
                    }
                });
                list.addAll(nearListBean.getLives());
                mNearAdapter.updateData(list);
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if(mNearAdapter!=null&&mNearAdapter.getItemCount()>0){
                            //考虑有刷新头
                            int itemViewType = mNearAdapter.getItemViewType(position);
                            if(itemViewType==TypeBean.TYPE_NEAR_HEAD){
                                return gridLayoutManager.getSpanCount();
                            }
                        }
                        return 1;
                    }
                });
                mRecyclerView.setLayoutManager(gridLayoutManager);


            }

            @Override
            public void onFailure(Call<NearListBean> call, Throwable throwable) {
                mRecyclerView.refreshComplete();
            }
        });
    }
}
