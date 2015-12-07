package com.snyxius.apps.dealwithit.adapters;

/**
 * Created by AMAN on 04-11-2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;

import java.util.List;

/**
 * Created by @vitovalov on 30/9/15.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    List<String> mListData;

    public ChatAdapter(List<String> mListData) {
        this.mListData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_user_list_item,
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

        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

}