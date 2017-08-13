package com.it520.yingke.fragment.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.it520.yingke.R;
import com.it520.yingke.activity.LiveShowActivity;
import com.it520.yingke.adapter.RoomMsgListAdapter;
import com.it520.yingke.adapter.ViewerIconAdapter;
import com.it520.yingke.bean.GiftBean;
import com.it520.yingke.bean.LiveBean;
import com.it520.yingke.bean.ViewerBean;
import com.it520.yingke.bean.ViewerListBean;
import com.it520.yingke.bean.socket.UserBean;
import com.it520.yingke.event.HideGiftShopEvent;
import com.it520.yingke.event.SendGiftEvent;
import com.it520.yingke.http.RetrofitCallBackWrapper;
import com.it520.yingke.http.ServiceGenerator;
import com.it520.yingke.http.service.ViewerServices;
import com.it520.yingke.util.Constant;
import com.it520.yingke.util.JsonUtil;
import com.it520.yingke.util.KeyboardUtil;
import com.it520.yingke.util.UIUtil;
import com.it520.yingke.util.UserManager;
import com.it520.yingke.util.WebSocketManager;
import com.it520.yingke.widget.gift.GiftSentInfo;
import com.it520.yingke.widget.gift.GiftView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.it520.yingke.R.id.edt;
import static com.it520.yingke.util.WebSocketManager.sendMessageToServer;


/* 
 * ============================================================
 * Editor: MuMuXuan(6511631@qq.com)
 *  
 * Time: 2017-05-16 21:01 
 * 
 * Description: 
 *
 * Version: 1.0
 * ============================================================
 */

public class RoomFragment extends Fragment {

