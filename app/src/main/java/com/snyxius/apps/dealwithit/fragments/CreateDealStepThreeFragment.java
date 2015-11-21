package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
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
public class CreateDealStepThreeFragment extends Fragment implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{


    int position = 0;
    LinearLayout recuringlayout;
    RadioGroup radioGroup;
    String repeats = "no";
    String recurring = "yes";
    String fixed = "no";
    TextView opening_hour,closing_hour,startdealdate,enddealdate;
    ArrayList<String> arrayListDays = new ArrayList<>();
    CheckBox monday,tuesday,wednesday,thusday,friday,saturday,sunday,repeat;

    static JSONObject jsonObject = new JSONObject();

    public static CreateDealStepThreeFragment newInstance(JSONObject Object) {
        jsonObject = Object;
        CreateDealStepThreeFragment f = new CreateDealStepThreeFragment();
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_deal_step_three, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

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

    }

    private void initialise(View view){
        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.radio_fixed).setOnClickListener(this);
        view.findViewById(R.id.radio_recurring).setOnClickListener(this);
        view.findViewById(R.id.dealstrt).setOnClickListener(this);
        view.findViewById(R.id.dealexp).setOnClickListener(this);
        view.findViewById(R.id.opening_time_layout).setOnClickListener(this);
        view.findViewById(R.id.closing_time_layout).setOnClickListener(this);
        opening_hour = (TextView) view.findViewById(R.id.opening_time_text);
        closing_hour = (TextView) view.findViewById(R.id.closing_time_text);
        startdealdate = (TextView) view.findViewById(R.id.dlstart);
        enddealdate = (TextView) view.findViewById(R.id.dlend);
        sunday=(CheckBox)view.findViewById(R.id.sun);
        monday=(CheckBox)view.findViewById(R.id.m);
        tuesday=(CheckBox)view.findViewById(R.id.t);
        wednesday=(CheckBox)view.findViewById(R.id.w);
        thusday=(CheckBox)view.findViewById(R.id.th);
        friday=(CheckBox)view.findViewById(R.id.f);
        saturday=(CheckBox)view.findViewById(R.id.sa);
        repeat =(CheckBox)view.findViewById(R.id.repeat);
        radioGroup=(RadioGroup)view.findViewById(R.id.dateGroup);
        recuringlayout=(LinearLayout)view.findViewById(R.id.recurring_layout);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.continue_button:
                validate();
                break;
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
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        if(position == 3){
            startdealdate.setText(date);
        }else if(position == 4){
            enddealdate.setText(date);
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


    private void validate(){
        if(startdealdate.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Start Deal Date");
        }else if(enddealdate.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the End Deal Date");
        } else if(opening_hour.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Opening Hour");
        } else if(closing_hour.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Closing Hour");
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
            }else if(radioButtonID == R.id.radio_fixed){
                recurring = "no";
                fixed = "yes";
            }


            sendBasicData();
        }
    }

    private void sendBasicData(){
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
            object.accumulate(Keys.id, id);
            JSONObject object1 = new JSONObject();
            object1.accumulate(Keys.deal, object);
            if (DealWithItApp.isNetworkAvailable()) {
                Log.d("Object",object1.toString());
                new sendCreateDealData().execute(object1.toString());
            } else {

            }


           Log.d("Deal",jsonObject.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
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