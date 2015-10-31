package com.github.jason1114.hscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                convertView = inflater.inflate(R.layout.item, null);
                ViewHolder vh = new ViewHolder();
                vh.sv = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
                convertView.setTag(vh);
            }
            final ViewHolder vh = (ViewHolder) convertView.getTag();
//            vh.sv.postDelayed(new Runnable() {
//                @Override
//                public void run() {
                    vh.sv.scrollTo(getResources().getDimensionPixelOffset(R.dimen.w), 0);
//                }
//            }, 500);
            vh.sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
//                    int scrollX = vh.sv.getScrollX(); //for horizontalScrollView
//                    Log.d("View Tree", "scrollX:" + scrollX);
                }
            });
//            vh.sv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    Log.e("onScrollChanged", "scrollX:" + scrollX + ",scrollY:" + scrollY + ",oldScrollX:" + oldScrollX + ",oldScrollY:" + oldScrollY);
//                }
//            });
            return convertView;
        }
    }
    class ViewHolder {
        HorizontalScrollView sv;
    }
}
