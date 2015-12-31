package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */

public class BusinessProfileIncomingDealDialogFragment extends DialogFragment implements View.OnClickListener{

    private TextView leftIndexValue;
    private RangeBar rangebar;
    private EditText max_guest,deal_offering;
    private CheckBox checkBox_Alcohol;
    private String alcohol = "No";
    IncomingDeals incomingDeal;
    EditIncomingDeals editIncomingDeals;
    static  int positions ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            Log.d("Position", String.valueOf(positions));
            if(positions == Constants.INT_ONE){
                incomingDeal = (IncomingDeals) activity;
            }else{
                editIncomingDeals = (EditIncomingDeals) activity;
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }


    public static BusinessProfileIncomingDealDialogFragment newInstance(int position) {
        positions = position;
        BusinessProfileIncomingDealDialogFragment f = new BusinessProfileIncomingDealDialogFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_business_profile_deals, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        view.findViewById(R.id.button_delete).setOnClickListener(this);
        rangebar = (RangeBar) view.findViewById(R.id.rangebar1);
        leftIndexValue = (TextView) view.findViewById(R.id.leftIndexValue);
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
        view.findViewById(R.id.save).setOnClickListener(this);

        deal_offering=(EditText)view.findViewById(R.id.deal_offering);
        max_guest=(EditText)view.findViewById(R.id.max_guest);
        checkBox_Alcohol =(CheckBox)view.findViewById(R.id.checkBox_Alcohol);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete:
                dismiss();
                break;
            case R.id.save:
                validate(positions);
                break;
            case R.id.add_incoming_deals:
                break;
        }

    }


    public void validate( int position){
        if(max_guest.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Maximum Guest");
        }else if(leftIndexValue.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Range cost/person");
        } else if(deal_offering.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select Deal Offering");
        } else{


           sendBasicData(position);
        }
    }


    private void sendBasicData(int position){
        try{

            if(checkBox_Alcohol.isChecked()){
                alcohol = "Yes";
            } else if(!checkBox_Alcohol.isChecked()) {
                alcohol = "No";
            }


            ArrayList<AllPojos> data = new ArrayList<>();
            AllPojos pojos = new AllPojos();
            pojos.setMax_guest(max_guest.getText().toString());
            pojos.setCost_per_person(leftIndexValue.getText().toString());
            pojos.setAlcohol(alcohol);
            pojos.setDeal_offering(deal_offering.getText().toString());
            data.add(pojos);



            if (position == Constants.INT_ONE){
                incomingDeal.sendDealsCategoryData(data);
            }else {
                editIncomingDeals.sendDealsCategoryData(data);
            }

            dismiss();

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }



    private void onDone(JSONObject jsonObject) {
        try{
            if(jsonObject != null){
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    DealWithItApp.saveToPreferences(getActivity(),Keys.profileId,jsonObject.getString(Keys.profileId));
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


    public interface IncomingDeals {
        void sendDealsCategoryData(ArrayList<AllPojos> data);
    }

    public interface EditIncomingDeals {
        void sendDealsCategoryData(ArrayList<AllPojos> data);
    }
}