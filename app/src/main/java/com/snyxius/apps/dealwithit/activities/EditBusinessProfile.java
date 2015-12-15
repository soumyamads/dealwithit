package com.snyxius.apps.dealwithit.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
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
import com.snyxius.apps.dealwithit.fragments.SuccessDialogFragment;
import com.snyxius.apps.dealwithit.fragments.WarningDialogFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 11/12/15 AD.
 */
public class EditBusinessProfile extends AppCompatActivity implements View.OnClickListener {

    private RangeBar rangebar;
    TextView leftIndexValue;
    private Dialog dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_profile);
        initialize();
    }

    private void initialize(){
        Log.d(Keys.deletebusinessprofilesIds,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.deletebusinessprofilesIds, Constants.DEFAULT_STRING));
        findViewById(R.id.wrong).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        leftIndexValue = (TextView) findViewById(R.id.leftIndexValue);
        rangebar = (RangeBar) findViewById(R.id.rangebar1);
        rangebar.setPinColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setTickColor(getResources().getColor(R.color.grey));
        rangebar.setConnectingLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setBarColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setSelectorColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                leftIndexValue.setText("Rs " + leftPinValue + " - " + "Rs " + rightPinValue);
            }
        });
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
                warningDialog();
                break;
            case R.id.yes:
                if (DealWithItApp.isNetworkAvailable()) {
                    try{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(),Keys.id,Constants.DEFAULT_STRING));
                        jsonObject.accumulate(Keys.deletebusinessprofilesIds,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.deletebusinessprofilesIds, Constants.DEFAULT_STRING));
                        jsonObject.accumulate(Keys.deleteFlag,String.valueOf(Constants.DEFAULT_INT));
                        new DeleteBusinessId().execute(jsonObject.toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else {
                    DealWithItApp.showAToast("Please check internet network");
                }
                break;
            case R.id.no:
                dialogs.cancel();
                break;
        }
    }


    private void warningDialog(){
        dialogs = new Dialog(EditBusinessProfile.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.warning_dialog);
        dialogs.setCancelable(false);
        Button Ok = (Button)dialogs.findViewById(R.id.yes);
        Ok.setOnClickListener(this);
        Button CreditCard = (Button)dialogs.findViewById(R.id.no);
        CreditCard.setOnClickListener(this);
        dialogs.show();
    }


    private class GetBusinessProfile extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.getOneBuisnessProf);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            onDone(jsonObject);
        }


        private void onDone(JSONObject jsonObject) {
            try {
                if (jsonObject != null) {
                    if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {

                    } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                        DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                    } else {
                        DealWithItApp.showAToast("Something Went Wrong.");
                    }
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class DeleteBusinessId extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.deleteBuisnessProf);
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
                    dialogs.cancel();
                    Intent intent = new Intent(getApplicationContext(),BusinessProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    dialogs.cancel();
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    if(jsonObject.getString(Keys.notice).equals("Deal is present with current profile")){

                       DialogFragment dialogFrag = WarningDialogFragment.newInstance();
                       dialogFrag.setCancelable(false);
                       dialogFrag.show(getSupportFragmentManager().beginTransaction(), Constants.WARNINGDIALOG_FRAGMENT);
                   }else{

                   }
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