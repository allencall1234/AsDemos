package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.widget.flowlayout.FlowLayout;
import com.example.demos.widget.flowlayout.TagAdapter;
import com.example.demos.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 524202 on 2017/9/20.
 */

public class SmartTagFlowActivity extends FragmentActivity {

    private TagFlowLayout tagFlowLayout1 = null;
    private TagFlowLayout tagFlowLayout2 = null;
    private List<String> labels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tag_flow_layout);
        tagFlowLayout1 = (TagFlowLayout) findViewById(R.id.tag_flow_layout_1);
        tagFlowLayout2 = (TagFlowLayout) findViewById(R.id.tag_flow_layout_2);

        labels = new ArrayList<>();
        labels.add("iphone");
        labels.add("Apple");
        labels.add("单反");
        labels.add("小米游戏本");
        labels.add("美图");
        labels.add("荣耀");
        labels.add("vivo");
        labels.add("小米");
        labels.add("小米鼠标");
        labels.add("游戏本");
        labels.add("灵越");
        labels.add("拯救");
        labels.add("这个肯定上热搜新闻");
        labels.add("这个肯定要上热搜新闻的信不信");
        labels.add("这个肯定要上热搜新闻的信不信");
        labels.add("这个肯定要上热搜新闻的信不信");
        labels.add("这个肯定要上热搜新闻的信不信");
        labels.add("这个肯定要上热搜新闻的信不信");
        labels.add("呵呵哒");
        labels.add("呵呵哒");
        labels.add("呵呵哒");
        labels.add("呵呵哒");
        labels.add("呵呵哒");


        tagFlowLayout1.enableSmartSort(false);
        tagFlowLayout1.setAdapter(new TagAdapter<String>(labels) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tvTag = (TextView) LayoutInflater.from(SmartTagFlowActivity.this).inflate(R.layout.search_label_tv, parent, false);
                tvTag.setText(o);
                tvTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return tvTag;
            }
        });

        tagFlowLayout2.setAdapter(new TagAdapter<String>(labels) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tvTag = (TextView) LayoutInflater.from(SmartTagFlowActivity.this).inflate(R.layout.search_label_tv, parent, false);
                tvTag.setText(o);
                tvTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return tvTag;
            }
        });
    }
}
