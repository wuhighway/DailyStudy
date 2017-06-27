package com.highway.study.Retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.highway.study.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;

public class RetrofitActivity extends AppCompatActivity {

    @Bind(R.id.text)
    TextView textView;
//    private CompositeSubscription mCompositeSubscription;
    private List<Call> calls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onClick() {
        ApiStores apiClient = RetrofitUtil.retrofit().create(ApiStores.class);
//        Call<MainModel> call = apiClient.loadDataByRetrofit("101190201");
//        call.enqueue(new RetrofitCallback<MainModel>() {
//            @Override
//            public void onSuccess(MainModel model) {
//                dataSuccess(model);
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//
//            }
//
//            @Override
//            public void onThrowable(Throwable t) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
//        addSubscription(apiClient.loadDataByRetrofitRxjava("101190201"), new ApiCallback<MainModel>() {
//            @Override
//            public void onSuccess(MainModel model) {
//                dataSuccess(model);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });

//        addCalls(call);
    }

    private void dataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        textView.setText(showData);
    }


//    public void addSubscription(Observable observable, Subscriber subscriber) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
////        mCompositeSubscription.add(observable
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(subscriber));
//    }

//    public void addSubscription(Subscription subscription) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(subscription);
//    }
//
//    public void onUnsubscribe() {
//        //取消注册，以避免内存泄露
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
//            mCompositeSubscription.unsubscribe();
//    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callCancel();
//        onUnsubscribe();
    }
}
