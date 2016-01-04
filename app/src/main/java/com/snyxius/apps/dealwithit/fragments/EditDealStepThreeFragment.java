package com.snyxius.apps.dealwithit.fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by snyxius on 10/15/2015.
 */
public class EditDealStepThreeFragment extends Fragment implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{


    int position = 0;
    LinearLayout recuringlayout;
    RadioGroup radioGroup;
    String repeats = "no";
    String recurring = "yes";
    String fixed = "no";
    TextView opening_hour,closing_hour,startdealdate,enddealdate;
   private static ArrayList<String> arrayListDays = new ArrayList<>();
   private static ArrayList<AllPojos> arrayStepThree = new ArrayList<>();
    CheckBox monday,tuesday,wednesday,thusday,friday,saturday,sunday,repeat;


    public static EditDealStepThreeFragment newInstance(ArrayList<AllPojos> Object,ArrayList<String> days) {
        arrayStepThree = Object;
        arrayListDays = days;
        EditDealStepThreeFragment f = new EditDealStepThreeFragment();
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_deal_step_three, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);



    }










    private void initialise(View view){
        view.findViewById(R.id.dealstrt).setOnClickListener(this);
        view.findViewById(R.id.dealexp).setOnClickListener(this);
        view.findViewById(R.id.opening_time_layout).setOnClickListener(this);
        view.findViewById(R.id.closing_time_layout).setOnClickListener(this);
        opening_hour = (TextView) view.findViewById(R.id.opening_time_text);

        opening_hour.setText(arrayStepThree.get(Constants.DEFAULT_INT).getOpening_Hour());
        closing_hour = (TextView) view.findViewById(R.id.closing_time_text);
        closing_hour.setText(arrayStepThree.get(Constants.DEFAULT_INT).getClosing_Hour());
        startdealdate = (TextView) view.findViewById(R.id.dlstart);
        startdealdate.setText(arrayStepThree.get(Constants.DEFAULT_INT).getStart_Deal_Date());
        enddealdate = (TextView) view.findViewById(R.id.dlend);
        enddealdate.setText(arrayStepThree.get(Constants.DEFAULT_INT).getEnd_Deal_date());


        sunday=(CheckBox)view.findViewById(R.id.sun);
        monday=(CheckBox)view.findViewById(R.id.m);
        tuesday=(CheckBox)view.findViewById(R.id.t);
        wednesday=(CheckBox)view.findViewById(R.id.w);
        thusday=(CheckBox)view.findViewById(R.id.th);
        friday=(CheckBox)view.findViewById(R.id.f);
        saturday=(CheckBox)view.findViewById(R.id.sa);
        repeat =(CheckBox)view.findViewById(R.id.repeat);
        radioGroup=(RadioGroup)view.findViewById(R.id.dateGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_recurring) {
                    recuringlayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radio_fixed) {
                    recuringlayout.setVisibility(View.GONE);
                }
            }


        });
        recuringlayout=(LinearLayout)view.findViewById(R.id.recurring_layout);


        if(arrayStepThree.get(Constants.DEFAULT_INT).getFix().equals("yes")){
            radioGroup.check(R.id.radio_fixed);
        }else{
            radioGroup.check(R.id.radio_recurring);
            if(arrayStepThree.get(Constants.DEFAULT_INT).getRepeat().equals("yes")){
                repeat.setChecked(true);
            }

            if(arrayListDays.size() != 0){
                for(int i=0; i <arrayListDays.size() ; i++){
                    if(arrayListDays.get(i).equals("sunday")){
                        sunday.setChecked(true);
                    }else if(arrayListDays.get(i).equals("monday")){
                        monday.setChecked(true);
                    }else if(arrayListDays.get(i).equals("tuesday")){
                        tuesday.setChecked(true);
                    }else if(arrayListDays.get(i).equals("wednesday")){
                        wednesday.setChecked(true);
                    }else if(arrayListDays.get(i).equals("thusday")){
                        thusday.setChecked(true);
                    }else if(arrayListDays.get(i).equals("friday")){
                        friday.setChecked(true);
                    }else if(arrayListDays.get(i).equals("saturay")){
                        saturday.setChecked(true);
                    }
                }
            }



        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.opening_time_layout:
                position = 1;
                setTime();
                break;
            case R.id.closing_time_layout:
                position = 2;
                setTime();
                break;
            case R.id.dealstrt:
                position = 3;
                setDate();
                break;
            case R.id.dealexp:
                position = 4;
                setDate();
                break;
        }

    }



    private void setDate(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
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




        try {


            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String input_date= date;
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date dt1=format1.parse(input_date);
            DateFormat format2=new SimpleDateFormat("EEE");
            String finalDay=format2.format(dt1);

                       if (position == 3) {
                startdealdate.setText(finalDay+", "+date);
            } else if (position == 4) {
                enddealdate.setText(finalDay+", "+date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
        @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time = hourString+":"+minuteString;
        Log.d("Time",time);
        if(position == 1){
            opening_hour.setText(time);
        }else if(position == 2){
            closing_hour.setText(time);
        }
    }


    public int validate(){
        if(startdealdate.getText().toString().isEmpty()){
            return Constants.INT_ONE;

        }else if(enddealdate.getText().toString().isEmpty()){
            return Constants.INT_TWO;

        } else if(opening_hour.getText().toString().isEmpty()){
            return Constants.INT_THREE;

        } else if(closing_hour.getText().toString().isEmpty()){
            return Constants.INT_FOUR;

        } else{

            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            if(radioButtonID == R.id.radio_recurring) {
                recurring = "yes";
                fixed = "no";
                if (repeat.isChecked()) {
                    repeats = "yes";
                } else {
                    repeats = "no";
                }
                if (sunday.isChecked()) {
                    arrayListDays.add("sunday");
                }
                if (monday.isChecked()) {
                    arrayListDays.add("monday");
                }
                if (tuesday.isChecked()) {
                    arrayListDays.add("tuesday");
                }
                if (wednesday.isChecked()) {
                    arrayListDays.add("wednesday");
                }
                if (thusday.isChecked()) {
                    arrayListDays.add("thusday");
                }
                if (friday.isChecked()) {
                    arrayListDays.add("friday");
                }
                if (saturday.isChecked()) {
                    arrayListDays.add("saturay");
                }
                if(arrayListDays.size() == 0){
                    return Constants.INT_SIX;

                }else {

                    return Constants.INT_FIVE;
                }
            }else {
                recurring = "no";
                fixed = "yes";
                return Constants.INT_FIVE;
            }

        }
    }

    public JSONObject sendBasicData(JSONObject jsonObject){
        try{
            jsonObject.accumulate(Keys.recurring, recurring);
            jsonObject.accumulate(Keys.fixed, fixed);
            jsonObject.accumulate(Keys.repeat, repeats);
            jsonObject.accumulate(Keys.startdealdate, startdealdate.getText().toString());
            jsonObject.accumulate(Keys.enddealdate, enddealdate.getText().toString());
            jsonObject.accumulate(Keys.opening_hour, opening_hour.getText().toString());
            jsonObject.accumulate(Keys.closing_hour, closing_hour.getText().toString());
            JSONArray jsonArray = new JSONArray(arrayListDays);
            jsonObject.accumulate(Keys.days, jsonArray);



            String id = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
            JSONObject object = new JSONObject();
            object.accumulate(Keys.profile, jsonObject);
            JSONObject object2 = new JSONObject();
            object2.accumulate(Keys.deal,object);
            object2.accumulate(Keys.id,id);
            object2.accumulate(Keys.dealId,DealWithItApp.readFromPreferences(getActivity(), Keys.dealId, Constants.DEFAULT_STRING));

            return object2;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    private class sendCreateDealData extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);


            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_loading, new ProgressBarFrament(), Constants.PROGRESS_FRAGMENT)
                    .commit();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.saveDeal);
            }catch (Exception e){
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
//            progressBar.setVisibility(View.GONE);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(getActivity().getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
                    .commit();
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject) {
        try{
            if(jsonObject != null){
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    DialogFragment dialogFrag = BusinessCreatedDialogFragment.newInstance();
                    dialogFrag.setCancelable(false);
                    dialogFrag.show(getFragmentManager().beginTransaction(), Constants.BUSINESS_PROFILE_CREATED_DIALOG);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something went wrong");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}