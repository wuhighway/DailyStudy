package com.highway.study.scanphotos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JH
 * on 2017/3/21.
 */

public class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Integer> media;
    private View.OnClickListener videoOnClickListener;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public ImagePagerAdapter(FragmentManager fm, ArrayList<Integer> media) {
        super(fm);
        this.media = media;
    }

    public void setVideoOnClickListener(View.OnClickListener videoOnClickListener) {
        this.videoOnClickListener = videoOnClickListener;
    }

    @Override public Fragment getItem(int pos) {
       return ImageFragment.newInstance(media.get(pos));
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void swapDataSet(ArrayList<Integer> media) {
        this.media = media;
        notifyDataSetChanged();
    }

    @Override public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override public int getCount() {
        return media.size();
    }
}
