package com.highway.study.shareelem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.highway.study.MainActivity;
import com.highway.study.R;

public class ShareTractionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_traction);

        ImagesAdapter adapter = new ImagesAdapter() {
            @SuppressLint("NewApi")
            @Override
            protected void onClickItem(View v, int position) {
                launch(ShareTractionActivity.this, v, data.get(position).resId);
            }
        };

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.content);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void launch(AppCompatActivity activity, View transitionView, int resId) {
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
        startActivity(intent, transitionActivityOptions.toBundle());
    }
}
