package com.example.demos.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.AndroidCharacter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.rvadapter.RvCommonAdapter;
import com.example.demos.rvadapter.ViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by 524202 on 2017/9/1.
 */

public class DoubleRecyclerViewActivity extends FragmentActivity {
    private RecyclerView listView1;
    private RecyclerView listView2;
    private String[] titles = {"手机", "电脑", "电视", "家电", "路由", "智能", "电源", "耳机", "音响", "礼品", "生活", "其他", "其他", "其他", "其他"};
    private List<String> list1;
    private List<Item> list2;

    private GodsAdaper mAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_recyclerview_layout);
        listView1 = (RecyclerView) findViewById(R.id.listview_1);
        listView2 = (RecyclerView) findViewById(R.id.listview_2);

        list1 = new ArrayList<>();
        list1.addAll(Arrays.asList(titles));

        listView1.setAdapter(new RvCommonAdapter<String>(this, android.R.layout.simple_list_item_1, list1) {
            @Override
            protected void convert(ViewHolder holder, final String o, int position) {
                holder.setText(android.R.id.text1, list1.get(position));
                holder.setOnClickListener(android.R.id.text1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(), "当前内容:" + o, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        initList2();
        listView2.setAdapter(mAdapter = new GodsAdaper(this, list2));

        listView1.setLayoutManager(new LinearLayoutManager(this));
        gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter.getItemViewType(position) == 0) {
                    return 3;
                }
                return 1;
            }
        });
        listView2.setLayoutManager(gridLayoutManager);
    }

    private void initList2() {
        list2 = new ArrayList<>();
        for (String title : titles) {
            list2.add(new Item(0, title));
            int rand = new Random().nextInt(10) + 5;
            for (int i = 0; i < rand; i++) {
                list2.add(new Item(1, title + ":" + i));
            }
        }
    }

    class Item {
        //0-title,1-content
        int type;
        String title;

        public Item(int type, String title) {
            this.type = type;
            this.title = title;
        }
    }

    class GodsAdaper extends RecyclerView.Adapter {

        private List<Item> datas;
        private Context mContext;

        public GodsAdaper(Context context, List<Item> lists) {
            mContext = context;
            datas = lists;
        }

        @Override
        public int getItemViewType(int position) {
            return datas.get(position).type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_title_layout, null);
                return new TitleHolder(view);
            } else if (viewType == 1) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_content_layout, null);
                return new ContentHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == 0) {
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.textView.setText(datas.get(position).title);
            } else if (type == 1) {

            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class TitleHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public TitleHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_text);
            }
        }

        class ContentHolder extends RecyclerView.ViewHolder {
            public ContentHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
