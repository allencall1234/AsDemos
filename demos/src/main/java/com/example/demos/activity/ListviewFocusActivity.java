package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 524202 on 2017/8/31.
 */

public class ListviewFocusActivity extends FragmentActivity {

    private List<String> list;
    private ListView mListView;
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_focus_layout);
        initlists();
        mListView = (ListView) findViewById(R.id.main_listview);
        mAdapter = new FocusAdapter(R.layout.focus_item);
        mListView.setAdapter(mAdapter);
    }

    private void initlists() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("第 " + (i + 1) + "个Item:");
        }
    }

    class FocusAdapter extends BaseAdapter {
        int layoutId;

        FocusAdapter(int layoutId) {
            this.layoutId = layoutId;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(ListviewFocusActivity.this).inflate(layoutId, parent, false);
                convertView.setFocusable(false);
                convertView.setFocusableInTouchMode(false);
                if (convertView.hasFocus()){
                    convertView.clearFocus();
                }
                viewHolder.textView = (TextView) convertView.findViewById(R.id.card_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(list.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
