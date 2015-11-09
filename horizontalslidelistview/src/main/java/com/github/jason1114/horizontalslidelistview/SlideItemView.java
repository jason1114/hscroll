package com.github.jason1114.horizontalslidelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * <p>
 *     Custom HorizontalScrollView to support slide of ListView item.
 * </p>
 * Created by baidu on 15/10/30.
 */
public class SlideItemView extends HorizontalScrollView {

    /**
     * Scroll states
     */
    static final int
            STATE_NORMAL = 205,
            STATE_LEFT_1 = 204,
            STATE_LEFT_2 = 203,
            STATE_RIGHT_1 = 206,
            STATE_RIGHT_2 = 207;


    int mMeasuredWidth = -1;
    int mState = STATE_NORMAL;
    int mStateReleaseFrom = -1;
    boolean mReleased = false;

    /**
     * Used by onScrollChangedListener to avoid executing the same callback twice.
     */
    int mLastChangeFrom = -1, mLastChangeTo = -1;

    ScrollStateListener mScrollStateListener;

    public ScrollStateListener getScrollStateListener() {
        return mScrollStateListener;
    }

    public void setScrollStateListener(ScrollStateListener mScrollStateListener) {
        this.mScrollStateListener = mScrollStateListener;
    }

    public SlideItemView(Context context) {
        super(context);
        setUp();
    }

