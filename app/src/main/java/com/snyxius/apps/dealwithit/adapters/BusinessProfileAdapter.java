package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snyxius on 11/5/2015.
 */
public class BusinessProfileAdapter extends BaseAdapter {

    public String[] estTypeData;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<AllPojos> mainDataList = null;
    private ArrayList<AllPojos> arraylist;

    public BusinessProfileAdapter(Context context,
                                  List<AllPojos> mainDataList) {
        this.mContext = context;
        this.mainDataList = mainDataList;
        mInflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<AllPojos>();
        this.arraylist.addAll(mainDataList);
//        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.establishment_type_list_item, null);

            holder.estName = (TextView) convertView.findViewById(R.id.est_type_name);
            holder.estChk = (CheckBox) convertView.findViewById(R.id.est_check_box);

            convertView.setTag(holder);
            convertView.setTag(R.id.est_type_name, holder.estName);
            convertView.setTag(R.id.est_check_box, holder.estChk);

            holder.estChk
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton vw,
                                                     boolean isChecked) {

                            int getPosition = (Integer) vw.getTag();
                            mainDataList.get(getPosition).setSelected(
                                    vw.isChecked());

                        }
                    });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int pos = position;
        holder.estChk.setTag(position);
        holder.estName.setText(mainDataList.get(position).getBusiness_name());

//        holder.estChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                employeeData.get(pos).setSelected(isChecked);
//            }
//        });
        holder.estChk.setChecked(mainDataList.get(position).isSelected());

        return convertView;
    }

    static class ViewHolder {
        TextView estName;
        CheckBox estChk;
    }

    public int getCount() {
        return mainDataList.size();
    }

    @Override
    public AllPojos getItem(int position) {
        return mainDataList.get(position);
    }

//    public EstablishmentTypeAdapter getItem(int position) {
//        return position;
////        return employeeData.get(position);
//    }

    public long getItemId(int position) {
        return position;
    }
}


