package com.highway.study.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by JH
 * on 2017/3/14.
 */

public class Rxjavade {
    private static final String TAG = "Rxjavade";

    /**
     * cerate
     */
    private void create() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 20; i++) {
                    subscriber.onNext("fuck i is " + i);
                }
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "error");
            }

            @Override
            public void onNext(String o) {
                Log.i(TAG, o);
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * form 操作集合
     */
    private void form() {
        List<String> items = new ArrayList<String>();
        items.add("1");
        items.add("10");
        items.add("100");
        items.add("200");
        Observable<String> observableString = Observable.from(items);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };
        observableString.subscribe(subscriber);
    }

    /**
     * just
     * just()方法可以传入一到九个参数，它们会按照传入的参数的顺序来发射它们。
     * 也可以接受列表或数组，就像from()方法，但是它不会迭代列表发射每个值,它将会发射整个列表。
     * 通常，当我们想发射一组已经定义好的值时会用到它。
     */
    private void just() {
        //通过调用just方法，传入你想发送的数据源，当订阅者进行订阅的时候就开始打印数据
        Observable<String> observableString = Observable.just("i", "lo", "you", "ve", "much", "kkk", "jjj");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };
        observableString.subscribe(subscriber);
    }

    /**
     * repeat.
     */
    void repeat(){
        //假如你想对一个Observable重复发射三次数据。例如，我们用just()例子中的Observable：
        //通过添加repeat(3)，just里面的内容会被打印3次
        Observable<String> observableString = Observable.just("i", "lo", "y","ve", "ch").repeat(3);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        };
        observableString.subscribe(subscriber);

    }



    public static void main(String[] args) throws Exception {
        Rxjavade rxjavade = new Rxjavade();
        rxjavade.repeat();
    }
}
