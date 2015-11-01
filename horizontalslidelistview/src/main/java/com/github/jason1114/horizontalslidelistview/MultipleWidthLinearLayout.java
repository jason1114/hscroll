package com.github.jason1114.horizontalslidelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * <p>
 *     Helper class to create LinearLayout that is n times width than the ScrollView
 * </p>
 *
 * Created by baidu on 15/10/31.
 */
public class MultipleWidthLinearLayout extends LinearLayout {

    int mMultiplier = 1;

    public MultipleWidthLinearLayout(Context context) {
        super(context);
    }

    public MultipleWidthLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWeight = MeasureSpec.getSize(widthMeasureSpec);
            int myWidth = parentWeight * mMultiplier;
            super.onMeasure(MeasureSpec.makeMeasureSpec(myWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
    }

    public int getMultiplier() {
        return mMultiplier;
    }

    public void setMultiplier(int mMultiplier) {
        this.mMultiplier = mMultiplier;
    }
}
