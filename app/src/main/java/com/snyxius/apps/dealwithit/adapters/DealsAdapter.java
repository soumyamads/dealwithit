package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Snyxius on 11/5/2015.
 */
public class DealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public String[] estTypeData;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<AllPojos> dealsArray = null;
    private static final int PROFILE_LIST_ITEM = 1;
    private ArrayList<ArrayList<AllPojos>> businessArray = null;
    ArrayList<ArrayList<String>> daysArray = null;
    List<String> mListData;

    public DealsAdapter(Context context, ArrayList<AllPojos> dealsArray, ArrayList<ArrayList<AllPojos>> businessArray,ArrayList<ArrayList<String>> daysArray) {

        this.mContext = context;
        this.dealsArray = dealsArray;
        mInflater = LayoutInflater.from(mContext);
        this.businessArray = businessArray;
        this.daysArray = daysArray;
    }


    public DealsAdapter(List<String> mListData) {
        this.mListData = mListData;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView profileTitle, profileSubTitle;
        CircleImageView profilePic;
        ImageView editButton;

        public ViewHolder(View itemView) {
            super(itemView);
            profileTitle = (TextView) itemView.findViewById(R.id.profile_title);
            profileSubTitle = (TextView) itemView.findViewById(R.id.profile_sub_title);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profile_pic);
            editButton = (ImageView) itemView.findViewById(R.id.edit_profile);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {



                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.deals_list_item_recurring, parent, false);
                FixedHolder vh = new FixedHolder(v);
                return vh;




    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (dealsArray.get(position).getFixed().equals("yes")){
            String businessName = "";
            FixedHolder fixedHolder = (FixedHolder) holder;
            fixedHolder.deal_name.setText(dealsArray.get(position).getDeal_name());
            fixedHolder.profilePic.setImageBitmap(DealWithItApp.base64ToBitmap(dealsArray.get(position).getDeal_image()));
            fixedHolder.days_layout.setVisibility(View.GONE);
            fixedHolder.date.setText(dealsArray.get(position).getStartdealdate() + "-" + dealsArray.get(position).getEnddealdate());
            fixedHolder.date.setVisibility(View.VISIBLE);
            for(int i = 0; i< businessArray.get(position).size(); i++){
                    if(i == 0) {
                        businessName += businessArray.get(position).get(i).getBusiness_name() + "" + businessArray.get(position).get(i).getLocation_name();
                    }else{
                        businessName += ", "+businessArray.get(position).get(i).getBusiness_name() + "" + businessArray.get(position).get(i).getLocation_name();
                    }
            }
            fixedHolder.Business_location_name.setText(businessName);
            fixedHolder.date.setText(dealsArray.get(position).getStartdealdate() + "-" + dealsArray.get(position).getEnddealdate());
            fixedHolder.time_limit.setText(dealsArray.get(position).getOpening_hour() + "-" + dealsArray.get(position).getClosing_hour());

        }else if(dealsArray.get(position).getFixed().equals("no")) {
            String businessName = "";
            FixedHolder holders = (FixedHolder) holder;
            holders.deal_name.setText(dealsArray.get(position).getDeal_name());
            holders.date.setVisibility(View.GONE);
            holders.days_layout.setVisibility(View.VISIBLE);
            holders.profilePic.setImageBitmap(DealWithItApp.base64ToBitmap(dealsArray.get(position).getDeal_image()));
            for(int i = 0; i<businessArray.get(position).size(); i++){
                if(i == 0) {
                    businessName += businessArray.get(position).get(i).getBusiness_name() + "" + businessArray.get(position).get(i).getLocation_name();
                }else{
                    businessName += ", "+businessArray.get(position).get(i).getBusiness_name() + "" + businessArray.get(position).get(i).getLocation_name();
                }
            }
            holders.Business_location_name.setText(businessName);
            holders.time_limit.setText(dealsArray.get(position).getOpening_hour() + "-" + dealsArray.get(position).getClosing_hour());


                if (daysArray.get(position).contains("sunday")) {
                    holders.sunday.setChecked(true);
                }else{
                    holders.sunday.setChecked(false);
                }
                if (daysArray.get(position).contains("monday")) {
                    holders.monday.setChecked(true);
                }else{
                    holders.monday.setChecked(false);
                }
                if (daysArray.get(position).contains("tuesday")) {
                    holders.tuesday.setChecked(true);
                }else{
                    holders.tuesday.setChecked(false);
                }
                if (daysArray.get(position).contains("wednesday")) {
                    holders.wednesday.setChecked(true);
                }else{
                    holders.wednesday.setChecked(false);
                }
                if (daysArray.get(position).contains("thusday")) {
                    holders.thusday.setChecked(true);
                }else{
                    holders.thusday.setChecked(false);
                }
                if (daysArray.get(position).contains("friday")) {
                    holders.friday.setChecked(true);
                }else{
                    holders.friday.setChecked(false);
                }
                if (daysArray.get(position).contains("saturday")) {
                    holders.saturday.setChecked(true);
                }else{
                    holders.saturday.setChecked(false);
                }
            }
    }



    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return  (null != dealsArray ? dealsArray.size() : 0);
    }

