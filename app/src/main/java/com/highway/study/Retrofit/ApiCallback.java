package com.highway.study.Retrofit;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by JH
 * on 2017/3/8.
 */

public abstract class ApiCallback<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M m) {
        onSuccess(m);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
