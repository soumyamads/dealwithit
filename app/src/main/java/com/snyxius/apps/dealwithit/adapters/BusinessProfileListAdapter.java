package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Snyxius on 11/5/2015.
 */
public class BusinessProfileListAdapter extends RecyclerView.Adapter<BusinessProfileListAdapter.ViewHolder> {
    public String[] estTypeData;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<AllPojos> mainDataList = null;
    private static final int PROFILE_LIST_HEADER = 0;
    private static final int PROFILE_LIST_ITEM = 1;
    //    private ArrayList<AllPojos> arraylist;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public BusinessProfileListAdapter(Context context, List<AllPojos> mainDataList) {
        this.mContext = context;
        this.mainDataList = mainDataList;
        mInflater = LayoutInflater.from(mContext);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == PROFILE_LIST_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.business_profile_list_header, parent, false);
            ViewHolder vh = new ViewHolder(v);


            return vh;
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.business_profile_list_item, parent, false);
            ViewHolder vh = new ViewHolder(v);

            return vh;
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != 0) {
            int currentItem = position - 1;
            holder.profileTitle.setText(mainDataList.get(currentItem).getBusiness_name() + " " + mainDataList.get(currentItem).getLocation_name());
            holder.profileSubTitle.setText(mainDataList.get(currentItem).getCategory());
            BitmapDrawable ob = new BitmapDrawable(mContext.getResources(), DealWithItApp.base64ToBitmap(mainDataList.get(currentItem).getCover_image()));
            holder.profilePic.setImageDrawable(ob);
        }
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mainDataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return PROFILE_LIST_HEADER;
        } else
            return PROFILE_LIST_ITEM;

    }
}


