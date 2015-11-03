package com.github.jason1114.hscroll;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jason1114.horizontalslidelistview.HScrollAdapter;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(new MailAdapter(this));
    }

    class MailAdapter extends HScrollAdapter {
        List<Mail> mData;

        public MailAdapter(Context context) {
            super(context);
            mData = new LinkedList<Mail>(){
                {
                    add(new Mail(
                            "在你的圈子中热门",
                            "变态辣椒 发推： 最后那辆三轮车真拉风",
                            ""
                    ));
                    add(new Mail(
                            "JavaScript Weekly",
                            "This week's JavaScript news, issue 256",
                            "This week;s JavaScript news Read this e-mail on the..."
                    ));
                    add(new Mail(
                            "Coding.NET",
                            "Coding Monthly|让开发更简单",
                            "产品更新日志 亲爱的 Coding.net 用户， 本月最值得..."
                    ));
                    add(new Mail(
                            "OpenShift by Red Hat",
                            "OpenShift Newsletter: October 2015",
                            "此邮件没有文字内容"
                    ));
                    add(new Mail(
                            "Quora Digest",
                            "What is a brain teaser that is very short and e.",
                            "Answer: Which way is this New York school bus..."
                    ));
                }
            };
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getLayout(int type) {
            switch (type) {
                case TYPE_LEFT:
                    return R.layout.item_left;
                case TYPE_RIGHT:
                    return R.layout.item_right;
                case TYPE_CENTER:
                    return R.layout.item;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void renderView(int position, View left, View center, View right) {
            ViewHolder h = (ViewHolder) center.getTag();
            if (h == null) {
                h = new ViewHolder();
                h.actionDelete = (ImageView) left.findViewById(R.id.action_delete);
                h.actionCheck = (ImageView) left.findViewById(R.id.action_check);
                h.actionMessage = (ImageView) right.findViewById(R.id.action_message);
                h.actionAlarm = (ImageView) right.findViewById(R.id.action_alarm);
                h.alarmBg = right.findViewById(R.id.alarm_bg);
                h.messageBg = right.findViewById(R.id.message_bg);
                h.checkBg = left.findViewById(R.id.check_bg);
                h.deleteBg = left.findViewById(R.id.delete_bg);
                h.sender = (TextView) center.findViewById(R.id.sender);
                h.title = (TextView) center.findViewById(R.id.title);
                h.content = (TextView) center.findViewById(R.id.content);
                center.setTag(h);
            }
            Mail mail = mData.get(position);
            h.content.setText(mail.getContent());
            h.sender.setText(mail.getSender());
            h.title.setText(mail.getTitle());
        }

        @Override
        public void onState(int position, int state, View left, View center, View right) {
            ViewHolder h = (ViewHolder) center.getTag();
            switch (state) {
                case STATE_LEFT_2:
                    h.actionCheck.setVisibility(View.INVISIBLE);
                    h.actionDelete.setVisibility(View.VISIBLE);
                    h.deleteBg.setVisibility(View.VISIBLE);
                    h.checkBg.setVisibility(View.INVISIBLE);
                    break;
                case STATE_LEFT_1:
                    h.actionCheck.setVisibility(View.VISIBLE);
                    h.actionDelete.setVisibility(View.INVISIBLE);
                    h.deleteBg.setVisibility(View.INVISIBLE);
                    h.checkBg.setVisibility(View.VISIBLE);
                    break;
                case STATE_NORMAL:
                    h.actionCheck.setVisibility(View.VISIBLE);
                    h.actionDelete.setVisibility(View.INVISIBLE);
                    h.actionMessage.setVisibility(View.VISIBLE);
                    h.actionAlarm.setVisibility(View.INVISIBLE);
                    h.deleteBg.setVisibility(View.INVISIBLE);
                    h.checkBg.setVisibility(View.INVISIBLE);
                    h.alarmBg.setVisibility(View.INVISIBLE);
                    h.messageBg.setVisibility(View.INVISIBLE);
                    break;
                case STATE_RIGHT_1:
                    h.messageBg.setVisibility(View.VISIBLE);
                    h.alarmBg.setVisibility(View.INVISIBLE);
                    h.actionAlarm.setVisibility(View.INVISIBLE);
                    h.actionMessage.setVisibility(View.VISIBLE);
                    break;
                case STATE_RIGHT_2:
                    h.messageBg.setVisibility(View.INVISIBLE);
                    h.alarmBg.setVisibility(View.VISIBLE);
                    h.actionAlarm.setVisibility(View.VISIBLE);
                    h.actionMessage.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        @Override
        public void onStateReleased(int position, int state, View left, View center, View right) {
            mData.remove(position);
            notifyDataSetChanged();
        }
    }
    static class ViewHolder {
        ImageView actionDelete, actionCheck, actionMessage, actionAlarm;
        TextView sender, title, content;
        View checkBg, deleteBg, alarmBg, messageBg;
    }
}
