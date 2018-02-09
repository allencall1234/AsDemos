package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.rvadapter.RvCommonAdapter;
import com.example.demos.rvadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 524202 on 2017/12/18.
 */

public class HorizontalViewActivity extends FragmentActivity {

    RecyclerView recyclerView = null;
    RvCommonAdapter mAdapter = null;
    List<DataBean> lists = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_layout);

        initDatas();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviw);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter = new RvCommonAdapter<DataBean>(this, R.layout.data_bean_item, lists) {
            @Override
            protected void convert(ViewHolder holder, final DataBean o, int position) {
                holder.setText(R.id.text_name, o.name);
                holder.setText(R.id.text_age, o.age + "");
                holder.setOnClickListener(R.id.text_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HorizontalViewActivity.this, "click : " + o.name,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initDatas() {
        lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DataBean bean = new DataBean();
            bean.age = i + 1;
            bean.name = "Pig " + bean.age;
            lists.add(bean);
        }
    }

    static class DataBean {
        public String name;
        public int age;
    }
}
