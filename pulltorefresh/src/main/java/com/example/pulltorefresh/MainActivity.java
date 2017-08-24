package com.example.pulltorefresh;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.pulltorefresh.widget.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_SUCCESS = 100;
    public static final int MSG_TIMEOUT = 101;
    public static final int MSG_FAILED = 102;

    PullToRefreshLayout refreshLayout = null;
    RecyclerView recyclerView = null;
    List<String> datas = null;
    TestAdapter mAdapter = null;

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    List<String> list = (List<String>) msg.obj;
                    datas.addAll(0, list);
                    mAdapter.notifyDataSetChanged();
                    showToast("成功添加了" + list.size() + "条数据!");
                    break;
                case MSG_FAILED:
                    showToast("刷新失败!");
                    break;
                case MSG_TIMEOUT:
                    showToast("刷新超时");
                    break;
            }
            refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
    };

    static class MessageThread extends Thread {
        int msg;
        static int count = 0;
        Handler mhandle;

        public MessageThread(Handler handle) {
            msg = new Random().nextInt(3) + 100;
            mhandle = handle;
        }

        @Override
        public void run() {
            Message message = Message.obtain();
            switch (msg) {
                case MSG_SUCCESS:
                    List<String> datas = new ArrayList<>();
                    int c = new Random().nextInt(6) + 5;
                    for (int i = 0; i < c; i++) {
                        count++;
                        datas.add("新添加的数据:" + count);
                    }

                    message.what = msg;
                    message.obj = datas;
                    break;
                case MSG_FAILED:
                case MSG_TIMEOUT:
                    message.what = msg;
                    break;
                default:
                    break;
            }
            mhandle.handleMessage(message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
    }

    private void initview() {

        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("测试数据" + i);
        }

        refreshLayout = (PullToRefreshLayout) findViewById(R.id.swipelayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new TestAdapter(this, datas));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                mHandler.postDelayed(new MessageThread(mHandler), 3000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
    }

    private Toast mToast = null;

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
