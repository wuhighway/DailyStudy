package com.highway.study.Retrofit;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JH
 * on 2017/3/8.
 * 请求回调
 */

public abstract class RetrofitCallback<M> implements Callback<M> {
    private static final String TAG = "RetrofitCallback";
    public abstract void onSuccess(M model);

    public abstract void onFailure(int code, String msg);

    public abstract void onThrowable(Throwable t);

    public abstract void onFinish();
    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        if (response.isSuccessful()) {
            Log.e(TAG, response.raw().request().url().url().toString());
            Log.e(TAG, response.toString());
            onSuccess(response.body());
        } else {
            onFailure(response.code(), response.errorBody().toString());
        }
        onFinish();
    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        onThrowable(t);
        onFinish();
    }
}
