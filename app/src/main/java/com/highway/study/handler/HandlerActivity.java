package com.highway.study.handler;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.highway.study.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
    }

    private void newThreadHandler() {
        new Thread("thread2"){
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler();
                Looper.loop();
            }
        };
    }

    /**
     * FixedThreadPool: 只有核心线程并且这些核心线程不会被回收
     * 核心线程没有超时机制，任务队列没有大小限制
     */
    private void createFixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
    }

    /**
     * CachedThreadPool：只有非核心线程，并且最大线程数为Integer.MAX_VALUE
     * 当线程池中的线程都处于活动状态时，线程池会创建新的线程来处理新任务，否则就会利用空闲的线程处理新任务
     * 适合执行大量的好事较少的任务，当整个线程池都处于闲置状态时，线程池中的线程都会超时而被停止
     */
    private void createCacheThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

    /**
     * ScheduleThreadPool:核心线程是固定的，非核心线程数量没有限制，当非核心线程被闲置时会立即回收
     * 用于执行定时任务或者具有固定周期的重复任务
     */
    private void createScheduleThreadPool() {
        ExecutorService executorService = Executors.newScheduledThreadPool(1);
    }

    /**
     * SingleThreadExecutor: 只有一个核心线程， 确保所有的任务在同一线程中顺序执行
     */
    private void createSingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    }

}
