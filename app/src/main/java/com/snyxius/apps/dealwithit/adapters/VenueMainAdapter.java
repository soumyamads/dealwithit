package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amanjham on 27/01/16.
 */
public class VenueMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER=0;
    private static final int TYPE_MIDDLE=1;
//    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private LayoutInflater inflater;
    List<AllPojos> mListData;
//    ArrayList<AllPojos> arrayBasic1 = new ArrayList<>();
    ArrayList<String> arrayMenuImages1= new ArrayList<>();
    ArrayList<String> arrayPhotosImages1 = new ArrayList<>();
    ArrayList<String> arraytype1 = new ArrayList<>();
    ArrayList<String> arrayCuisine = new ArrayList<>();
    ArrayList<String> arrayAmbiance1 = new ArrayList<>();
    ArrayList<AllPojos> arrayDetails1 = new ArrayList<>();
    ArrayList<AllPojos> arrayDeals1=new ArrayList();


    public VenueMainAdapter(Context context, ArrayList<AllPojos> dummyData) {
        mListData = dummyData;
        mContext = context;
        inflater=LayoutInflater.from(context);
        this.mListData = mListData;

    }

//    public VenueMainAdapter(Context context, ArrayList<AllPojos> arrayBasic, ArrayList<String> arrayMenuImages, ArrayList<String> arrayPhotosImages, ArrayList<String> arraytype, ArrayList<String> arrayCuisine, ArrayList<String> arrayAmbiance, ArrayList<AllPojos> arrayDetails, ArrayList<AllPojos> arrayDeals) {
//        mContext = context;
//        inflater=LayoutInflater.from(context);
//       arrayAmbiance1.addAll(arrayAmbiance);
//        arrayBasic1.addAll(arrayBasic);
//
//        arrayMenuImages1=arrayMenuImages;
//        arrayBasic1=arrayBasic;
//        arrayDeals1=arrayDeals;
//        arrayPhotosImages1=arrayPhotosImages;
//        arraytype1=arraytype;
//        arrayCuisine=arrayCuisine;
//        arrayDetails1=arrayDetails;
//
//
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER){
            View view=inflater.inflate(R.layout.venue_header_items, parent,false);
            HeaderHolder holder=new HeaderHolder(view);
            return holder;
        }
        else if(viewType == TYPE_MIDDLE){
            View view=inflater.inflate(R.layout.venue_middle_item_layout, parent,false);
            ItemRowHolder holder = new ItemRowHolder(view);
            return holder;
        }else {
            return  null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderHolder ){
            HeaderHolder headerHolder= (HeaderHolder) holder;
            headerHolder.stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
            headerHolder.rat.setRating(4.0f);
            headerHolder.bussinessname.setText(mListData.get(position).getBusiness_name());
            headerHolder.locationname.setText(mListData.get(position).getLocation_name());
            headerHolder.capacity.setText(mListData.get(position).getCapacity());
            headerHolder.descriptions.setText(mListData.get(position).getDescription());
//            headerHolder.cuisines.setText(arrayCuisine.get(position).getCuisine().get(position)+" ,");


        }

        else if(holder instanceof ItemRowHolder ){
            ItemRowHolder headerHolder= (ItemRowHolder) holder;
//            final String sectionName = mListData.get(position-1).getHeaderTitle();
//            ArrayList singleSectionItems = mListData.get(position-1).getAllItemsInSection();
//            headerHolder.itemTitle.setText(sectionName);
//            VenueChildAdapter itemListDataAdapter = new VenueChildAdapter(mContext, singleSectionItems);
//            headerHolder.recycler_view_list.setHasFixedSize(true);
//            headerHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//            headerHolder.recycler_view_list.setAdapter(itemListDataAdapter);
//            headerHolder.recycler_view_list.setNestedScrollingEnabled(false);
//            headerHolder.btnMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(v.getContext(), "click event on more, " + sectionName, Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }else {
            return TYPE_MIDDLE;
        }
    }

    @Override
    public int getItemCount() {
        return (null != mListData ? mListData.size()+1 : 0);
    }

    class HeaderHolder extends RecyclerView.ViewHolder {


        protected TextView itemTitle,locationname,bussinessname,capacity,cuisines,descriptions;
        protected RatingBar rat;
        protected LayerDrawable stars;

        public HeaderHolder(View itemView) {
            super(itemView);

            this.rat = (RatingBar) itemView.findViewById(R.id.myRatingBar);
            this.stars = (LayerDrawable) this.rat.getProgressDrawable();
            this.bussinessname=(TextView)itemView.findViewById(R.id.bussinessname);
            this.locationname=(TextView)itemView.findViewById(R.id.locationname);
            this.capacity=(TextView)itemView.findViewById(R.id.capacityvalue);
            this.cuisines=(TextView)itemView.findViewById(R.id.cuisine);
            this.descriptions=(TextView)itemView.findViewById(R.id.description);




//            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
//            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
//            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
//            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);

        }

    }



    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;


        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);


        }
    }
}
