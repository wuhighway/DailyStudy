package com.highway.study.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author JH
 * @date 2017/8/11
 */


class LoaderResult {
    String uri;
    ImageView imageView;
    Bitmap bitmap;

    public LoaderResult(String uri, ImageView imageView, Bitmap bitmap) {
        this.uri = uri;
        this.imageView = imageView;
        this.bitmap = bitmap;
    }
}
