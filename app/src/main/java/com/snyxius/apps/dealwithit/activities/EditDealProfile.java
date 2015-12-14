package com.snyxius.apps.dealwithit.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.WarningDialogFragment;

import org.json.JSONObject;

/**
 * Created by amanjham on 11/12/15 AD.
 */
public class EditDealProfile extends AppCompatActivity implements View.OnClickListener {


    private Dialog dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal_profile);
        initialize();
    }

    private void initialize(){
        Log.d(Keys.deletebusinessprofilesIds, DealWithItApp.readFromPreferences(getApplicationContext(), Keys.deletebusinessprofilesIds, Constants.DEFAULT_STRING));
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
                warningDialog();
                break;
            case R.id.yes:
                if (DealWithItApp.isNetworkAvailable()) {
                    try{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(),Keys.id,Constants.DEFAULT_STRING));
                        jsonObject.accumulate(Keys.dealId,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.dealId, Constants.DEFAULT_STRING));
                        new DeleteDealId().execute(jsonObject.toString());
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
        dialogs = new Dialog(EditDealProfile.this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setContentView(R.layout.warning_dialog);
        dialogs.setCancelable(false);
        TextView textView = (TextView)dialogs.findViewById(R.id.textView);
        textView.setText("Do you really want delete deal profile?");
        Button Ok = (Button)dialogs.findViewById(R.id.yes);
        Ok.setOnClickListener(this);
        Button CreditCard = (Button)dialogs.findViewById(R.id.no);
        CreditCard.setOnClickListener(this);
        dialogs.show();
    }


    private class GetDealProfile extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.getOneDeal);
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


    private class DeleteDealId extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.deleteDeal);
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
                    Intent intent = new Intent(getApplicationContext(),DealsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Keys.position, 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    dialogs.cancel();
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
