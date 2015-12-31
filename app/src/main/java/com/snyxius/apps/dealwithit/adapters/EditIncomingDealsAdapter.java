package com.snyxius.apps.dealwithit.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by Windows on 04-03-2015.
 */
public class EditIncomingDealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public OnItemClickListener mItemClickListener;
    List<AllPojos> bookingsData = new ArrayList<>();
    private LayoutInflater inflater;
    private Activity context;




    public EditIncomingDealsAdapter(Activity context, ArrayList<AllPojos> data,OnItemClickListener mItemClickListener) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.mItemClickListener = mItemClickListener;
        this.bookingsData=data;

    }







//    public void addItem(ArrayList<AllPojos> data) {
//        AllPojos pojos = new AllPojos();
//        pojos.setMax_guest(data.get(0).getMax_guest());
//        pojos.setCost_per_person(data.get(0).getCost_per_person());
//        pojos.setAlcohol(data.get(0).getAlcohol());
//        pojos.setDeal_offering(data.get(0).getDeal_offering());
//        bookingsData.add(pojos);
//        notifyItemInserted(bookingsData.size());
//        Log.d("Size",String.valueOf(bookingsData.size()));
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=inflater.inflate(R.layout.business_incoming_deal_midle, parent,false);
            ItemHolder holder=new ItemHolder(view);
            return holder;

    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView max_guest,leftIndexValue,alcohol,deal_offering;
        ImageView button_delete;
        public ItemHolder(View itemView) {
            super(itemView);
            button_delete = (ImageView)itemView.findViewById(R.id.button_delete);
            button_delete.setOnClickListener(this);
            max_guest = (TextView) itemView.findViewById(R.id.max_guest);
            leftIndexValue = (TextView) itemView.findViewById(R.id.leftIndexValue);
            alcohol = (TextView) itemView.findViewById(R.id.alcohol);
            deal_offering = (TextView) itemView.findViewById(R.id.deal_offering);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition(), bookingsData);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ItemHolder ) {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.max_guest.setText(bookingsData.get(position).getMax_guest());
            itemHolder.leftIndexValue.setText(bookingsData.get(position).getCost_per_person());
            itemHolder.alcohol.setText(bookingsData.get(position).getAlcohol());
            itemHolder.deal_offering.setText(bookingsData.get(position).getDeal_offering());
        }
    }



    @Override
    public int getItemCount() {
        return  null != bookingsData ? bookingsData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {


        return position;
    }




    public interface OnItemClickListener {
        void onItemClick(View view, int position, List<AllPojos> mTaskList);
    }
}
