package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 524202 on 2017/8/31.
 */

public class RecyclerviewFocusActivity extends FragmentActivity {
    private List<String> list;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_focus_layout);
        initlists();
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new FocusAdapter());
    }

    private void initlists() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("第 " + (i + 1) + "个Item:");
        }
    }

    class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.FocusHolder> {
        @Override
        public FocusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerviewFocusActivity.this)
                    .inflate(R.layout.focus_item, parent, false);
            return new FocusHolder(view);
        }

        @Override
        public void onBindViewHolder(FocusHolder holder, int position) {
            holder.setIsRecyclable(false);
            holder.textview.setText(list.get(position));
            final EditText edit = holder.edittext;
            if (edit.getTag() != null ) {
                edit.setText(edit.getTag().toString());
            }
            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edit.setTag(s.toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class FocusHolder extends RecyclerView.ViewHolder {
            TextView textview;
            EditText edittext;
            public FocusHolder(View itemView) {
                super(itemView);
                textview = (TextView) itemView.findViewById(R.id.card_text);
                edittext = (EditText) itemView.findViewById(R.id.card_edit);
            }
        }
    }
}
