package com.snyxius.apps.dealwithit.adapters;

import android.app.Activity;
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
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 7/4/16.
 */
public class TempAdapter  extends RecyclerView.Adapter<TempAdapter.MyViewHolder> {

    List<AllPojos> mListData;

    public TempAdapter(List<AllPojos> mListData) {
        this.mListData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newtemplatelist,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}