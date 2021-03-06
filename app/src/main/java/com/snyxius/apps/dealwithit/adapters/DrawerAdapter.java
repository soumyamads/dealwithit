package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<String> mData;
String data;
    public void add(String s,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.menu_text);
        }
    }

    public DrawerAdapter(Context context, String[] data) {
        mContext = context;

        if (data != null)
            mData = new ArrayList<>(Arrays.asList(data));
        else mData = new ArrayList<>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}