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
     *     Horizontal Slide States
     * </p>
     */
    public static final int STATE_LEFT_2 = 203;
    public static final int STATE_LEFT_1 = 204;
    public static final int STATE_NORMAL = 205;
    public static final int STATE_RIGHT_1 = 206;
    public static final int STATE_RIGHT_2 = 207;

    /**
     * <p>
     *     Layout types
     * </p>
     */
    public static final int TYPE_RIGHT = 105;
    public static final int TYPE_CENTER = 104;
    public static final int TYPE_LEFT = 103;

    /**
     * <p>
     *     Layout Xml when item when sliding, there are three type: TYPE_RIGHT, TYPE_CENTER, TYPE_LEFT
     * </p>
     * @return
     */
    public abstract @LayoutRes int getLayout(int type);

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

    public abstract void onState(int position, int state, View left, View center, View right);

    public abstract void onStateReleased(int position, int state, View left, View center, View right);

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
            final ViewHolder holder = new ViewHolder();
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
            holder.left = inflater.inflate(getLayout(TYPE_LEFT), leftArea);
            holder.right = inflater.inflate(getLayout(TYPE_RIGHT), rightArea);
            holder.center = inflater.inflate(getLayout(TYPE_CENTER), centerArea);
            holder.hsv.setScrollStateListener(new SlideItemView.ScrollStateListener() {
                @Override
                public void onState(int state) {
                    HScrollAdapter.this.onState(
                            holder.position, state, holder.left, holder.center, holder.right);
                }

                @Override
                public void onStateReleased(int state) {
                    HScrollAdapter.this.onStateReleased(
                            holder.position, state, holder.left, holder.center, holder.right);
                }
            });
            /**
             * Combine
             */
            holder.hsv.addView(holder.hLayout);
            convertView = holder.hsv;
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.position = position;
        renderView(position, holder.left, holder.center, holder.right);
        holder.hsv.restoreScrollPosition();
        return convertView;
    }

    static class ViewHolder {
        SlideItemView hsv;
        MultipleWidthLinearLayout hLayout;
        View left, center, right;
        int position;
    }
}
