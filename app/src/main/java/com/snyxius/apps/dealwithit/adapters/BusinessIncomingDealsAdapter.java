package com.snyxius.apps.dealwithit.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMAN on 11-09-2015.
 */
public class BusinessIncomingDealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public OnItemClickListener mItemClickListener;
    List<AllPojos> bookingsData = new ArrayList<>();
    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;
    private static final int TYPE_SAVE=2;
    private LayoutInflater inflater;
    private Activity context;
    public Boolean isAllAmenitiesShowing=false;
    static Double Latitude,Longitude;



    public BusinessIncomingDealsAdapter(Activity context, ArrayList<AllPojos> data,OnItemClickListener mItemClickListener) {
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
        if(viewType==TYPE_HEADER){
            View view=inflater.inflate(R.layout.business_incoming_deal_header, parent,false);
            HeaderHolder holder=new HeaderHolder(view);
            return holder;
        }
        else if(viewType==TYPE_ITEM){
            View view=inflater.inflate(R.layout.business_incoming_deal_midle, parent,false);
            ItemHolder holder=new ItemHolder(view);
            return holder;
        }else if(viewType==TYPE_SAVE){
            View view=inflater.inflate(R.layout.business_incoming_deal_footer, parent,false);
            FooterHolder holder=new FooterHolder(view);
            return holder;
        }
        else{
            return null;
        }
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
        if(holder instanceof HeaderHolder ){

        }
        else if(holder instanceof ItemHolder ){
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.max_guest.setText(bookingsData.get(position-1).getMax_guest());
            itemHolder.leftIndexValue.setText(bookingsData.get(position-1).getCost_per_person());
            itemHolder.alcohol.setText(bookingsData.get(position-1).getAlcohol());
            itemHolder.deal_offering.setText(bookingsData.get(position-1).getDeal_offering());
        }else if(holder instanceof FooterHolder ){
            FooterHolder footerHolder=(FooterHolder) holder;


        }

    }



    @Override
    public int getItemCount() {
        return  null != bookingsData ? 1+bookingsData.size()+1 : 0;
    }

    @Override
    public int getItemViewType(int position) {


        if(position==0){
            return TYPE_HEADER;
        }

        else if((bookingsData.size()+ 1) == position){
            return TYPE_SAVE;
        }else{

             return TYPE_ITEM;
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder{

        public FooterHolder(View itemView) {
            super(itemView);
        }



    }




    class HeaderHolder extends RecyclerView.ViewHolder {


        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, List<AllPojos> mTaskList);
    }
}