    protected ArrayList<LiveBean> mLiveBeanList;
    protected int mCurrentIndex = 0;
    @BindView(R.id.recyclerView_chat)
    RecyclerView mRecyclerViewChat;
    //    @BindView(R.id.recyclerView_gift)
//    RecyclerView mRecyclerViewGift;
    @BindView(R.id.giftView)
    GiftView mGiftView;
    @BindView(R.id.iv_anchor_icon)
    SimpleDraweeView mIvAnchorIcon;
    @BindView(R.id.tv_anchor_name)
    TextView mTvAnchorName;
    @BindView(R.id.tv_online_number)
    TextView mTvOnlineNumber;
    @BindView(R.id.ll_left)
    LinearLayout mLlLeft;
    @BindView(R.id.recyclerView_Viewer)
    RecyclerView mRecyclerViewViewer;
    @BindView(R.id.tv_gold_number)
    TextView mTvGoldNumber;
    @BindView(R.id.card)
    LinearLayout mCard;
    @BindView(R.id.iv_send)
    ImageView mIvSend;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_gift_shop)
    ImageView mIvGiftShop;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    @BindView(R.id.tv_anchor_number)
    TextView mTvAnchorNumber;
    @BindView(R.id.iv_msg_off)
    ImageView mIvMsgOff;
    @BindView(R.id.tv_send_msg)
    TextView mTvSendMsg;
    @BindView(edt)
    EditText mEdt;
    @BindView(R.id.rl_edit)
    RelativeLayout mRlEdit;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    protected ViewerIconAdapter mViewerIconAdapter;
    protected GiftShopFragment mGiftShopFragment;
    public static final String TAG_GIFT_SHOP_FRAGMENT = "giftShopFragment";
    @BindView(R.id.fl_gift_shop)
    FrameLayout mFlGiftShop;
    protected String mRoomId;
    protected RoomMsgListAdapter mRoomMsgListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_room, container, false);
        ButterKnife.bind(this, view);
        mRecyclerViewViewer = ButterKnife.findById(view,R.id.recyclerView_Viewer);
        mRecyclerViewChat = ButterKnife.findById(view,R.id.recyclerView_chat);
        initUI();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName() + "xmg", "onDestroy: " + "");
        clearUI();
        mRoomMsgListAdapter = null;
        mGiftShopFragment = null;
        mLiveBeanList = null;
        mViewerIconAdapter = null;
        mRecyclerViewViewer = null;
        mRecyclerViewChat = null;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showButton(HideGiftShopEvent event) {
        if (event.isHideGiftShop) {
            //展示下面那一排按钮
            mRlBottom.setVisibility(View.VISIBLE);
            //也需要展示聊天列表
            mRecyclerViewChat.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendGift(SendGiftEvent event) {
        GiftBean giftBean = event.getGiftBean();
//        ArrayList<GiftBean> list = new ArrayList<>();
//        list.add(giftBean);
        UserBean currentUser = new UserBean(UserManager.getSingleton().getCurrentUserId());
        currentUser.setGroup(mRoomId);
        currentUser.setGift(giftBean);
        WebSocketManager.sendMessageToServer(JsonUtil.toJson(currentUser));
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManagerChat = new LinearLayoutManager(UIUtil.getContext());
        mRecyclerViewChat.setLayoutManager(linearLayoutManagerChat);
        LinearLayoutManager horLinearLayoutManager = new LinearLayoutManager(UIUtil.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewViewer.setLayoutManager(horLinearLayoutManager);
        ArrayList<ViewerBean> viewerBeenList = new ArrayList<>();
        mViewerIconAdapter = new ViewerIconAdapter(viewerBeenList);
        mRecyclerViewViewer.setAdapter(mViewerIconAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mLiveBeanList = (ArrayList<LiveBean>) arguments.getSerializable(LiveShowActivity.LIVE_SHOW_DATA);
            mCurrentIndex = arguments.getInt(LiveShowActivity.LIVE_SHOW_INDEX);
            mRoomId = mLiveBeanList.get(mCurrentIndex).getId();
        }
        setUI(mLiveBeanList.get(mCurrentIndex));
    }

    private void connectWebSocket() {
        WebSocketManager webSocketManager = WebSocketManager.getInstance();
        //设置回调，一定要在初始化连接和登录之前来设置，不然会有BUG
        webSocketManager.setHandler(new WebSocketManager.SocketHandler() {
            @Override
            public void onOpen() {
                autoLogin(mRoomId);
            }

            @Override
            public void onClose(int code, String reason) {
            }

            @Override
            public void onTextMessage(String payload) {
                //接收到消息以后，需要将其展示到页面上，
                // 判断类型，是礼物类型还是一般的聊天信息
                receivedMessage(payload);
            }
        });

        if (WebSocketManager.isConnect()) {
            //如果已经连接好了，直接登录
            autoLogin(mRoomId);
        }else{
            webSocketManager.init();
        }

    }

    //连接以后自动登录，登录后方便给当前用户分配用户ID，以便发送消息进行聊天
    private void autoLogin(String roomId) {

        UserBean currentUser = new UserBean(UserManager.getSingleton().getCurrentUserId());
        currentUser.setGroup(roomId);
        String s = JsonUtil.toJson(currentUser);
        //发消息
        sendMessageToServer(s);
    }

    //接收到消息以后，需要将其展示到页面上，
    // 判断类型，是礼物类型还是一般的聊天信息
    private void receivedMessage(String payload) {
        Log.e(getClass().getSimpleName() + "xmg", "receivedMessage: " + "");
        UserBean userBean = JsonUtil.parseJson(payload, UserBean.class);
        if (userBean == null) {
            return;
        }
        //判断类型
        int type = userBean.getType();
        if (type == UserBean.SEND_GIFT_TYPE) {
            //有人发送礼物，还要更新一下礼物列表
            updateGiftList(userBean);
        }
        //更新一下消息列表
        updateMsgList(userBean);
    }

    private void updateMsgList(UserBean userBean) {
        if (mRoomMsgListAdapter == null) {
            ArrayList<UserBean> list = new ArrayList<>();
            list.add(userBean);
            mRoomMsgListAdapter = new RoomMsgListAdapter(list);
            Log.e(getClass().getSimpleName() + "xmg", "updateMsgList: " + mRecyclerViewChat);
            mRecyclerViewChat.setAdapter(mRoomMsgListAdapter);
        } else {
            //否则直接新增一个在列表后面
            mRoomMsgListAdapter.addItem(userBean);
            //让列表滚动到最后面的那个item来进行展示
            mRecyclerViewChat.smoothScrollToPosition(mRoomMsgListAdapter.getItemCount() - 1);
        }
    }

    //更新礼物的显示
    private void updateGiftList(UserBean userBean) {
//        GiftBean gift = userBean.getGifts().get(0);
        GiftBean gift = userBean.getGift();
        if (gift == null) {
            return;
        }
        GiftSentInfo giftSentInfo = new GiftSentInfo(userBean.getUserId(), gift.getName());
        giftSentInfo.setGiftIcon(gift.getIcon());
        mGiftView.sendGift(giftSentInfo);
    }

    public void clearUI() {
        mTvAnchorNumber.setText("");
        mIvAnchorIcon.setImageResource(R.drawable.default_head);
        mViewerIconAdapter.setViewerData(new ArrayList<ViewerBean>());
        //新增
        if (mRoomMsgListAdapter != null) {
            mRoomMsgListAdapter.clearAll();
        }
    }

    public void setUI(LiveBean liveBean) {
        //每次都新设置一遍id
        mRoomId = liveBean.getId();
        //重新登录房间
        connectWebSocket();

        //设置映客直播号
        mTvAnchorNumber.setText("映客号：" + liveBean.getCreator().getId());
        //设置主播头像
        String scaledImgUrl = Constant.getScaledImgUrl(liveBean.getCreator().getPortrait(), 100, 100);
        mIvAnchorIcon.setImageURI(scaledImgUrl);
        //设置围观观众
        ViewerServices service = ServiceGenerator.getSingleton().createService(ViewerServices.class);
        Call<ResponseBody> responseBodyCall = service.getViewerData(mRoomId);
        responseBodyCall.enqueue(new RetrofitCallBackWrapper<ResponseBody>() {
            @Override
            public void onResponse(ResponseBody body) {
                Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "");
                try {
                    String string = body.string();
                    ViewerListBean viewerListBean = JsonUtil.parseJson(string, ViewerListBean.class);
                    if (viewerListBean == null || viewerListBean.getUsers() == null || viewerListBean.getUsers().size() == 0) {
                        Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "该主播已经不再直播，或者该主播房间没观众");
                        return;
                    }
                    mViewerIconAdapter.setViewerData(viewerListBean.getUsers());
                    Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "size: " + viewerListBean.getUsers().size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });

    }

    @OnClick({R.id.iv_close, R.id.iv_gift_shop, R.id.iv_send, R.id.tv_send_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                //关闭页面
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.finish();
                }
                break;
            case R.id.iv_gift_shop:
                //点击时，展示礼物商店
                //如果已经初始化，直接调用show，并播放内部动画
                if (mGiftShopFragment == null) {
                    mGiftShopFragment = new GiftShopFragment();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fl_gift_shop, mGiftShopFragment, TAG_GIFT_SHOP_FRAGMENT)
                            .commit();
                } else {
                    mGiftShopFragment.showContent();
                }
                //隐藏下面的一排按钮
                mRlBottom.setVisibility(View.GONE);
                //也需要隐藏聊天列表
                mRecyclerViewChat.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_send:
                //点击了消息发送
                showEditKeyboard();
                break;
            case R.id.tv_send_msg:
                //点击了输入框旁边的发送
                String etString = mEdt.getText().toString();
                mEdt.setText("");
                sendMessage(etString);
                break;

        }
    }

    private void sendMessage(String s) {
        UserBean currentUser = new UserBean(UserManager.getSingleton().getCurrentUserId());
        currentUser.setGroup(mRoomId);
        currentUser.setMsg(s);
        String json = JsonUtil.toJson(currentUser);
        WebSocketManager.sendMessageToServer(json);
    }


    private void showEditKeyboard() {
        mRlBottom.setVisibility(View.GONE);
        mRlEdit.setVisibility(View.VISIBLE);
        mEdt.requestFocus();
        //弹出
        KeyboardUtil.showInputKeyboard(getActivity(), mEdt);
    }

    //让布局进行移动，以便适应键盘
    public void adjustShowKeyboard(int keyboardSize) {
        //通过设置位移y将控件的Y轴进行移动
        Log.e(getClass().getSimpleName() + "xmg", "adjustShowKeyboard: " + keyboardSize);
        translateViews(-keyboardSize);
    }

    public void hideEdit() {
        translateViews(0);

        mRlBottom.setVisibility(View.VISIBLE);
        mRlEdit.setVisibility(View.GONE);
    }

    private void translateViews(float distance) {
        mRlEdit.setTranslationY(distance);
        mLlLeft.setTranslationY(distance);
        mRecyclerViewViewer.setTranslationY(distance);
        mCard.setTranslationY(distance);

        //新增：将聊天列表和礼物列表也位移上来
        //礼物和聊天不需要移动，聊天就直接不用显示了
        mGiftView.setTranslationY(distance);
    }

    /**
     * 用于执行一些关闭view
     *
     * @return 返回为true，则关闭所在的Activity或执行一些super源码
     */
    public boolean backPressed() {
        if (mGiftShopFragment != null) {
            return mGiftShopFragment.backPressed();
        }
        return true;
    }

}