    public SlideItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public SlideItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setUp();
        restoreScrollPosition();
    }

    /**
     * Initial place scroll viewport at the second item
     */
    public void restoreScrollPosition() {
        this.post(new Runnable() {
            @Override
            public void run() {
                scrollTo(getMeasuredWidth(), 0);
            }
        });
    }

    /**
     * <p>Basic appearance set up</p>
     */
    private void setUp() {
        setFillViewport(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * See {@link #startScrollerTask()}}
     */
    Runnable scrollerTask = new Runnable() {

        public void run() {
            int currentState = getState(getScrollX());
            switch (currentState) {
                case STATE_LEFT_2:
                    scrollToLeftArea();
                    if (mScrollStateListener != null) {
                        mStateReleaseFrom = STATE_LEFT_2;
                    }
                    break;
                case STATE_LEFT_1:
                    scrollToLeftArea();
                    if (mScrollStateListener != null) {
                        mStateReleaseFrom = STATE_LEFT_1;
                    }
                    break;
                case STATE_NORMAL:
                    scrollToCenterArea();
                    break;
                case STATE_RIGHT_1:
                    scrollToRightArea();
                    if (mScrollStateListener != null) {
                        mStateReleaseFrom = STATE_RIGHT_1;
                    }
                    break;
                case STATE_RIGHT_2:
                    scrollToRightArea();
                    if (mScrollStateListener != null) {
                        mStateReleaseFrom = STATE_RIGHT_2;
                    }
                    break;
            }
        }
    };

    /**
     * {@inheritDoc}
     *
     * <p>
     *     Slow down the speed of horizontal scrolling.
     * </p>
     * @param velocityX
     */
    @Override
    public void fling(int velocityX) {
        super.fling(velocityX / 20);
    }

    /**
     * <p>
     *     Start to scroll to some position after user releasing scroll
     * </p>
     */
    public void startScrollerTask(){
        SlideItemView.this.postDelayed(scrollerTask, 100);
    }

    /**
     * Watch user's scroll release
     */
    OnTouchListener mOnTouchListener = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                startScrollerTask();
                mReleased = true;
            } else {
                mReleased = false;
            }
            return false;
        }
    };

    /**
     * {@inheritDoc}
     * <p>
     *      Watch current scroll state and trigger
     *      callbacks of {@link SlideItemView.ScrollStateListener}
     * </p>
     * @param x
     * @param y
     * @param oldX
     * @param oldY
     */
    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (mMeasuredWidth == -1) {
            mMeasuredWidth = getMeasuredWidth();
        }
        if (oldX == mLastChangeFrom && x == mLastChangeTo) {
            return;
        } else {
            mLastChangeFrom = oldX;
            mLastChangeTo = x;
        }
        int currentState = getState(x);
        /**
         * Trigger callbacks when scroll state change
         */
        switch (currentState) {
            case STATE_LEFT_2:
                if (mState != STATE_LEFT_2 && mScrollStateListener != null && !mReleased) {
                    mScrollStateListener.onState(HScrollAdapter.STATE_LEFT_2);
                }
                break;
            case STATE_LEFT_1:
                if (mState != STATE_LEFT_1 && mScrollStateListener != null && !mReleased) {
                    mScrollStateListener.onState(HScrollAdapter.STATE_LEFT_1);
                }
                break;
            case STATE_NORMAL:
                if (mState != STATE_NORMAL && mScrollStateListener != null && !mReleased) {
                    mScrollStateListener.onState(HScrollAdapter.STATE_NORMAL);
                }
                break;
            case STATE_RIGHT_1:
                if (mState != STATE_RIGHT_1 && mScrollStateListener != null && !mReleased) {
                    mScrollStateListener.onState(HScrollAdapter.STATE_RIGHT_1);
                }
                break;
            case STATE_RIGHT_2:
                if (mState != STATE_RIGHT_2 && mScrollStateListener != null && !mReleased) {
                    mScrollStateListener.onState(HScrollAdapter.STATE_RIGHT_2);
                }
                break;
        }
        mState = currentState;
        /**
         * Release form either STATE_LEFT_1 or STATE_LEFT_2
         */
        if (x == 0) {
            if (mStateReleaseFrom == STATE_LEFT_1) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollStateListener.onStateReleased(HScrollAdapter.STATE_LEFT_1);
                    }
                });
            } else if (mStateReleaseFrom == STATE_LEFT_2) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollStateListener.onStateReleased(HScrollAdapter.STATE_LEFT_2);
                    }
                });
            }
        } else if (x >= 2 * mMeasuredWidth) {
            /**
             * Release form either STATE_RIGHT_1 or STATE_RIGHT_2
             */
            if (mStateReleaseFrom == STATE_RIGHT_1) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollStateListener.onStateReleased(HScrollAdapter.STATE_RIGHT_1);
                    }
                });
            } else if (mStateReleaseFrom == STATE_RIGHT_2) {
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollStateListener.onStateReleased(HScrollAdapter.STATE_RIGHT_2);
                    }
                });
            }
        }
        this.setOnTouchListener(mOnTouchListener);
    }

    /**
     * <p>
     *     Scroll to the first item
     * </p>
     */
    public void scrollToLeftArea() {
        smoothScrollTo(0, 0);
    }

    /**
     * <p>
     *     Scroll to the second item
     * </p>
     */
    public void scrollToRightArea() {
        smoothScrollTo(2 * getMeasuredWidth(), 0);
    }

    /**
     * <p>
     *     Scroll to the third item
     * </p>
     */
    public void scrollToCenterArea() {
        smoothScrollTo(getMeasuredWidth(), 0);
    }

    /**
     * Get scroll state according to x scroll offset
     * @param scroll
     * @return
     */
    private int getState(int scroll) {
        float ratio = (float)scroll/mMeasuredWidth;
        if (ratio <= 0.5f) {
            return STATE_LEFT_2;
        }
        if (ratio <= 0.8f) {
            return STATE_LEFT_1;
        }
        if (ratio <= 1.2f) {
            return STATE_NORMAL;
        }
        if (ratio <= 1.5f) {
            return STATE_RIGHT_1;
        }
        return STATE_RIGHT_2;
    }

    /**
     * <p>
     *     Callbacks of scroll state change
     * </p>
     */
    public interface ScrollStateListener {
        /**
         * Callbacks triggered when scroll state change
         */
        void onState(int state);

        /**
         * Callbacks triggered when release from some scroll state
         */
        void onStateReleased(int state);
    }
}
