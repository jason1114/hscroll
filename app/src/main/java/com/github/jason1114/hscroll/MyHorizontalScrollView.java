package com.github.jason1114.hscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by baidu on 15/10/30.
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
    public MyHorizontalScrollView(Context context) {
        super(context);
        setUp();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setUp();
        scrollTo(getResources().getDimensionPixelOffset(R.dimen.w), 0);
    }

    private void setUp() {
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    int initialPosition;
    Runnable scrollerTask = new Runnable() {

        public void run() {
            int newPosition = getScrollY();
            if(initialPosition - newPosition == 0){//has stopped
                smoothScrollTo(getResources().getDimensionPixelOffset(R.dimen.w), 0);
            }else{
                initialPosition = getScrollY();
                MyHorizontalScrollView.this.postDelayed(scrollerTask, 100);
            }
        }
    };
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 4);
    }

    public void startScrollerTask(){
        initialPosition = getScrollY();
        MyHorizontalScrollView.this.postDelayed(scrollerTask, 100);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("onScrollChanged", "l:" + l + ",t:" + t + ",oldl:" + oldl + ",oldt:" + oldt);
        this.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startScrollerTask();
                }
                return false;
            }
        });
    }

}
