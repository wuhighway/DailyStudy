package com.highway.study.anim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.highway.study.R;
import com.highway.study.TitleAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 动画
 */
public class AnimActivity extends AppCompatActivity {


    @Bind(R.id.recycler)
    RecyclerView mAnimationApisRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mAnimationApisRecycler.setLayoutManager(new LinearLayoutManager(this));
        String[] apiArray = getResources().getStringArray(R.array.animation_apis);
        TitleAdapter animationApisAdapter =
                new TitleAdapter(apiArray, onRecyclerItemClick);
        mAnimationApisRecycler.setAdapter(animationApisAdapter);
        mAnimationApisRecycler.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
    }

    private TitleAdapter.OnRecyclerItemClick onRecyclerItemClick =
            new TitleAdapter.OnRecyclerItemClick() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(
                                    AnimActivity.this, ViewPropertyAnimatorActivity.class);
                            break;
//                        case 1:
//                            intent = new Intent(MainActivity.this, ObjectAnimatorActivity.class);
//                            break;
//                        case 2:
//                            intent = new Intent(MainActivity.this, InterpolatorActivity.class);
//                            break;
//                        case 3:
//                            intent = new Intent(MainActivity.this, CircularRevealActivity.class);
//                            break;
//                        case 4:
//                            intent = new Intent(MainActivity.this, MorphTransitionsActivity.class);
//                            break;
//                        case 5:
//                            intent = new Intent(MainActivity.this, SharedTransitionsActivity.class);
//                            break;
//                        case 6:
//                            intent = new Intent(MainActivity.this, WindowTransitionsActivity.class);
//                            break;
//                        case 7:
//                            intent = new Intent(
//                                    MainActivity.this, WindowTransitionsActivityExplode.class);
//                            break;
//                        case 8:
//                            intent = new Intent(
//                                    MainActivity.this, AnimatedVectorDrawablesActivity.class);
//                            break;
                    }
                    if (intent != null) startActivity(intent);
                }
            };
}
