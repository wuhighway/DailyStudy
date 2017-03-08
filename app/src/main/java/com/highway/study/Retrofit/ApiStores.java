package com.highway.study.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by JH
 * on 2017/3/7.
 */

public interface ApiStores {
    String API_SERVER_URL = "http://www.weather.com.cn/";
//    list.add(new BasicNameValuePair("pn", String.valueOf(pageIndex))); // 页数
//    list.add(new BasicNameValuePair("ps", String.valueOf(pageSize))); // 每页多少条
//    list.add(new BasicNameValuePair("oflag", String.valueOf(queryFalg))); // 查询类型
//    /trade/god_proj_list.go
    @GET("/data/app/jczq/new_jczq_hh.xml")
    Call<ResponseBody> getSearchOrder();

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Call<MainModel> loadDataByRetrofit(@Path("cityId") String cityId);

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxjava(@Path("cityId") String cityId);
}
