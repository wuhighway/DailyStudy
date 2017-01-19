package com.highway.study.takephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.highway.study.util.FileUtils;

import java.io.File;

/**
 * Created by JH
 * on 2017/1/18.
 */

public final class PhotoUtils {
    private static final String TAG = "PhotoUtils";
    private static final int ALBUM_WITH_DATA = 1001;//相册
    private static final int CAMERA_WITH_DATA = 1002;//相机
    private static final int CROP_PHOTO = 101; // 裁剪
    private static final long MAX_PHOTO_SIZE = 5 * 1024 * 1024;

    /**
     * 打开相册，使用图片选择器
     * @param context
     */
    public static void openAlbum(Context context) {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // 4.4 以前
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        ((Activity)context).startActivityForResult(intent, ALBUM_WITH_DATA);
    }

    /**
     * 打开系统相机
     * @param context
     * @param fileName
     */
    public static void openCamera(Context context, String fileName) {
        String status = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(status)) {
            Toast.makeText(context, "SD卡不可以", Toast.LENGTH_LONG).show();
            return;
        }
        File mCurrentPhotoFile = new File(context.getExternalCacheDir(), fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0使用FileProvider
            imageUri = FileProvider.getUriForFile(context, "com.highway.study.provider", mCurrentPhotoFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            imageUri = Uri.fromFile(mCurrentPhotoFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, MAX_PHOTO_SIZE);
        ((Activity)context).startActivityForResult(intent, CAMERA_WITH_DATA);
    }

//    /**
//     * 处理返回图片
//     */
//    public static void dealPictureHelper(Context context, int requestCode, int resultCode, Intent data, String fileName) {
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return;
//        }
//        switch (requestCode) {
//            case ALBUM_WITH_DATA://相册返回
//                Uri uri = data.getData();
//                String selectedImagePath = FileUtils.getPath(context, uri);
//                File file = new File(selectedImagePath);
//                startPhotoZoom(Uri.fromFile(file),context);
//                break;
//            case CAMERA_WITH_DATA://相机返回
//                if (mCurrentPhotoFile == null || !mCurrentPhotoFile.exists()) {
//                    mCurrentPhotoFile = new File(context.getExternalCacheDir(), fileName);
//                }
//                startPhotoZoom(Uri.fromFile(mCurrentPhotoFile), context);
//                break;
//            case CROP_PHOTO://图片剪裁返回
//                String url = mCacheFile.getAbsolutePath();
//                break;
//        }
//    }

    /**
     * 裁剪图片
     * @param uri
     * @param context
     */
    public static void startPhotoZoom(Uri uri, Context context, String fileName) {

        File file = new File(context.getExternalCacheDir(), fileName);
        Uri outputUri = Uri.fromFile(new File(file.getPath()));
        String url = FileUtils.getPath(context, uri);

        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri imageUri = FileProvider.getUriForFile(context, "com.highway.study.provider", new File(url));//通过FileProvider创建一个content类型的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("noFaceDetection", true);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.setDataAndType(imageUri, "image/*");

            //19=<sdk<24
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");

            //sdk<19
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 19);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 20);// x:y=1:2
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 500);
        intent.putExtra("output", outputUri);
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        ((Activity)context).startActivityForResult(intent, CROP_PHOTO);
    }
}
