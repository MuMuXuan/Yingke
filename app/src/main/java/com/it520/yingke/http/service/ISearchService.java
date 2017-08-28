package com.it520.yingke.http.service;


import com.it520.yingke.util.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by kay on 16/11/15.
 */
public interface ISearchService {

    @GET(Constant.SEARCH_ALL)
    Call<ResponseBody> getRecommend();

    @GET
    public Call<ResponseBody> getSearchResult(@Url String ur);
}
