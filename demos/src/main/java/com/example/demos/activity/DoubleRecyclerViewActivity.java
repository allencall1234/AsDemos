package com.example.demos.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.AndroidCharacter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demos.R;
import com.example.demos.other.ItemHeaderDecoration;
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

public class DoubleRecyclerViewActivity extends FragmentActivity implements ItemHeaderDecoration.CheckListener {
    private RecyclerView listView1;
    private RecyclerView listView2;
    private String[] titles = {"手机", "电脑", "电视", "家电", "路由", "智能", "电源", "耳机", "音响", "礼品", "生活", "其他", "其他", "其他", "其他"};
    private List<String> list1;
    private List<Item> list2;

    private ItemHeaderDecoration mDecoration;
    private RvCommonAdapter titleAdapter;
    private GodsAdaper mAdapter;
    private GridLayoutManager gridLayoutManager;
    private boolean move;
    private int mIndex;
    private int selectPos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_recyclerview_layout);
        listView1 = (RecyclerView) findViewById(R.id.listview_1);
        listView2 = (RecyclerView) findViewById(R.id.listview_2);

        list1 = new ArrayList<>();
        list1.addAll(Arrays.asList(titles));

        listView1.setAdapter(titleAdapter = new RvCommonAdapter<String>(this, android.R.layout.simple_list_item_1, list1) {

            @Override
            protected void convert(ViewHolder holder, final String o, final int position) {
                if (selectPos == position) {
                    holder.setTextColor(android.R.id.text1, Color.parseColor("#00ebb8"));
                    holder.setTextSize(android.R.id.text1, 18);
                } else {
                    holder.setTextColor(android.R.id.text1, Color.BLACK);
                    holder.setTextSize(android.R.id.text1, 12);

                }

                holder.setText(android.R.id.text1, list1.get(position));
                holder.setOnClickListener(android.R.id.text1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getBaseContext(), "当前内容:" + o, Toast.LENGTH_SHORT).show();
                        moveToCenter(listView1, position);
                        selectPos = position;
                        ItemHeaderDecoration.setCurrentTag(String.valueOf(selectPos));
                        notifyDataSetChanged();

                        int count = 0;
                        for (int i = 0; i < list2.size(); i++) {
                            if (list2.get(i).type == 0) {
                                count++;
                            }
                            if (count == (position + 1)) {
                                count = i;
                                break;
                            }
                        }
                        move = true;
                        mIndex = count;
                        smoothScrollToPosition(listView2, count);
                        showToast("count = " + count);
                    }
                });
            }
        });

        initList2();

        mDecoration = new ItemHeaderDecoration(this, list2);
        listView2.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(this);
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
        listView2.setOnScrollListener(new RecyclerViewListener());
    }

    //将当前选中的item居中
    private void moveToCenter(RecyclerView rv, int position) {
        RecyclerView.LayoutManager manager = rv.getLayoutManager();
        int firstPosition = 0;
        if (manager instanceof GridLayoutManager) {
            firstPosition = ((GridLayoutManager) manager).findFirstVisibleItemPosition();
        } else if (manager instanceof LinearLayoutManager) {
            firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
        }

        View childAt = rv.getChildAt(position - firstPosition);
        if (childAt != null) {
            int y = (childAt.getTop() - rv.getHeight() / 2);
            rv.smoothScrollBy(0, y);
        }
    }

    private void smoothScrollToPosition(RecyclerView rv, int pos) {
        if (rv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
            rv.stopScroll();
        }
        RecyclerView.LayoutManager mManager = rv.getLayoutManager();

        int firstPosition = 0;
        int lastPosition = 0;

        if (mManager instanceof GridLayoutManager) {
            firstPosition = ((GridLayoutManager) mManager).findFirstVisibleItemPosition();
            lastPosition = ((GridLayoutManager) mManager).findLastVisibleItemPosition();
        } else if (mManager instanceof LinearLayoutManager) {
            firstPosition = ((LinearLayoutManager) mManager).findFirstVisibleItemPosition();
            lastPosition = ((LinearLayoutManager) mManager).findLastVisibleItemPosition();
        }

        Log.d("first--->", String.valueOf(firstPosition));
        Log.d("last--->", String.valueOf(lastPosition));
        if (pos <= firstPosition || pos > lastPosition) {
            rv.scrollToPosition(pos);
        } else {
            int top = rv.getChildAt(pos - firstPosition).getTop();
            rv.scrollBy(0, top);
        }
    }

    private void initList2() {
        list2 = new ArrayList<>();
        for (int k = 0; k < titles.length; k++) {
            list2.add(new Item(0, titles[k], String.valueOf(k)));
            int rand = new Random().nextInt(10) + 5;
            for (int i = 0; i < rand; i++) {
                list2.add(new Item(1, titles[i] + ":" + i, String.valueOf(k)));
            }
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        if (move) {
            move = false;
        } else {
            selectPos = position;
            titleAdapter.notifyDataSetChanged();
        }
        moveToCenter(listView1, position);
        ItemHeaderDecoration.setCurrentTag(String.valueOf(selectPos));
    }

    public class Item extends Tag {
        //0-title,1-content
        int type;
        String title;

        public Item(int type, String title, String tag) {
            this.type = type;
            this.title = title;
            this.tag = tag;
        }
    }

    public class Tag {
        String tag;

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            int type = getItemViewType(position);
            if (type == 0) {
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.textView.setText(datas.get(position).title);
            } else if (type == 1) {
                final ContentHolder contentHolder = (ContentHolder) holder;
                contentHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("position = " + position);
                    }
                });
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
            LinearLayout layout;

            public ContentHolder(View itemView) {
                super(itemView);
                layout = (LinearLayout) itemView.findViewById(R.id.item_content_ll);
            }
        }
    }

    private Toast mToast = null;

    public void showToast(CharSequence msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                int firstPosition = 0;
                if (manager instanceof GridLayoutManager) {
                    firstPosition = ((GridLayoutManager) manager).findFirstVisibleItemPosition();
                } else if (manager instanceof LinearLayoutManager) {
                    firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                }
                int n = mIndex - firstPosition;
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    recyclerView.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                int firstPosition = 0;
                if (manager instanceof GridLayoutManager) {
                    firstPosition = ((GridLayoutManager) manager).findFirstVisibleItemPosition();
                } else if (manager instanceof LinearLayoutManager) {
                    firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                }
                int n = mIndex - firstPosition;
                if (0 <= n && n < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(n).getTop();
                    recyclerView.scrollBy(0, top);
                }
            }
        }
    }

}
