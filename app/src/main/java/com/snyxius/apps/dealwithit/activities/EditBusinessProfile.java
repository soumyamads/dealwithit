package com.snyxius.apps.dealwithit.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.EstablishmentTypeAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepOneFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepThreeFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepTwoFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 11/12/15 AD.
 */
public class EditBusinessProfile extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_profile);
        initialize();
    }

    private void initialize(){
        findViewById(R.id.wrong).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right:
                DealWithItApp.showAToast("Under Construction");
                break;
            case R.id.wrong:
                onBackPressed();
                break;
            case R.id.delete:
                if (DealWithItApp.isNetworkAvailable()) {
                    try{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(),Keys.id,Constants.DEFAULT_STRING));
                        jsonObject.accumulate(Keys.deletebusinessprofilesIds,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.deletebusinessprofilesIds, Constants.DEFAULT_STRING));
                        new DeleteBusinessId().execute(jsonObject.toString());

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else {
                    DealWithItApp.showAToast("Please check internet network");
                }
                break;
        }
    }


    private class DeleteBusinessId extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0],WebServices.deleteBuisnessProf);
            }catch (Exception e){

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
