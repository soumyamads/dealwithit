package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.pojos.VenueModel;

import java.util.ArrayList;

/**
 * Created by amanjham on 27/01/16.
 */
public class VenueChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<VenueModel> dataList;
    private Context mContext;
    private LayoutInflater inflater;

    public VenueChildAdapter(Context context, ArrayList<VenueModel> dummyData) {
        dataList = dummyData;
        mContext = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_child_item_layout, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof ItemRowHolder ){
            ItemRowHolder headerHolder= (ItemRowHolder) holder;

        }


    }



    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {




        public ItemRowHolder(View view) {
            super(view);




        }
    }
}
