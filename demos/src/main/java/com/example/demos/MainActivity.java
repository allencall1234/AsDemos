package com.example.demos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demos.activity.AmapLocationActivity;
import com.example.demos.activity.DoubleRecyclerViewActivity;
import com.example.demos.activity.DynamicFragmentActivity;
import com.example.demos.activity.ListviewFocusActivity;
import com.example.demos.activity.RecyclerviewFocusActivity;
import com.example.demos.activity.SearchHistoryActivity;
import com.example.demos.activity.SmartTagFlowActivity;
import com.example.demos.activity.StableEditTextActivity;
import com.example.demos.fragment.DynamicFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> activities;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLists();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviw);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initLists() {
        activities = new ArrayList<>();
        activities.add(new Item("recyclerview焦点测试", RecyclerviewFocusActivity.class));
        activities.add(new Item("listview焦点测试", ListviewFocusActivity.class));
        activities.add(new Item("双列表滑动测试", DoubleRecyclerViewActivity.class));
        activities.add(new Item("固定内容EditText", StableEditTextActivity.class));
        activities.add(new Item("限长搜索列表", SearchHistoryActivity.class));
        activities.add(new Item("智能FlowLayout", SmartTagFlowActivity.class));
        activities.add(new Item("动态增添fragment", DynamicFragmentActivity.class));
        activities.add(new Item("高德地图定位", AmapLocationActivity.class));
    }


    class MainAdapter extends RecyclerView.Adapter<MainAdapter.ClassHolder> {
        @Override
        public ClassHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_item, parent, false);
            return new ClassHolder(view);
        }

        @Override
        public void onBindViewHolder(final ClassHolder holder, final int position) {
            holder.textview.setText(activities.get(position).desc);
            holder.textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, activities.get(position).activity));
                }
            });
        }

        @Override
        public int getItemCount() {
            return activities.size();
        }

        class ClassHolder extends RecyclerView.ViewHolder {
            TextView textview;

            public ClassHolder(View itemView) {
                super(itemView);
                textview = (TextView) itemView.findViewById(R.id.card_text);
            }
        }
    }

    class Item {
        String desc;
        Class activity;

        public Item(String desc, Class activity) {
            this.desc = desc;
            this.activity = activity;
        }

        public String getDesc() {
            return desc;
        }

        public Class getActivity() {
            return activity;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("zlt", "deviceId = " + ev.getDeviceId() + ",source = " + ev.getSource() + ",toolType = " + InputDevice.SOURCE_STYLUS);
        return super.dispatchTouchEvent(ev);
    }
}
