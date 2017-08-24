package com.example.all_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 524202 on 2017/8/14.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppInfoHolder> {

    private List<AppInfo> mInfos;
    private Context mContext;
    private OnItemClickListener listener;

    public AppAdapter(Context context, OnItemClickListener listener, List<AppInfo> infos) {
        mContext = context;
        mInfos = infos;
        this.listener = listener;
    }

    @Override
    public AppInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_item, parent, false);
        return new AppInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(AppInfoHolder holder, int position) {
        final AppInfo info = mInfos.get(position);
        Drawable drawable = info.getIcon();
        if (drawable != null) {
            holder.icon.setImageDrawable(drawable);
        } else {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }

        CharSequence label = info.getLabel();
        if (label != null) {
            holder.label.setText(label);
        } else {
            holder.label.setText(info.getPackageName());
        }

        holder.packageName.setText(info.getPackageName());

        if (listener != null) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(info);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }

    class AppInfoHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView label;
        TextView packageName;
        RelativeLayout layout;

        public AppInfoHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_logo);
            label = (TextView) itemView.findViewById(R.id.item_label);
            packageName = (TextView) itemView.findViewById(R.id.item_package);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        }
    }

}