//    class RecurringHolder extends RecyclerView.ViewHolder {
//        TextView deal_name,Business_location_name,time_limit;
//        CircleImageView profilePic;
//        CheckBox monday,tuesday,wednesday,thusday,friday,saturday,sunday,repeat;
//        public RecurringHolder(View itemView) {
//            super(itemView);
//            sunday=(CheckBox)itemView.findViewById(R.id.sun);
//            profilePic = (CircleImageView) itemView.findViewById(R.id.profile_pic);
//            monday=(CheckBox)itemView.findViewById(R.id.m);
//            tuesday=(CheckBox)itemView.findViewById(R.id.t);
//            wednesday=(CheckBox)itemView.findViewById(R.id.w);
//            thusday=(CheckBox)itemView.findViewById(R.id.th);
//            friday=(CheckBox)itemView.findViewById(R.id.f);
//            saturday=(CheckBox)itemView.findViewById(R.id.sa);
//            deal_name = (TextView)itemView.findViewById(R.id.deal_name);
//            Business_location_name = (TextView)itemView.findViewById(R.id.Business_location_name);
//            time_limit = (TextView)itemView.findViewById(R.id.time_limit);
//        }
//    }


    class FixedHolder extends RecyclerView.ViewHolder {
        TextView deal_name,Business_location_name,date,time_limit;
        CircleImageView profilePic;
        CheckBox monday,tuesday,wednesday,thusday,friday,saturday,sunday;
        LinearLayout days_layout;
        public FixedHolder(View itemView) {
            super(itemView);
            days_layout = (LinearLayout)itemView.findViewById(R.id.days_layout);
            deal_name = (TextView)itemView.findViewById(R.id.deal_name);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profile_pic);
            Business_location_name = (TextView)itemView.findViewById(R.id.Business_location_name);
            date = (TextView)itemView.findViewById(R.id.date);
            time_limit = (TextView)itemView.findViewById(R.id.time_limit);
            sunday=(CheckBox)itemView.findViewById(R.id.sun);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profile_pic);
            monday=(CheckBox)itemView.findViewById(R.id.m);
            tuesday=(CheckBox)itemView.findViewById(R.id.t);
            wednesday=(CheckBox)itemView.findViewById(R.id.w);
            thusday=(CheckBox)itemView.findViewById(R.id.th);
            friday=(CheckBox)itemView.findViewById(R.id.f);
            saturday=(CheckBox)itemView.findViewById(R.id.sa);
            deal_name = (TextView)itemView.findViewById(R.id.deal_name);
            Business_location_name = (TextView)itemView.findViewById(R.id.Business_location_name);
            time_limit = (TextView)itemView.findViewById(R.id.time_limit);
        }
    }
}


