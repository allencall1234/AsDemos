package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button refreshButton = null;
    private TagAdapter<String> mAdapter1;
    private TagAdapter<String> mAdapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tag_flow_layout);
        tagFlowLayout1 = (TagFlowLayout) findViewById(R.id.tag_flow_layout_1);
        tagFlowLayout2 = (TagFlowLayout) findViewById(R.id.tag_flow_layout_2);

        labels = new ArrayList<>();
        labels.add("iphone");
        labels.add("荣耀");
        labels.add("小米");
        labels.add("苹果数据线");
        labels.add("充电宝");
        labels.add("耳机");
        labels.add("华为");
        labels.add("大闸蟹");
        labels.add("月饼");
        labels.add("白酒");
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
        tagFlowLayout1.setAdapter(mAdapter1 = new TagAdapter<String>(labels) {
            @Override
            public View getView(FlowLayout parent, int position, final String o) {
                TextView tvTag = (TextView) LayoutInflater.from(SmartTagFlowActivity.this).inflate(R.layout.search_label_tv, parent, false);
                tvTag.setText(o);
                tvTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("click : " + o);
                        labels.remove(o);
                        mAdapter1.notifyDataChanged();
                        mAdapter2.notifyDataChanged();
                    }
                });
                return tvTag;
            }
        });

        tagFlowLayout2.setAdapter(mAdapter2 = new TagAdapter<String>(labels) {
            @Override
            public View getView(FlowLayout parent, int position, final String o) {
                TextView tvTag = (TextView) LayoutInflater.from(SmartTagFlowActivity.this).inflate(R.layout.search_label_tv, parent, false);
                tvTag.setText(o);
                tvTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("click : " + o);
                        labels.remove(o);
                        mAdapter1.notifyDataChanged();
                        mAdapter2.notifyDataChanged();
                    }
                });
                return tvTag;
            }
        });

        refreshButton = (Button) findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (labels.size() > 0){
                    labels.remove(0);
                    mAdapter1.notifyDataChanged();
                    mAdapter2.notifyDataChanged();
                }
            }
        });
    }

    private Toast mToast;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(SmartTagFlowActivity.this, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
