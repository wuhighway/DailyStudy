package com.highway.study.service;

/**
 * Created by highway
 * on 2017/2/8.
 * 下载监听
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
