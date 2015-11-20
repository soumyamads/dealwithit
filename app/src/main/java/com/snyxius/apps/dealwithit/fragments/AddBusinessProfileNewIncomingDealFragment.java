package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.IncomingDeals;
import com.snyxius.apps.dealwithit.extras.Keys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by snyxius on 10/15/2015.
 */

public class AddBusinessProfileNewIncomingDealFragment extends Fragment implements View.OnClickListener{

    IncomingDeals incomingDeals;
    TextView leftIndexValue;

    private RangeBar rangebar;

    EditText max_guest,deal_offering;

    CheckBox checkBox_Alcohol;

    String alcohol = "No";

    ProgressBar progressBar;

    Button save;

    Button add_incoming_deals;
    static JSONObject jsonObject = new JSONObject();
    static JSONArray incomingDealArray = new JSONArray();
    public static AddBusinessProfileNewIncomingDealFragment newInstance(JSONObject Object,JSONArray incomingDealArray) {
        incomingDealArray = incomingDealArray;
        jsonObject = Object;
        AddBusinessProfileNewIncomingDealFragment f = new AddBusinessProfileNewIncomingDealFragment();
        return f;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {


            incomingDeals = (IncomingDeals) activity;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_business_profile_deals, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        rangebar.setPinColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setTickColor(getResources().getColor(R.color.grey));
        rangebar.setConnectingLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setBarColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setSelectorColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                leftIndexValue.setText("Rs "+leftPinValue+" - "+ "Rs "+rightPinValue);
            }
        });


    }

    private void initialise(View view){
        save = (Button)view.findViewById(R.id.save);
        add_incoming_deals = (Button)view.findViewById(R.id.add_incoming_deals);
        save.setOnClickListener(this);
        add_incoming_deals.setOnClickListener(this);
        view.findViewById(R.id.save).setOnClickListener(this);
        progressBar=(ProgressBar)view.findViewById(R.id.pBar);
        rangebar = (RangeBar) view.findViewById(R.id.rangebar1);
        leftIndexValue = (TextView) view.findViewById(R.id.leftIndexValue);
        deal_offering=(EditText)view.findViewById(R.id.deal_offering);
        max_guest=(EditText)view.findViewById(R.id.max_guest);
        checkBox_Alcohol =(CheckBox)view.findViewById(R.id.checkBox_Alcohol);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                    validate(1);
                break;
            case R.id.add_incoming_deals:
                    validate(2);
                break;
        }

    }


    private void validate(int position){
        if(max_guest.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Maximum Guest");
        }else if(leftIndexValue.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Range cost/person");
        } else if(deal_offering.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select Deal Offering");
        } else{

            if(checkBox_Alcohol.isChecked()){
                alcohol = "Yes";
            } else if(!checkBox_Alcohol.isChecked()) {
                alcohol = "No";
            }

            if(position == 1) {
                sendBasicData(position);
            }else if(position == 2){
                sendBasicData(position);

            }
        }
    }


    private void sendBasicData(int position){
        try{


            JSONObject jsonObjectDeal = new JSONObject();
            jsonObjectDeal.put(Keys.max_guest, max_guest.getText().toString());
            jsonObjectDeal.put(Keys.cost_per_person, leftIndexValue.getText().toString());
            jsonObjectDeal.put(Keys.alcohol, alcohol);
            jsonObjectDeal.put(Keys.deal_offering, deal_offering.getText().toString());

            incomingDealArray.put(jsonObjectDeal);

            if(position == 2) {
                incomingDeals.sendDealsCategoryData(jsonObject,incomingDealArray);
            }else {
                jsonObject.accumulate(Keys.incoming_deals, incomingDealArray);
                String id = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
                JSONObject object = new JSONObject();
                object.accumulate(Keys.profile, jsonObject);
                object.accumulate(Keys.id, id);
                JSONObject object1 = new JSONObject();
                object1.accumulate(Keys.business, object);
                if (DealWithItApp.isNetworkAvailable()) {
                    Log.d("Object",object1.toString());
                    new sendBusinessProfileData().execute(object1.toString());
                } else {

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class sendBusinessProfileData extends AsyncTask<String, Void, JSONObject> {


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
                return WebRequest.postData(params[0], WebServices.saveBuisnessProf);
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

        }
    }


}