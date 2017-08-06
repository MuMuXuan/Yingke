package com.it520.yingke.fragment.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.it520.yingke.R;
import com.it520.yingke.adapter.GiftGridViewAdapter;
import com.it520.yingke.bean.GiftBean;
import com.it520.yingke.bean.GiftListBean;
import com.it520.yingke.event.HideGiftShopEvent;
import com.it520.yingke.event.SendGiftEvent;
import com.it520.yingke.http.RetrofitCallBackWrapper;
import com.it520.yingke.http.ServiceGenerator;
import com.it520.yingke.http.service.GiftShopService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-06-21 14:35 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class GiftShopFragment extends Fragment {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.ll_dots)
    LinearLayout mLlDots;
    @BindView(R.id.ll_golds)
    LinearLayout mLlGolds;
    @BindView(R.id.tv_send_store)
    TextView mTvSendStore;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.back)
    View mBack;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    protected Animation mAnimIn;
    protected Animation mAnimOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_gift_shop, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        requestGiftData();
        showContent();
    }

    private void requestGiftData() {
        GiftShopService service = ServiceGenerator.getSingleton().createService(GiftShopService.class);
        Call<GiftListBean> giftsData = service.getGiftsData();
        giftsData.enqueue(new RetrofitCallBackWrapper<GiftListBean>() {
            @Override
            public void onResponse(GiftListBean body) {
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "");
                loadGiftsData(body);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void loadGiftsData(GiftListBean giftListBean) {
        //计算总页数，每页展示8个
        List<GiftBean> allGifts = giftListBean.getGifts();
        //取余数
        int remainder = allGifts.size() % DEFAULT_GIFT_COUNT;//余数
        if (remainder != 0) {
            //补全不够的那一页，放入一些空数据
            for (int i = 0; i < DEFAULT_GIFT_COUNT - remainder; i++) {
                allGifts.add(new GiftBean());
            }
        }
        int pageSize = allGifts.size() / DEFAULT_GIFT_COUNT;
        ArrayList<GridView> gridViewList = new ArrayList<>();
        mGiftGridViewAdapters = new ArrayList<>();//用来保存各页礼物商店的数据适配器
        //准备各页的GridView用于展示
        for (int i = 0; i < pageSize; i++) {
            ArrayList<GiftBean> gifts = getCurrentPageGifts(allGifts, i);
            GridView gridView = generateGridView(gifts);
            gridView.setTag(i);//设置tag标记，方便找出对应的GridView和Adapter
            gridViewList.add(gridView);
        }
        //将各个GridView发给ViewPager来展示
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(gridViewList);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //添加点
        initDot(pageSize);
        //让点默认选中第一个
        setDot(0);
        //默认先选中第一页的第一个礼物，其实这里需要记录和回显的
        GiftGridViewAdapter giftGridViewAdapter = mGiftGridViewAdapters.get(0);
        //默认选中的时候，记录下来这个gift信息
        mSelectedGiftBean = giftGridViewAdapter.updateSelected(0);
    }

    private void initDot(int count) {
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(getActivity());
            dot.setBackgroundResource(R.drawable.dot_selector);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(15, 0, 0, 0);
            mLlDots.addView(dot, p);
        }
    }

    private void setDot(int index) {
        for (int i = 0; i < mLlDots.getChildCount(); i++) {
            ImageView image = (ImageView) mLlDots.getChildAt(i);
            image.setSelected(i == index);
        }
    }

    private ArrayList<GiftGridViewAdapter> mGiftGridViewAdapters;

    private GridView generateGridView(ArrayList<GiftBean> gifts) {
        GridView gridView = new GridView(getContext());
        gridView.setNumColumns(4);
        GiftGridViewAdapter giftGridViewAdapter = new GiftGridViewAdapter(gifts);
        mGiftGridViewAdapters.add(giftGridViewAdapter);
        gridView.setAdapter(giftGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //先取消
                mGiftGridViewAdapters.get(mLastSelectedIndex).cancelAllSelected();
                mLastSelectedIndex = (int) parent.getTag();
                //选中
                GiftGridViewAdapter adapter = (GiftGridViewAdapter) parent.getAdapter();
                mSelectedGiftBean = adapter.updateSelected(position);
            }
        });
        return gridView;
    }

    private int mLastSelectedIndex = 0;//记录最近一次的选中的GridView
    private GiftBean mSelectedGiftBean;//被选中的那个礼物
    public static final int DEFAULT_GIFT_COUNT = 8;

    /**
     * 获得当前页的礼物对应的集合
     *
     * @param allGifts
     * @param currentIndex
     * @return
     */
    private ArrayList<GiftBean> getCurrentPageGifts(List<GiftBean> allGifts, int currentIndex) {
        ArrayList<GiftBean> giftBeanList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_GIFT_COUNT; i++) {
            giftBeanList.add(allGifts.get(currentIndex * DEFAULT_GIFT_COUNT + i));
        }
        return giftBeanList;
    }

    private void init() {
        mAnimIn = AnimationUtils.loadAnimation(getContext(), R.anim.gift_shop_in);
        mAnimOut = AnimationUtils.loadAnimation(getContext(), R.anim.gift_shop_out);
        mAnimOut.setAnimationListener(new MyOutAnimListener());
    }

    public void showContent() {
//        Toast.makeText(getContext(), "TEST", Toast.LENGTH_SHORT).show();
        if (isShowContent())
            return;
        //不可见时，开始可见并播放动画
        mLlContent.setVisibility(View.VISIBLE);
        mLlContent.startAnimation(mAnimIn);
    }

    private boolean isShowContent() {
        return mLlContent.getVisibility() == View.VISIBLE;
    }

    public void hideContent() {
        mLlContent.startAnimation(mAnimOut);
        EventBus.getDefault().post(new HideGiftShopEvent(true));
    }

    public boolean backPressed() {
        if (isShowContent()) {
            hideContent();
            return false;
        }
        //可以关闭当前Activity
        return true;
    }

    @OnClick({R.id.tv_send_store, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_store:
//                Toast.makeText(getContext(), "接下来开始送礼物：" + mSelectedGiftBean.getName(), Toast.LENGTH_SHORT).show();
                sendGift();
                break;
            case R.id.back:
                hideContent();
                break;
        }
    }

    private void sendGift() {
        SendGiftEvent sendGiftEvent = new SendGiftEvent(mSelectedGiftBean);
        EventBus.getDefault().post(sendGiftEvent);
    }

    private class MyOutAnimListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mLlContent.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class MyPagerAdapter extends PagerAdapter {


        ArrayList<GridView> gridViewList;

        public MyPagerAdapter(ArrayList<GridView> gridViewList) {
            this.gridViewList = gridViewList;
        }

        @Override
        public int getCount() {
            return gridViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GridView gridView = gridViewList.get(position);
            container.addView(gridView);
            return gridView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
