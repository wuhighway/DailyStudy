package com.highway.study;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JH
 * on 2017/2/7.
 */

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {
    private String[] mApiArray;
    private OnRecyclerItemClick mOnRecyclerItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mApiNameTextView;
        public ViewHolder(TextView v) {
            super(v);
            mApiNameTextView = v;
            mApiNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerItemClickListener != null) {
                int itemPosition = getAdapterPosition();
                mOnRecyclerItemClickListener.onItemClick(itemPosition);
            }
        }
    }

    public TitleAdapter(String[] apiArray, OnRecyclerItemClick onRecyclerItemClick) {
        mApiArray = apiArray;
        mOnRecyclerItemClickListener = onRecyclerItemClick;
    }

    @Override
    public TitleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_api, parent, false);
        return new ViewHolder((TextView) v.findViewById(R.id.text_name));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mApiNameTextView.setText(mApiArray[position]);
    }

    @Override
    public int getItemCount() {
        return mApiArray.length;
    }

    public interface OnRecyclerItemClick {
        void onItemClick(int position);
    }
}
