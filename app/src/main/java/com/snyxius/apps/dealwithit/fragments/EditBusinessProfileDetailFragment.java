package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by snyxius on 10/15/2015.
 */
public class EditBusinessProfileDetailFragment extends Fragment implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{




    //DealStroke mDealCallback;
    EditText max_seat;
    PassData passData;
    TextView slot1_start_time_text,slot1_end_time_text,ambience_text,cuisine_text,type_text;
    TextView slot2_start_time_text,slot2_end_time_text;
    private int position = 0;
    RelativeLayout typeLayout,cuisineLayout,ambienceLayout;
    static  ArrayList<AllPojos> arrayDetails = new ArrayList<>();
    static  ArrayList<String> arrayListAmbience = new ArrayList<>();
    static  ArrayList<String> arrayListCuisine = new ArrayList<>();
    static  ArrayList<String> arrayListType = new ArrayList<>();





    public static EditBusinessProfileDetailFragment newInstance(ArrayList<AllPojos> Object,ArrayList<String> arrayAmbience,
    ArrayList<String> arrayCuisine ,ArrayList<String> arrayType) {
        arrayDetails = Object;
        arrayListType = arrayType;
        arrayListAmbience = arrayAmbience;
        arrayListCuisine = arrayCuisine;
        EditBusinessProfileDetailFragment f = new EditBusinessProfileDetailFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {


                  passData = (PassData) activity;

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
        View rootView = inflater.inflate(R.layout.edit_business_profile_details, container, false);
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

        max_seat = (EditText)view.findViewById(R.id.max_seat);

        slot1_end_time_text.setText(arrayDetails.get(Constants.DEFAULT_INT).getTiming_slot_1_end());
        slot1_start_time_text.setText(arrayDetails.get(Constants.DEFAULT_INT).getTiming_slot_1_start());
        slot2_end_time_text.setText(arrayDetails.get(Constants.DEFAULT_INT).getTiming_slot_2_end());
        slot2_start_time_text.setText(arrayDetails.get(Constants.DEFAULT_INT).getTiming_slot_2_start());

        ambienceLayout= (RelativeLayout) view.findViewById(R.id.ambience_layout);
        cuisineLayout= (RelativeLayout) view.findViewById(R.id.cuisine_layout);
        typeLayout= (RelativeLayout) view.findViewById(R.id.type_layout);
        max_seat.setText(arrayDetails.get(Constants.DEFAULT_INT).getMax_seating());



        view.findViewById(R.id.cuisine_layout).setOnClickListener(this);
        view.findViewById(R.id.ambience_layout).setOnClickListener(this);
        view.findViewById(R.id.type_layout).setOnClickListener(this);

        view.findViewById(R.id.slot1_start_time_layout).setOnClickListener(this);
        view.findViewById(R.id.slot1_end_time_layout).setOnClickListener(this);
        view.findViewById(R.id.slot2_start_time_layout).setOnClickListener(this);
        view.findViewById(R.id.slot2_end_time_layout).setOnClickListener(this);



        if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Restaurant)){
            cuisineLayout.setVisibility(View.VISIBLE);
            ambienceLayout.setVisibility(View.VISIBLE);
        }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Activities)){
            cuisineLayout.setVisibility(View.GONE);
            ambienceLayout.setVisibility(View.GONE);
            type_text.setText("Activity Type");
        }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Halls)){
            ambienceLayout.setVisibility(View.GONE);
            cuisine_text.setText("Features");
            type_text.setText("Hall Type");
        }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Spa)){
            type_text.setText("Caters To");
            cuisine_text.setText("Services");
            ambienceLayout.setVisibility(View.GONE);

        }

        settingTypeData();
        settingAmbienceData();
        settingCuisineData();
    }

    private void settingTypeData() {
        StringBuffer sb = new StringBuffer();

        for(int i=0; i<arrayListType.size();i++){

            sb.append(arrayListType.get(i));
            if(i<arrayListType.size()-1) {
                sb.append(",");
            }

        }
        String s = sb.toString().trim();
        type_text.setText(s);
    }

    private void settingCuisineData() {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<arrayListCuisine.size();i++){
            sb.append(arrayListCuisine.get(i));
            if(i<arrayListCuisine.size()-1) {
                sb.append(",");
            }

        }
        String s = sb.toString().trim();
        cuisine_text.setText(s);
    }

    private void settingAmbienceData() {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<arrayListAmbience.size();i++){
            sb.append(arrayListAmbience.get(i));

            if(i<arrayListAmbience.size()-1) {
                sb.append(",");
            }

        }
        String s = sb.toString().trim();
        ambience_text.setText(s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.type_layout:
                passData.setTypeData(type_text.getText().toString());
                break;
            case R.id.cuisine_layout:
                passData.setCuisineData(cuisine_text.getText().toString());
                break;
            case R.id.ambience_layout:
                passData.setAmbienceData(ambience_text.getText().toString());
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


    public interface PassData{
        void setCuisineData(String string);
        void setAmbienceData(String string);
        void setTypeData(String string);
    }

    public interface DealStroke{
        void setDealStoke();
        void sendDetailsCategoryData(JSONObject jsonObject);
    }


    public void changeAmbienceText(String string,ArrayList<String> arrayList){
        try {
            arrayListAmbience = arrayList;
            ambience_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void changeCuisineText(String string,ArrayList<String> arrayList){
        try {
            arrayListCuisine =arrayList;
            cuisine_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void changeTypeText(String string,ArrayList<String> arrayList){
        try {
            arrayListType = arrayList;
            type_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public int validate(){

        if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Restaurant)) {
            if (type_text.getText().toString().isEmpty()) {
                return Constants.INT_ONE;
            } else if (cuisine_text.getText().toString().isEmpty()) {
                return Constants.INT_TWO;
            } else if (ambience_text.getText().toString().isEmpty()) {
                return Constants.INT_THREE;
            }
        }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Activities)) {
            if (type_text.getText().toString().isEmpty()) {
                return Constants.INT_ONE;
            }
        }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Halls)) {
            if (type_text.getText().toString().isEmpty()) {
                return Constants.INT_ONE;
            } else if (cuisine_text.getText().toString().isEmpty()) {
                return Constants.INT_TWO;
            }
        }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Spa)) {
            if (type_text.getText().toString().isEmpty()) {
                return Constants.INT_ONE;
            } else if (cuisine_text.getText().toString().isEmpty()) {
                return Constants.INT_TWO;
            }
        }

        if(slot1_start_time_text.getText().toString().isEmpty()){

            return Constants.INT_FOUR;
        }else if(slot1_end_time_text.getText().toString().isEmpty()){
            return Constants.INT_FIVE;

        }else if(slot2_start_time_text.getText().toString().isEmpty()){
            return Constants.INT_SIX;

        }else if(slot2_end_time_text.getText().toString().isEmpty()){
            return Constants.INT_SEVEN;

        }else if(max_seat.getText().toString().isEmpty()){
            return Constants.INT_EIGHT;

       }
        else{
            return Constants.INT_NINE;

        }
    }


    public JSONObject sendBasicData(JSONObject jsonObject){
        try{
            if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Restaurant)) {
                Log.d("ArrayCuisine", arrayListCuisine.toString());
                Log.d("ArrayType", arrayListType.toString());
                Log.d("ArrayAmbience", arrayListAmbience.toString());
                JSONArray arrayType = new JSONArray(arrayListType);
                JSONArray arrayCuisine = new JSONArray(arrayListCuisine);
                JSONArray arrayAmbience = new JSONArray(arrayListAmbience);
                jsonObject.accumulate(Keys.type,arrayType);
                jsonObject.accumulate(Keys.cusine, arrayCuisine);
                jsonObject.accumulate(Keys.ambience,arrayAmbience);
            }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Activities)) {
                Log.d("ArrayType", arrayListType.toString());
                JSONArray arrayType = new JSONArray(arrayListType);
                jsonObject.accumulate(Keys.Activ,arrayType);
            }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Spa)) {
                Log.d("ArraySpa", arrayListType.toString());
                Log.d("ArrayServices", arrayListCuisine.toString());
                JSONArray arrayType = new JSONArray(arrayListType);
                JSONArray arrayCuisine = new JSONArray(arrayListCuisine);
                jsonObject.accumulate(Keys.Spa,arrayType);
                jsonObject.accumulate(Keys.Services,arrayCuisine);
            }else if(DealWithItApp.readFromPreferences(getActivity().getApplicationContext(), Keys.category,"").equals(Keys.Halls)) {
                Log.d("ArrayHallType", arrayListType.toString());
                Log.d("ArrayHallFeatures", arrayListCuisine.toString());
                JSONArray arrayType = new JSONArray(arrayListType);
                JSONArray arrayCuisine = new JSONArray(arrayListCuisine);
                jsonObject.accumulate(Keys.HallType,arrayType);
                jsonObject.accumulate(Keys.Feautures,arrayCuisine);
            }






//            JSONArray arrayType = new JSONArray(arrayListType);
//            JSONArray arrayCuisine = new JSONArray(arrayListCuisine);
//            JSONArray arrayAmbience = new JSONArray(arrayListAmbience);
//            jsonObject.accumulate(Keys.type,arrayType);
//            jsonObject.accumulate(Keys.cusine,arrayCuisine);
//            jsonObject.accumulate(Keys.ambience,arrayAmbience);
            jsonObject.accumulate(Keys.timing_slot_1_start, slot1_start_time_text.getText().toString());
            jsonObject.accumulate(Keys.timing_slot_1_end, slot1_end_time_text.getText().toString());
            jsonObject.accumulate(Keys.timing_slot_2_start,slot2_start_time_text.getText().toString());
            jsonObject.accumulate(Keys.timing_slot_2_end, slot2_end_time_text.getText().toString());
            jsonObject.accumulate(Keys.max_seating, max_seat.getText().toString());
            return  jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}