package com.it520.yingke.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.it520.yingke.R;
import com.it520.yingke.adapter.ResultAdapter;
import com.it520.yingke.adapter.SearchAdapter;
import com.it520.yingke.bean.SearchTypesAnchorBean;
import com.it520.yingke.bean.SearchUserBean;
import com.it520.yingke.bean.TypeBean;
import com.it520.yingke.http.ServiceGenerator;
import com.it520.yingke.http.service.ISearchService;
import com.it520.yingke.util.Constant;
import com.it520.yingke.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.it520.yingke.R.id.result;


/**
 * 收索界面
 * Created by kay on 16/12/8.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.searchicon)
    ImageView mSearchicon;
    @BindView(R.id.search)
    EditText mSearch;
    @BindView(R.id.clean)
    ImageView mClean;
    @BindView(R.id.recyle)
    RecyclerView mRecyle;
    @BindView(result)
    RecyclerView mResult;

    ResultAdapter mCancel_adapter;
    protected SearchAdapter mSearchAdapter;
    protected ResultAdapter mResultAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        setListener();
        getAll();
    }


    private void initView() {
        //推荐的栏目的数据集合
        //推荐的用户的数据集合
        mRecyle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //设置一个空的Adapter，后面请求网络了再来更新数据
        mSearchAdapter = new SearchAdapter(new ArrayList<TypeBean>());
        mRecyle.setAdapter(mSearchAdapter);
        mResultAdapter = new ResultAdapter(new ArrayList<SearchUserBean>());
        mResult.setAdapter(mResultAdapter);
    }

    private void setListener() {
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //如果搜索的关键字不为空，就去请求查询接口
                if (!TextUtils.isEmpty(s.toString())) {
                    mClean.setVisibility(View.VISIBLE);
                    doSearch(s.toString());
                } else {
                    mClean.setVisibility(View.GONE);
                    mResult.setVisibility(View.GONE);
                }
            }
        });
        //todo 给Adapter设置点击事件
        /*adapter.setOnClickListener(new SearchAdapter.OnSearchClickListener() {
            @Override
            public void onClickSearchItem(int index) {
                Toast.makeText(getApplication(), "index=" + index, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickSearchOnePicture(View v, int position, LivesBean lives) {
//                Toast.makeText(getApplication(),"position="+position,Toast.LENGTH_SHORT).show();
                if (lives != null) {
                    Intent intent = new Intent();
                    intent.setClass(SearchActivity.this, PlayActivity.class);
                    intent.putExtra(PlayActivity.DATA, lives);
                    startActivity(intent);
                }

            }
        });*/

    }


    public void doSearch(String keyWord) {
        try {
            String par = URLEncoder.encode(keyWord, "utf-8");
            String url = Constant.getSearchKeyword(par);
            Log.e(getClass().getSimpleName() + "xmg", "doSearch: " + "url :"+url);
            // Create a very simple REST adapter which points the GitHub API endpoint.
            ISearchService client = ServiceGenerator.getSingleton().createService(ISearchService.class);

            Call<ResponseBody> call = client.getSearchResult(url);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String json = response.body().string();
                            if (TextUtils.isEmpty(json)) {
                                return;
                            }
                            ArrayList<SearchUserBean> result = new ArrayList<>();
                            try {

                                JSONObject js = new JSONObject(json);

                                JSONArray users = js.getJSONArray("users");

                                for (int i = 0; i < users.length(); i++) {
                                    JSONObject tmp = users.getJSONObject(i);
                                    SearchUserBean bean = JsonUtil.parseJson(tmp.toString(), SearchUserBean.class);
                                    result.add(bean);
                                }
                                //展示查询结果
                                showSearchResult(result);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void submit() {
        // validate
        String searchString = mSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchString)) {
            Toast.makeText(this, "请输入内容！！！", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //获取所有的推荐的数据
    private void getAll() {
        ISearchService client = ServiceGenerator.getSingleton().createService(ISearchService.class);
        Call<ResponseBody> call = client.getRecommend();
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        JSONObject js = new JSONObject(json);
                        JSONArray live = js.getJSONArray("live_nodes");
                        JSONArray user_nodes = js.getJSONArray("user_nodes");
                        //只拿今日推荐
                        JSONObject jsonObject = user_nodes.getJSONObject(0);
                        JSONArray users = jsonObject.optJSONArray("users");

                        ArrayList<SearchTypesAnchorBean> searchTypesAnchorBeanList = new ArrayList<>();
                        for (int i = 0; i < live.length(); i++) {
                            JSONObject tmp = live.optJSONObject(i);
                            SearchTypesAnchorBean typesAnchorBean = JsonUtil.parseJson(tmp.toString(), SearchTypesAnchorBean.class);
                            searchTypesAnchorBeanList.add(typesAnchorBean);
                        }

                        ArrayList<SearchUserBean> searchUserBeanList = new ArrayList<>();
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject tmp = users.optJSONObject(i);
                            SearchUserBean searchUserBean = JsonUtil.parseJson(tmp.toString(), SearchUserBean.class);
                            searchUserBeanList.add(searchUserBean);
                        }
                        Log.e(getClass().getSimpleName() + "xmg", "onResponse: " + "");
                        //这里拿到了上下两块数据（类型主播和每日推荐主播）
                        ArrayList<TypeBean> typeBeanList = new ArrayList<>();
                        typeBeanList.addAll(searchTypesAnchorBeanList);
                        typeBeanList.add(new TypeBean() {
                            @Override
                            public int getType() {
                                return TypeBean.TYPE_RECOMMEND_TITLE;
                            }
                        });
                        typeBeanList.addAll(searchUserBeanList);
                        mSearchAdapter.updateDatas(typeBeanList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });
    }

    //显示查找结果列表
    public void showSearchResult(ArrayList<SearchUserBean> resultList) {
        if(resultList!=null){
            mResult.setVisibility(View.VISIBLE);
            mResultAdapter.updateData(resultList);
        }
    }

    @OnClick({R.id.cancel, R.id.clean})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                mSearch.setText("");
                break;
            case R.id.clean:
                mSearch.setText("");
                break;
        }
    }

}
