package com.highway.study.shareelem;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.highway.study.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewerActivity extends AppCompatActivity {

    public static void launch(AppCompatActivity activity, View transitionView, int resId) {
//        Intent intent = new Intent(activity, ViewerActivity.class);
//
//
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(activity, transitionView, "image");
//
//        ActivityCompat.startActivity(activity, intent, options.toBundle());


        Pair squareParticipant = new Pair<>(transitionView, ViewCompat.getTransitionName(transitionView));
        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, squareParticipant);
        Intent intent = new Intent(
                activity, ViewerActivity.class);
        intent.putExtra("resId", resId);
        activity.startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer);
        ButterKnife.bind(this);

        Slide slide = new Slide(Gravity.LEFT);
        slide.addTarget(R.id.tv_text);

        getWindow().setEnterTransition(slide);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(getIntent().getIntExtra("resId", R.mipmap.pic_1));

//        ViewCompat.setTransitionName(imageView, "image");
    }

//    @OnClick(R.id.linear_content)
//    public void click() {
//        finishAfterTransition();
//    }

}
