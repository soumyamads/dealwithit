package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by snyxius on 10/15/2015.
 */
public class AddBusinessProfileDetailFragment extends Fragment implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{


    DealStroke mDealCallback;
    TextView slot1_start_time_text,slot1_end_time_text,ambience_text,cuisine_text,type_text;
    TextView slot2_start_time_text,slot2_end_time_text;
    private int position = 0;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {

                  mDealCallback = (DealStroke) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_business_profile_details, container, false);
        return rootView;
     }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }

    private void initialise(View view){
        slot1_start_time_text = (TextView) view.findViewById(R.id.slot1_start_time_text);

        slot1_end_time_text = (TextView) view.findViewById(R.id.slot1_end_time_text);

        slot2_start_time_text = (TextView) view.findViewById(R.id.slot2_start_time_text);

        slot2_end_time_text = (TextView) view.findViewById(R.id.slot2_end_time_text);


        ambience_text = (TextView) view.findViewById(R.id.ambience_text);

        cuisine_text = (TextView) view.findViewById(R.id.cuisine_text);

        type_text = (TextView) view.findViewById(R.id.type_text);


        view.findViewById(R.id.continue_detail).setOnClickListener(this);
        view.findViewById(R.id.cuisine_layout).setOnClickListener(this);
        view.findViewById(R.id.ambience_layout).setOnClickListener(this);
        view.findViewById(R.id.type_layout).setOnClickListener(this);

        view.findViewById(R.id.slot1_start_time_layout).setOnClickListener(this);
        view.findViewById(R.id.slot1_end_time_layout).setOnClickListener(this);
        view.findViewById(R.id.slot2_start_time_layout).setOnClickListener(this);
        view.findViewById(R.id.slot2_end_time_layout).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continue_detail:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frmaecontainer,new AddBusinessProfileDealFragment(),Constants.ADDBUSINESSPROFILEDEAL_FRAGMENT)
                        .addToBackStack(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                        .commit();
                mDealCallback.setDealStoke();
                break;
            case R.id.type_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new TypeFragment(), Constants.TYPE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.cuisine_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new CuisineTypeFragment(), Constants.CUISINE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.ambience_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new AmbienceTypeFragment(), Constants.AMBINENCE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.slot1_start_time_layout:
                position = 1;
                setTime();
                break;
            case R.id.slot1_end_time_layout:
                position = 2;
                setTime();
                break;
            case R.id.slot2_start_time_layout:
                position = 3;
                setTime();
                break;
            case R.id.slot2_end_time_layout:
                position = 4;
                setTime();
                break;
        }
    }

    private void setTime(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "TimePicker");

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time = hourString+":"+minuteString;
        Log.d("Time",time);
        if(position == 1){
              slot1_start_time_text.setText(time);
        }else if(position == 2){
                        slot1_end_time_text.setText(time);
        }else if(position == 3){
                        slot2_start_time_text.setText(time);
        }else if(position == 4){
                      slot2_end_time_text.setText(time);
        }
    }



    public interface DealStroke{
        void setDealStoke();
    }
    public void changeAmbienceText(String string){
        try {
            ambience_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void changeCuisineText(String string){
        try {
            cuisine_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void changeTypeText(String string){
        try {
            type_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}