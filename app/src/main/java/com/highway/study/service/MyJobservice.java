package com.highway.study.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.highway.study.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by JH
 * on 2017/2/10.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobservice extends JobService {
    private final static String TAG="MyJobService";

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public boolean onStartJob(JobParameters params) {

        if(isNetworkConnected()){
//            new WebDownLoadTask().execute(params);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("http://blog.csdn.net/qq_31726827/article/details/50462025"));
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            Notification notification = builder
                    .setContentTitle("这是通知标题")
                    .setContentText("这是通知内容")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .build();
            manager.notify(1, notification);
            jobFinished(params,false);
            Log.e(TAG, "success");
            return true;
        }
        Log.e(TAG, "fail");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private boolean isNetworkConnected(){

        ConnectivityManager connManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());

    }

//    private class WebDownLoadTask extends AsyncTask<JobParameters,Void,String> {
//
//        protected  JobParameters mJobParam;
//
//        @Override
//        protected String doInBackground(JobParameters... params) {
//            mJobParam=params[0];
//            try{
//
//                InputStream is=null;
//                int len=50;
//                URL url=new URL("https://www.baidu.com");
//                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
//                conn.setReadTimeout(10000);
//                conn.setConnectTimeout(15000);
//                conn.setRequestMethod("GET");
//
//                conn.connect();
//                int response=conn.getResponseCode();
//                is=conn.getInputStream();
//
//                Reader reader=null;
//                reader=new InputStreamReader(is,"UTF-8");
//                char[] buffer=new char[len];
//                reader.read(buffer);
//
//                return new String(buffer);
//
//            }catch (IOException e){
//
//            }
//
//            return "";
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            jobFinished(mJobParam,false);
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
