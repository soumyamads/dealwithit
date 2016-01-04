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
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class EditDealStepOneFragment extends Fragment implements View.OnClickListener {


    TextView business_text;
    PassData passData;
    EditText quickDescription,fullDescription,deal_name;
    static  ArrayList<String> arrayListBusinessProfile ;
    private static  ArrayList<AllPojos> arrayStepOne;
    private static  ArrayList<String> arrayBusinessIds;
    public static EditDealStepOneFragment newInstance(ArrayList<AllPojos> Object,ArrayList<String> arrayBusiness,ArrayList<String> arrayBusinessIds) {
        arrayStepOne = Object;
        arrayListBusinessProfile = arrayBusiness;
        arrayBusinessIds = arrayBusinessIds;
        EditDealStepOneFragment f = new EditDealStepOneFragment();
        return f;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            passData = (PassData)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_deal_step_one, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

    }

    private void initialise(View view) {
        view.findViewById(R.id.select_business_layout).setOnClickListener(this);
        business_text = (TextView) view.findViewById(R.id.select_business_text);
        quickDescription = (EditText)view.findViewById(R.id.quick_desc);
        fullDescription =  (EditText)view.findViewById(R.id.additional);
        deal_name =  (EditText)view.findViewById(R.id.deal_name);
        deal_name.setText(arrayStepOne.get(Constants.DEFAULT_INT).getDeal_Name());
        quickDescription.setText(arrayStepOne.get(Constants.DEFAULT_INT).getQuick_Description());
        fullDescription.setText(arrayStepOne.get(Constants.DEFAULT_INT).getFull_Description());
        settingBusinessNameData();
    }

    private void settingBusinessNameData() {
        StringBuffer sb = new StringBuffer();

        for(int i=0; i<arrayListBusinessProfile.size();i++){

            sb.append(arrayListBusinessProfile.get(i));
            if(i<arrayListBusinessProfile.size()-1) {
                sb.append(",");
            }

        }
        String s = sb.toString().trim();
        business_text.setText(s);
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

    public interface PassData{
        void setBusinessData(String string);
    }

    public int validate(){
        if(deal_name.getText().toString().isEmpty()){
            return Constants.INT_ONE;

        }else if(business_text.getText().toString().isEmpty()){
            return Constants.INT_TWO;

        }else if(quickDescription.getText().toString().isEmpty()){
            return Constants.INT_THREE;

        }else if(fullDescription.getText().toString().isEmpty()) {
            return Constants.INT_FOUR;

        }
        else{
            return Constants.INT_FIVE;
        }
    }


    public JSONObject sendBasicData(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.deal_name, deal_name.getText().toString());
            JSONArray jsonArray = new JSONArray(arrayBusinessIds);
            jsonObject.accumulate(Keys.businessprofilesIds, jsonArray);
            JSONArray jsonArray1 = new JSONArray(arrayListBusinessProfile);
            jsonObject.accumulate(Keys.businessprofilesNames, jsonArray1);
            jsonObject.accumulate(Keys.quick_description, quickDescription.getText().toString());
            jsonObject.accumulate(Keys.full_description, fullDescription.getText().toString());
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void changeBusinessProfileText(String string,ArrayList<String> arrayList,ArrayList<String> arrayBusinessId){
        try {
            arrayListBusinessProfile = arrayList;
            arrayBusinessIds = arrayBusinessId;
            business_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
