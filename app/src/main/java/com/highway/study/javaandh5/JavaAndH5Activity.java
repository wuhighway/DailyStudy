package com.highway.study.javaandh5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.highway.study.R;

/**
 * webview和h5交互
 *
 * js调用java时，调用方法运行在名为JavaBridge的线程中，
 * 如果在调用的方法中调用webview的loadUrl()必须运行在主线程中
 * 所以要切换线程
 * <p>代码混淆时，记得保持 JavascriptInterface 内容，在 proguard 文件中添加如下类似规则 (有关类名按需修改)：
 * keepattributes *Annotation*
 * keepattributes JavascriptInterface
 * -keep public class com.mypackage.MyClass$MyJavaScriptInterface
 * -keep public class * implements com.mypackage.MyClass$MyJavaScriptInterface
 * -keepclassmembers class com.mypackage.MyClass$MyJavaScriptInterface {
 * <methods>;
 * }
 * loadUrl() note see {@link android.webkit.WebView#evaluateJavascript}
 * @author JH
 * @date 2017/4/20
 */
public class JavaAndH5Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "JavaAndH5Activity";
    private WebView webView;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_h5);
        webView = (WebView) findViewById(R.id.click);
//        WebView.setOnClickListener(this);
        initWebView();
        // Android Thread[main,5,main]
        Log.e(TAG, "Android " + Thread.currentThread().toString());
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new AndroidAndJsInterface(), "Android");
//        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        webView.loadUrl("file:///android_asset/readmode.html");
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.click:
//                setContentView(webView);
//                webView.loadUrl("javascript:javaCallJs(" + "'" + this.getClass().getSimpleName() + "'" + ")");
//                mButton.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadUrl("javascript:showDialog()");
//                    }
//                }, 3000);
//
//                break;
//        }
    }

    class AndroidAndJsInterface {

        @JavascriptInterface
        public void showToast() {
            Toast.makeText(JavaAndH5Activity.this, "调用android", Toast.LENGTH_LONG).show();
//            webView.loadUrl("javascript:javaCallJs("+")");
        }

        /**
         * 该方法将被js调用,用于加载数据
         */
        @JavascriptInterface
        public void showcontacts() {
            // Thread[JavaBridge,5,main]
            Log.e(TAG, "js " + Thread.currentThread().toString());
            // 下面的代码建议在子线程中调用
            webView.post(new Runnable() {
                @Override
                public void run() {
                    String json = "[{\"name\":\"siri\", \"phone\":\"18600012345\"}]";
                    // 调用JS中的方法
                    webView.loadUrl("javascript:show('" + json + "')");
                }
            });

        }

        /**
         * 拨打电话
         *
         * @param phone
         */
        @JavascriptInterface
        public void call(String phone) {
            JavaAndH5Activity.this.phone = phone;

            webView.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + JavaAndH5Activity.this.phone));
                    if (ActivityCompat.checkSelfPermission(JavaAndH5Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        JavaAndH5Activity.this.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack(); // 返回上一次加载页面
        } else {
            finish();
        }
//        super.onBackPressed();
    }
}
