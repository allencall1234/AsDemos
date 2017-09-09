package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.other.LimitQueue;

/**
 * Created by zlt on 2017/9/9.
 */

public class SearchHistoryActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ListView listView;
    BaseAdapter mAdapter;
    LimitQueue<String> queue = new LimitQueue<String>(5);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history_layout);
        button = (Button) findViewById(R.id.search_button);
        editText = (EditText) findViewById(R.id.search_edit);
        listView = (ListView) findViewById(R.id.search_list);
        listView.setAdapter(mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return queue.getSize();
            }

            @Override
            public Object getItem(int position) {
                return queue.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(SearchHistoryActivity.this)
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                textView = (TextView) convertView.findViewById(android.R.id.text1);
                textView.setText(queue.get(queue.getSize() - 1 - position));

                return convertView;
            }
        });

        TextView textView = (TextView) findViewById(android.R.id.text1);
        textView.setText("清除历史记录");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.empty();
                mAdapter.notifyDataSetChanged();
            }
        });
//        listView.addFooterView(textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() != null && !TextUtils.isEmpty(editText.getText())) {
                    String val = editText.getText().toString().trim();
                    if (queue.offer(val)) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
