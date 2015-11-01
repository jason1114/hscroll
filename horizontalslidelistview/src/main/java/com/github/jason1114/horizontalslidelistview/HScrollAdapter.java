package com.github.jason1114.horizontalslidelistview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * <p>
 *     Inherit the adapter and set this adapter to
 *     a ListView to support list view item horizontal scrolling
 * </p>
 * Created by baidu on 15/10/31.
 */
public abstract class HScrollAdapter extends BaseAdapter {

    /**
     * <p>
     *     Layout Xml when item when sliding right and the left item is shown
     * </p>
     * @return
     */
    public abstract @LayoutRes int getLeftLayout();

    /**
     * <p>
     *      Layout Xml that is shown when no horizontal slide is performed
     * </p>
     * @return
     */
    public abstract @LayoutRes int getCenterLayout();

    /**
     * <p>
     *     Layout Xml when item when sliding left and the right item is shown
     * </p>
     * @return
     */
    public abstract @LayoutRes int getRightLayout();

    /**
     * <p>
     *      It's a replacement of {@link #getView(int, View, ViewGroup)},
     *      the getView method is set to final.
     * </p>
     * @param position
     * @param left
     * @param center
     * @param right
     */
    public abstract void renderView(int position, View left, View center, View right);

    public abstract void onStateLeft2(int position, View left, View center, View right);
    public abstract void onStateLeft1(int position, View left, View center, View right);
    public abstract void onStateNormal(int position, View left, View center, View right);
    public abstract void onStateRight1(int position, View left, View center, View right);
    public abstract void onStateRight2(int position, View left, View center, View right);

    public abstract void onStateLeft1Released(int position, View left, View center, View right);
    public abstract void onStateLeft2Released(int position, View left, View center, View right);
    public abstract void onStateRight1Released(int position, View left, View center, View right);
    public abstract void onStateRight2Released(int position, View left, View center, View right);

    protected Context mContext;

    public HScrollAdapter(Context context) {
        mContext = context;
    }

    /**
     * {@inheritDoc}
     * <p>
     *     Wrap a horizontal scroll view,
     *     Also use the ViewHolder design pattern
     * </p>
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public final View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            ViewHolder holder = new ViewHolder();
            /**
             * Horizontal ScrollView
             */
            holder.hsv = new SlideItemView(mContext);
            AbsListView.LayoutParams hsvLayoutParams = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            holder.hsv.setLayoutParams(hsvLayoutParams);
            /**
             * Horizontal LinearLayout
              */
            LayoutInflater inflater = LayoutInflater.from(mContext);
            holder.hLayout = new MultipleWidthLinearLayout(mContext);
            holder.hLayout.setMultiplier(3);
            holder.hLayout.setOrientation(LinearLayout.HORIZONTAL);
            HorizontalScrollView.LayoutParams scrollLayoutParams =
                    new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.hLayout.setLayoutParams(scrollLayoutParams);
            /**
             * Left & Right & center
             */
            RelativeLayout
                    leftArea = new RelativeLayout(mContext),
                    centerArea = new RelativeLayout(mContext),
                    rightArea = new RelativeLayout(mContext);
            LinearLayout.LayoutParams areaParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            areaParams.weight = 1;
            leftArea.setLayoutParams(areaParams);
            centerArea.setLayoutParams(areaParams);
            rightArea.setLayoutParams(areaParams);
            holder.hLayout.addView(leftArea);
            holder.hLayout.addView(centerArea);
            holder.hLayout.addView(rightArea);
            holder.left = inflater.inflate(getLeftLayout(), leftArea);
            holder.right = inflater.inflate(getRightLayout(), rightArea);
            holder.center = inflater.inflate(getCenterLayout(), centerArea);
            /**
             * Combine
             */
            holder.hsv.addView(holder.hLayout);
            convertView = holder.hsv;
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        renderView(position, holder.left, holder.center, holder.right);
        holder.hsv.restoreScrollPosition();
        holder.hsv.setScrollStateListener(new SlideItemView.ScrollStateListener() {
            @Override
            public void onStateLeft2() {
                HScrollAdapter.this.onStateLeft2(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateLeft1() {
                HScrollAdapter.this.onStateLeft1(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateNormal() {
                HScrollAdapter.this.onStateNormal(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateRight1() {
                HScrollAdapter.this.onStateRight1(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateRight2() {
                HScrollAdapter.this.onStateRight2(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateLeft1Released() {
                HScrollAdapter.this.onStateLeft1Released(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateLeft2Released() {
                HScrollAdapter.this.onStateLeft2Released(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateRight1Released() {
                HScrollAdapter.this.onStateRight1Released(position, holder.left, holder.center, holder.right);
            }

            @Override
            public void onStateRight2Released() {
                HScrollAdapter.this.onStateRight2Released(position, holder.left, holder.center, holder.right);
            }
        });
        return convertView;
    }

    class ViewHolder {
        SlideItemView hsv;
        MultipleWidthLinearLayout hLayout;
        View left, center, right;
    }
}
