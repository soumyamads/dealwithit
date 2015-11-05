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

/**
 * Created by Snyxius on 11/5/2015.
 */
public class EstablishmentTypeAdapter extends BaseAdapter {

    public String[] employeeData;
    private Context mContext;
    private LayoutInflater mInflater;

    public EstablishmentTypeAdapter(Context context,
                                    String[] objects) {
        this.mContext = context;
        this.employeeData=objects;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.establishment_type_list_item, null);

            holder.txtName = (TextView) convertView.findViewById(R.id.rowTextView);
            holder.chkTick = (CheckBox) convertView.findViewById(R.id.CheckBox01);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int pos = position;
        holder.txtName.setText(employeeData[position] );

        holder.chkTick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                employeeData.get(pos).setSelected(isChecked);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        CheckBox chkTick;
    }

    public int getCount() {
        return employeeData.length;
    }

    @Override
    public Object getItem(int position) {
        return employeeData.length;
    }

//    public EstablishmentTypeAdapter getItem(int position) {
//        return position;
////        return employeeData.get(position);
//    }

    public long getItemId(int position) {
        return 0;
    }
}


