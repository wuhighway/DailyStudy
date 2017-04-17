package study.highway.com.stady.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JH
 * @date 2017/4/17
 */


public class WxItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "WxItemDecoration";
    /** 绘制item的高度*/
    private int mTitleHeight;
    /** 数据源*/
    private List<CityBean> mDatas = new ArrayList<CityBean>();


    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private static int mTitleFontSize;//title字体大小

    public WxItemDecoration(Context context, List<CityBean> datas) {
        super();
        mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }


    /**
     * 绘制在itemView的下层
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++ ) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int pos = parent.getChildAdapterPosition(view);
            if (pos > -1) {
                if (pos == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, view, params, pos);

                } else {//其他的通过判断
                    if (null != mDatas.get(pos).getTag() && !mDatas.get(pos).getTag().equals(mDatas.get(pos - 1).getTag())) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, view, params, pos);
                    }
                }
            }
        }
    }

    /**
     * 绘制在itemView的上层
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int pos = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
        String tag = mDatas.get(pos).getTag();
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        boolean flag = false;//定义一个flag，Canvas是否位移过的标志
        if (pos + 1 < mDatas.size()) {
            if (null != tag && !tag.equals(mDatas.get(pos + 1).getTag())) {
                if (child.getHeight() + child.getTop() < mTitleHeight) {
                    c.save();
                    Log.e(TAG, "child.getTop() = " + child.getTop());
                    flag = true;
                    c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
                    Log.e(TAG, "child.translate = " + (child.getHeight() + child.getTop() - mTitleHeight));
                }
            }
        }
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        c.drawText(tag, child.getPaddingLeft(),
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                mPaint);

        if (flag)
            c.restore();
    }

    /**
     * 绘制Title区域背景和文字的方法
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
/*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        mPaint.getTextBounds(mDatas.get(position).getTag(), 0, mDatas.get(position).getTag().length(), mBounds);
        c.drawText(mDatas.get(position).getTag(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if (pos > -1) {
            if (pos == 0) { // 第一条数据肯定需要绘制
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                if ((null != mDatas.get(pos)
                        && !mDatas.get(pos).getTag()
                        .equals(mDatas.get(pos - 1).getTag()))) { // 对比当前的数据和上一条数据的tag是否相同，不同则需要绘制
                    outRect.set(0, mTitleHeight, 0, 0);
                } else { // 相同不需要绘制
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }
}
