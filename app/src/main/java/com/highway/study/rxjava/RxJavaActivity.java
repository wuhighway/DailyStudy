package com.highway.study.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.highway.study.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        // 1、create
//        create();
        // 2、from
//        form();
        // 3、just
//        just();
        // 4、repeat
//        repeat();
        // 5、range
//        range();
//        interval();
//        timmer();
//        filter();
//        take ();
//        distinct();
//        skip();
//        elementAt();
//        map();
//        flatMap1();
        concatMap();

    }


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

    private void repeat() {
        //假如你想对一个Observable重复发射三次数据。例如，我们用just()例子中的Observable：
        //通过添加repeat(3)，just里面的内容会被打印3次
        Observable<String> observableString = Observable.just("i", "lo", "y", "ve", "ch").repeat(3);
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
     * range函数用两个数字作为参数：第一个是起始点，第二个是我们想发射数字的个数
     */
    private void range() {
//        从一个指定的数字x开发发射n个数字
        Observable
                .range(10, 3)
                .repeat(3)
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Integer number) {
                        Log.i(TAG, number + "");
                    }
                });
    }

    /**
     * 一个指定两次发射的时间间隔，另一个是用到的时间单位。这个只会执行一次，添加subscribeOn(Schedulers.newThread())好像能够达到轮训的效果
     */
    private void interval() {
        //interval()函数在你需要创建一个轮询程序时非常好用。
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }
                });
    }

    /**
     * timer 它将3秒后发射0,然后就完成了
     */
    private void timmer() {
        //如果你需要一个一段时间之后才发射的Observable，你可以像下面的例子使用timer()：
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long number) {
                        Log.d(TAG, "I say " + number);
                    }
                });
    }

    /**
     * 通过设置filter，然后在call里面添加s.startsWith("H")，如果是H开头就返回true，
     * 否则返回false。从而能够过滤掉不是h开头的消息，打印出以H开头的消息。
     */
    public void filter() {
        //RxJava让我们使用filter()方法来过滤我们观测序列中不想要的值
        List<String> items = new ArrayList<String>();
        items.add("H1");
        items.add("h10");
        items.add("h100");
        items.add("h200");

        Observable.from(items).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.startsWith("H");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, s);
            }
        });
    }

    /**
     * 通过设置take(3),就可以取出前3个消息，打印出H1,h2,h3
     * 注：如果想从后面取数据，可以调用takeLast(3)取最后3条消息c
     */
    private void take() {
        //take()函数用整数N来作为一个参数，从原始的序列中发射前N个元素，然后完成：
        Observable.just("H1", "h2", "h3", "h4", "h5").take(3).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        });

    }

    /**
     * 我们可以对我们的序列使用distinct()函数去掉重复的。
     * 就像takeLast()一样，distinct()作用于一个完整的序列，然后得到重复的过滤项，它需要记录每一个发射的值。
     */
    private void distinct() {
        Observable.just("H1", "h2", "h2", "h3", "h4", "h5")//.repeat(3)//输入重复3遍
                .distinct().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        });
    }

    /**
     * skip()和skipLast()函数与take()和takeLast()相对应。它们用整数N作参数，从本质上来说，它们不让Observable发射前N个或者后N个值。
     */

    private void skip() {
        // eg:先过滤重复的数据，然后在限制第一个和后两个不能发射，只发射h2、h3
        Observable.just("H1", "h2", "h2", "h3", "h4", "h5")
                .distinct().skip(1).skipLast(2).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        });
    }

    /**
     * ElementAt和elementAtOrDefault
     * elementAt()函数仅从一个序列中发射第n个元素然后就完成了。
     * 如果我们想查找第五个元素但是可观测序列只有三个元素可供发射时该怎么办？我们可以使用elementAtOrDefault()。
     */
    private void elementAt() {
        // 返回序列下标从0 开始
        // eg1 : 发射h4
        // eg2 : 一共只有五个元素，发射第六个无法找到，发射默认 no string
        Observable.just("H1", "h2", "h3", "h4", "h5")
                .distinct()
//                .elementAt(3)
                .elementAtOrDefault(5, "no string")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, s);
                    }
                });
    }

    /**
     * 在Observable后面加一个sample()，我们将创建一个新的可观测序列，
     * 它将在一个指定的时间间隔里由Observable发射最近一次的数值：
     */
    private void sampling() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("i = " + i);
        }
        Observable<String> observable = Observable.from(list);
        observable.sample(50, TimeUnit.MILLISECONDS).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }
        });
        //50毫秒取一次最近的消息进行打印
    }

    /**
     * map
     * 将一个系列的元素通过操作变换成新的元素
     */
    private void map() {
//        Observable.just(1, 2, 3, 4, 5)
//                .map(new Func1<Integer, String>() {
//                    @Override
//                    public String call(Integer integer) {
//                        return "the position is" + integer;
//                    }
//                }).subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, s);
//            }
//        });
//        接收一系列的String，经过map转成integer类型，然后打印出来。
        List<String> items = new ArrayList<String>();
        items.add("H1");
        items.add("h10");
        items.add("h100");
        items.add("h200");
        Observable
                .from(items)
                .map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.startsWith("H") ? 0 : 1;
            }
        })
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, integer + "");
            }
        });
    }

    /**
     * flatMap
     */
    private void flatMap1() {
        Observable.just("上海", "南京").flatMap(new Func1<String, Observable<WeatherInfo>>() {
            @Override
            public Observable<WeatherInfo> call(final String s) {
                return Observable.create(new Observable.OnSubscribe<WeatherInfo>() {
                    @Override
                    public void call(Subscriber<? super WeatherInfo> subscriber) {
                        subscriber.onNext(getWeatherInfo(s));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io()) ; // 时间发生在io
            }
        })
                .observeOn(AndroidSchedulers.mainThread()) // 更新发生在ui
                .subscribe(new Subscriber<WeatherInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        Log.e(TAG, weatherInfo.name);
                    }
                });
    }

    private void concatMap() {
        Observable.just("上海", "苏州").concatMap(new Func1<String, Observable<WeatherInfo>>() {
            @Override
            public Observable<WeatherInfo> call(final String s) {
                return Observable.create(new Observable.OnSubscribe<WeatherInfo>() {
                    @Override
                    public void call(Subscriber<? super WeatherInfo> subscriber) {
                        subscriber.onNext(getWeatherInfo(s));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io());
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<WeatherInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherInfo weatherInfo) {
                Log.e(TAG, weatherInfo.name);
            }
        });
    }


    private Observable<WeatherInfo> getWeather(final String city) {
        Observable<WeatherInfo> observable = Observable.create(new Observable.OnSubscribe<WeatherInfo>() {
            @Override
            public void call(Subscriber<? super WeatherInfo> subscriber) {
                subscriber.onNext(getWeatherInfo(city));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());//网络请求一定要加这句
        return observable;
    }

    private WeatherInfo getWeatherInfo(String city) {
        //模拟网络请求返回weatherinfo
        return new WeatherInfo(city);
    }
    //南昌－>能够获取南昌天气的observable->更新ui


    //新建天气信息类
    class WeatherInfo {
        String name;

        public WeatherInfo(String name) {
            this.name = name;
        }
    }
}
