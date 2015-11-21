package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Keys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class CreateDealStepOneFragment extends Fragment implements View.OnClickListener {


    TextView business_text;
    StepOneStroke mCallback;
    PassData passData;
    EditText quickDescription,fullDescription,deal_name;
    ArrayList<String> arrayListBusinessProfile = new ArrayList<>();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (StepOneStroke) activity;
            passData = (PassData)activity;
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
        View rootView = inflater.inflate(R.layout.create_deal_step_one, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

    }

    private void initialise(View view) {
        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.select_business_layout).setOnClickListener(this);
        business_text = (TextView) view.findViewById(R.id.select_business_text);
        quickDescription = (EditText)view.findViewById(R.id.quick_desc);
        fullDescription =  (EditText)view.findViewById(R.id.additional);
        deal_name =  (EditText)view.findViewById(R.id.deal_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                validate();
               // sendBasicData();
                break;
            case R.id.select_business_layout:
                passData.setBusinessData(business_text.getText().toString());
                break;
        }
    }
    public interface StepOneStroke {
        void setStepTwoStoke();
        void sendStepOneData(JSONObject jsonObject);
    }

    public interface PassData{
        void setBusinessData(String string);
    }

    private void validate(){
        if(deal_name.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Deal Name");
        }else if(business_text.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Business Type");
        }else if(quickDescription.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please give the Quick Description");
        }else if(fullDescription.getText().toString().isEmpty()) {
            DealWithItApp.showAToast("Please give the Full Description");
        }
        else{
            sendBasicData();
        }
    }


    private void sendBasicData(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.deal_name, deal_name.getText().toString());
            JSONArray jsonArray = new JSONArray(arrayListBusinessProfile);
            jsonObject.accumulate(Keys.businessprofilesIds, jsonArray);
            jsonObject.accumulate(Keys.quick_description, quickDescription.getText().toString());
            jsonObject.accumulate(Keys.full_description, fullDescription.getText().toString());
            mCallback.setStepTwoStoke();
            mCallback.sendStepOneData(jsonObject);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeBusinessProfileText(String string,ArrayList<String> arrayList){
        try {
            arrayListBusinessProfile = arrayList;
            business_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
