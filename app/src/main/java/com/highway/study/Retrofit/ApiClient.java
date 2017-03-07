package com.highway.study.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JH
 * on 2017/3/7.
 */

public interface ApiClient {
//    list.add(new BasicNameValuePair("pn", String.valueOf(pageIndex))); // 页数
//    list.add(new BasicNameValuePair("ps", String.valueOf(pageSize))); // 每页多少条
//    list.add(new BasicNameValuePair("oflag", String.valueOf(queryFalg))); // 查询类型
//    /trade/god_proj_list.go
    @GET("/data/app/jczq/new_jczq_hh.xml")
    Call<ResponseBody> getSearchOrder();
}
