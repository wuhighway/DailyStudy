package com.highway.study.scanphotos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.highway.study.widget.photoview.PhotoView;


/**
 *
 */
public class ImageFragment extends Fragment {

    private int imgres;


    public static ImageFragment newInstance (int imgres) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uri", imgres);
        imageFragment.setArguments(bundle);
        return imageFragment;
    }


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgres =  getArguments().getInt("uri");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PhotoView photoView = new PhotoView(container.getContext());

        Glide.with(getContext())
                .load(imgres)
                .into(photoView);
        return photoView;
    }

}
