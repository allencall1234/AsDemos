package com.example.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 524202 on 2017/8/4.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private List<String> mdatas = null;
    private Context mContext = null;

    public TestAdapter(Context context, List<String> datas) {
        mContext = context;
        mdatas = datas;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_layout, parent, false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
        holder.textView.setText(mdatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    static class TestHolder extends RecyclerView.ViewHolder {
        TextView textView = null;

        public TestHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_content);
        }
    }
}
