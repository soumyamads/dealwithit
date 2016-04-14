package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snyxius on 11/5/2015.
 */
public class TempTitleAdapter extends RecyclerView.Adapter<TempTitleAdapter.MyViewHolder>{



    List<AllPojos> mListData;
    private  static  CheckBox lastChecked=null;
    private static int lastCheckedPos=0;
    //    Context context;
    AppCompatActivity activity;
    passChecked pass = null;
    public static int clickedPOs;
    final CharSequence[] items = {
            "View", "Delete", "Email Template"
    };
    List<String> mAnimals;

    public TempTitleAdapter(AppCompatActivity activity,List<AllPojos> mListData) {
        this.mListData = mListData;
        this.activity=activity;
        mAnimals = new ArrayList<String>();
        mAnimals.add("View");
        mAnimals.add("Delete");
        mAnimals.add("Email Template");

    }
    @Override
    public TempTitleAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.establishment_type_list_item,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    public interface passChecked{
        void getChecked(int pos);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(TempTitleAdapter.MyViewHolder holder, int i) {

        if(holder instanceof MyViewHolder ) {
            MyViewHolder MyViewHolder = (MyViewHolder) holder;
            MyViewHolder.title.setText(mListData.get(i).getTemplate_name());

            if(i==0 && MyViewHolder.checkBox.isChecked()){
                lastChecked=MyViewHolder.checkBox;
                lastCheckedPos=0;
            }
//            MyViewHolder.description.setText(mListData.get(i).getTemplate_Description());
            MyViewHolder.checkBox.setTag(i);

            MyViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        CheckBox cb=(CheckBox)v;
                    clickedPOs=((Integer)cb.getTag()).intValue();
//                    DealWithItApp.showAToast("click"+clickedPOs);


                    if(cb.isChecked())
                    {
                        if(lastChecked != null)
                        {
                            lastChecked.setChecked(false);
//                            fonts.get(lastCheckedPos).setSelected(false);
                        }

                        lastChecked = cb;
                        lastCheckedPos = clickedPOs;
                    }
                    else
                        lastChecked = null;

//                    fonts.get(clickedPos).setSelected(cb.isChecked);
//                    pass.getChecked(clickedPOs);
//                    DealWithItApp.saveToPreferences(activity,"checkbox_pos",clickedPOs);
                    }
                });





        }

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.est_type_name);
//            description=(TextView)itemView.findViewById(R.id.content);
//            threedots=(ImageView)itemView.findViewById(R.id.dots);
checkBox=(CheckBox)itemView.findViewById(R.id.est_check_box);


        }
    }
}
