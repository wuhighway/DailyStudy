package com.highway.study.takephoto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.highway.study.R;
import com.highway.study.util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * take photo
 */
public class TakePhotoActivity extends AppCompatActivity {

    private static final String TAG = "TakePhotoActivity";
    private static final int ALBUM_WITH_DATA = 1001;//相册
    private static final int CAMERA_WITH_DATA = 1002;//相机
    private static final int CROP_PHOTO = 101; // 裁剪
    private File mCurrentPhotoFile;// 照相机拍照得到的图片
    private RecyclerView recyclerView;
    private ArrayList<Pictrue> mpicList;
    private PictureAdapter adapter;
    /** 拍照图片的名字*/
    private String fileName;
    /** 剪裁图片的名字*/
    private String zoomName;
    private static final int TAKEPHOTO_CODE = 0X0001;
    private static final int OPENALBUM_CODE = 0X0002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mpicList = new ArrayList<>();//用于显示 车辆图片的数据集
        mpicList.add(new Pictrue("", "default", false));
        adapter = new PictureAdapter(this, mpicList, new ItemCallback() {
            @Override
            public void clickCallback() {
                showPhoto(TAKEPHOTO_CODE);
            }

            @Override
            public void deleteCallback() {

            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void addPictoList(String url) {
        //需要上传的新添加的图片
        for (int i = 0; i < mpicList.size(); i++) {
            if (mpicList.get(i).getUrl().equals("default")) {
                mpicList.set(i, new Pictrue(UUID.randomUUID().toString(), url, true));
                mpicList.add(new Pictrue("", "default", false));
                break;
            }

        }
        adapter.notifyDataSetChanged();
    }


    /**
     * get photo
     * @param code
     */
    private void showPhoto (int code) {
        fileName = System.currentTimeMillis() + ".jpg";
        if (code == TAKEPHOTO_CODE) {
            PhotoUtils.openCamera(TakePhotoActivity.this, fileName);
        } else if (code == OPENALBUM_CODE) {
            PhotoUtils.openAlbum(this);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case ALBUM_WITH_DATA://from album
                Uri uri = data.getData();
                String selectedImagePath = FileUtils.getPath(this, uri);
                mCurrentPhotoFile = new File(selectedImagePath);
                zoomName = fileName = System.currentTimeMillis() + ".jpg";
                PhotoUtils.startPhotoZoom(Uri.fromFile(mCurrentPhotoFile), this, zoomName);
                break;
            case CAMERA_WITH_DATA://from camera
                if (mCurrentPhotoFile == null || !mCurrentPhotoFile.exists()) {
                    mCurrentPhotoFile = new File(getExternalCacheDir(), fileName);
                }
                zoomName = fileName = System.currentTimeMillis() + ".jpg";
                PhotoUtils.startPhotoZoom(Uri.fromFile(mCurrentPhotoFile), this, zoomName);
                break;
            case CROP_PHOTO://from crop
                File file = new File(getExternalCacheDir(), zoomName);
                if (file.exists()) {
                    String url = file.getAbsolutePath();
                    addPictoList(url);
                } else {
                    Toast.makeText(this, "裁剪失败！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
