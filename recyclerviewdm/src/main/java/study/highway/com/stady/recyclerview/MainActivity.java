package study.highway.com.stady.recyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zxt";
    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;
    private List<String> pyDatas;
    private TextView show;
    private IndexView indexView;

    private WxItemDecoration mDecoration;

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView) findViewById(R.id.show);
        indexView = (IndexView) findViewById(R.id.index);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        initDatas();
        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.addItemDecoration(mDecoration = new WxItemDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        indexView.resetData(pyDatas);
        indexView.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                int pos = getPosByTag(word);
                Log.e(TAG, "当前位置 = " + pos);
                handler.removeCallbacksAndMessages(null);
                ((LinearLayoutManager)mRv.getLayoutManager()).scrollToPositionWithOffset(pos, 0);
                show.setText(word);
                show.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        show.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        pyDatas = new ArrayList<>();
        mDatas.add(new CityBean("安徽"));
        mDatas.add(new CityBean("北京"));
        mDatas.add(new CityBean("福建"));
        mDatas.add(new CityBean("广东"));
        mDatas.add(new CityBean("甘肃"));
        mDatas.add(new CityBean("贵州"));
        mDatas.add(new CityBean("广西"));
        mDatas.add(new CityBean("河南"));
        mDatas.add(new CityBean("湖北"));
        mDatas.add(new CityBean("湖南"));
        mDatas.add(new CityBean("河北"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("江苏"));
        mDatas.add(new CityBean("日本"));
        mDatas.add(new CityBean("日本"));
        mDatas.add(new CityBean("日本"));
        mDatas.add(new CityBean("日本"));



        Collections.sort(mDatas, new Comparator<CityBean>() {
            @Override
            public int compare(CityBean o1, CityBean o2) {
                return o1.getPinyin().compareTo(o2.getPinyin());
            }
        });
        for (int i = 0; i < mDatas.size(); i++) {
            String tag = mDatas.get(i).getTag();
            if (pyDatas != null && !pyDatas.contains(tag)) {
                pyDatas.add(tag);
            }
        }
        Collections.sort(pyDatas, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * 根据传入的pos返回tag
     *
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mDatas.size(); i++) {
            if (tag.equals(mDatas.get(i).getTag())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
