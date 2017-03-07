package com.highway.study.Retrofit;

import com.highway.study.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by JH
 * on 2017/3/7.
 */

public class RetrofitUtil {

    private static final String TAG = "RetrofitUtil";
//    public static final String BASE_URL = Config.getInstance(CaiYi.getInstance()).getDomain();
    public static final String BASE_URL = "";

    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;

    private static void initClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (builder.interceptors() != null){
            builder.interceptors().clear();
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                // 添加固定参数
                HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
//                        .addQueryParameter("appversion", Utility.getVersionCode(CaiYi.getInstance()) + "")
//                        .addQueryParameter("source", Config.getInstance(CaiYi.getInstance()).getSource())
//                        .addQueryParameter("accesstoken", Config.getInstance(CaiYi.getInstance()).getToken())
//                        .addQueryParameter("appid", Config.getInstance(CaiYi.getInstance()).getAppId())
                        .addQueryParameter("logintype", "1")
                        .addQueryParameter("mtype", "1")
//                        .addQueryParameter("rversion", Utility.getVersionName(CaiYi.getInstance()))
//                        .addQueryParameter("imei", Utility.getUid(CaiYi.getInstance()))
//                        .addQueryParameter("osversion" , Config.getInstance(CaiYi.getInstance()).getMobileReleaseVersion())
                        ;

                // 新的请求
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(authorizedUrlBuilder.build())
                        .addHeader("Accept-Encoding", "gzip") // 添加头部信息
                        .build();

                // 获取返回头部信息
                Response response = chain.proceed(newRequest);
                // 处理响应头部信息
                String time = response.header("THE-TIME", null);
//                Logger.e(TAG, time);
                return response;
            }
        }) // 设置超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        //DEBUG模式下配Log
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        mOkHttpClient = builder.build();
    }

    public static Retrofit retrofit(){
        if (mRetrofit == null){
            initClient();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }


//    ApiClient apiClient = RetrofitUtil.retrofit().create(ApiClient.class);
//    Call<ResponseBody> call = apiClient.getSearchOrder();
//    call.enqueue(new Callback<ResponseBody>() {
//        @Override
//        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//            try {
//                InputStream inputStream = new GZIPInputStream(response.body().byteStream());
////                    String content = Utility.getStringFromInput(inputStream);
////                    Logger.e(TAG, "getdata = " + content);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<ResponseBody> call, Throwable t) {
////                Logger.e(TAG, t.toString());
//        }
//    });
//}
}
