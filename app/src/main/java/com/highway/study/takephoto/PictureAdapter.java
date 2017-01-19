package com.highway.study.takephoto;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.highway.study.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JH
 * on 2017/1/19.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHelper> {

    private Context mContent;
    private List<Pictrue> pictures = new ArrayList<>();
    private ItemCallback itemCallback;

    public PictureAdapter(Context mContent, List<Pictrue> pictures, ItemCallback itemCallback) {
        this.mContent = mContent;
        this.pictures = pictures;
        this.itemCallback = itemCallback;
    }

    @Override
    public ViewHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.adapter_mainpic, parent, false);
        return new ViewHelper(view);
    }

    @Override
    public void onBindViewHolder(ViewHelper holder, final int position) {
        if (pictures.get(position).getUrl().equals("default")) {
            holder.imgContent.setImageResource(R.mipmap.bg_img_up);
            holder.imgDel.setVisibility(View.GONE);
            holder.imgDel.setClickable(false);
        } else {
            holder.imgContent.setImageURI(Uri.parse(pictures.get(position).getUrl()));
            holder.imgDel.setVisibility(View.VISIBLE);
            holder.imgDel.setClickable(true);
        }


        holder.imgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pictures.size() < 10) {
                    if (pictures.get(position).getUrl().equals("default")) {//选择图片
                        if (itemCallback != null) {
                            itemCallback.clickCallback();
                        }
                    }
                } else {
                    if (pictures.size() == 10) {
                        if (pictures.get(9).getUrl().equals("default")) {
                            if (itemCallback != null) {
                                itemCallback.clickCallback();
                            }
                        } else {
                            Toast.makeText(mContent, "最多只能上传10张图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
        });

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pictrue p = pictures.get(position);
                if (pictures.size() == 1) {
                    pictures.remove(p);
                    pictures.add(new Pictrue("", "default", false));
                } else {

                    pictures.remove(p);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class ViewHelper extends RecyclerView.ViewHolder {
        private ImageView imgContent;
        private ImageView imgDel;

        public ViewHelper(View itemView) {
            super(itemView);
            imgContent = (ImageView) itemView.findViewById(R.id.iv_content);
            imgDel = (ImageView) itemView.findViewById(R.id.iv_del);
        }
    }
}
