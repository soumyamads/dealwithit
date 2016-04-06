package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.snyxius.apps.dealwithit.R;

import java.util.List;

/**
 * Created by amanjham on 13/01/16.
 */
public class ShareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ResolveInfo> list;
    public OnItemClickListener mItemClickListener;
    PackageManager pm;
    LayoutInflater inflater;
    public ShareAdapter(Context context, List<ResolveInfo> listApp, OnItemClickListener mItemClickListener){
        this.context = context;
        list = listApp;
        this.mItemClickListener = mItemClickListener;
        pm = context.getPackageManager();
        inflater= LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_share_app, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder holder1 = (ItemHolder) holder;
        ResolveInfo appInfo = list.get(position);
        holder1.ivLogo.setImageDrawable(appInfo.loadIcon(pm));
        holder1.tvAppName.setText(appInfo.loadLabel(pm));
        holder1.tvPackageName.setText(appInfo.activityInfo.packageName);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, List<ResolveInfo> mTaskList);
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivLogo;
        TextView tvAppName;
        TextView tvPackageName;
        public ItemHolder(View convertView) {
            super(convertView);
            itemView.setOnClickListener(this);

            ivLogo = (ImageView) convertView.findViewById(R.id.iv_logo);
            tvAppName = (TextView) convertView.findViewById(R.id.tv_app_name);
            tvPackageName = (TextView) convertView.findViewById(R.id.tv_app_package_name);

        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition(), list);
        }
    }
}
