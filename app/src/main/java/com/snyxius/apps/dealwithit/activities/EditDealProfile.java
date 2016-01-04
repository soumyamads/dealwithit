package com.snyxius.apps.dealwithit.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.snyxius.apps.dealwithit.fragments.CreateDealStepOneFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessIncomingDeals;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.EditDealStepOneFragment;
import com.snyxius.apps.dealwithit.fragments.EditDealStepThreeFragment;
import com.snyxius.apps.dealwithit.fragments.EditDealStepTwoFragment;
import com.snyxius.apps.dealwithit.fragments.GetAllBusinessProfileFragment;
import com.snyxius.apps.dealwithit.fragments.WarningDialogFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 11/12/15 AD.
 */
public class EditDealProfile extends AppCompatActivity implements View.OnClickListener,EditDealStepOneFragment.PassData,GetAllBusinessProfileFragment.DataPassListener {


    private Dialog dialogs;
    private ArrayList<AllPojos> arrayStepOne;
    private ArrayList<AllPojos> arrayStepTwo;
    private ArrayList<AllPojos> arrayStepThree;
    private ArrayList<String> arrayBuisnessName;
    private ArrayList<String> arrayBuisnessIds;
    private ArrayList<String> arrayDays;
    private JSONObject jsonObject = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal_profile);
        if (DealWithItApp.isNetworkAvailable()) {
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(),Keys.id,Constants.DEFAULT_STRING));
                jsonObject.accumulate(Keys.dealId,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.dealId, Constants.DEFAULT_STRING));
                new GetDealProfile().execute(jsonObject.toString());
            }catch(Exception e){
                e.printStackTrace();
            }

        } else {
            DealWithItApp.showAToast("Please check internet network");
        }
        initialize();
    }

    private void initialize(){
        Log.d(Keys.dealId, DealWithItApp.readFromPreferences(getApplicationContext(), Keys.dealId, Constants.DEFAULT_STRING));
        findViewById(R.id.wrong).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
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
            case R.id.save:
                validateStepOne();
                break;
        }
    }

    private void validateStepOne() {
        EditDealStepOneFragment f = (EditDealStepOneFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditDealStepOneFragment);
        int i = f.validate();
        switch(i){
            case Constants.INT_ONE:
                DealWithItApp.showAToast("Please select the Deal Name");
                break;
            case Constants.INT_TWO:
                DealWithItApp.showAToast("Please select the Business Type");
                break;
            case Constants.INT_THREE:
                DealWithItApp.showAToast("Please give the Quick Description");
                break;
            case Constants.INT_FOUR:
                DealWithItApp.showAToast("Please give the Full Description");
                break;
            case Constants.INT_FIVE:
                Log.d("Data", f.sendBasicData().toString());
                jsonObject  =  f.sendBasicData();
                validateStepTwo();
                break;

        }


    }

    private void validateStepTwo() {
        EditDealStepTwoFragment f = (EditDealStepTwoFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditDealStepTwoFragment);
        int i = f.validate();
        switch(i){
            case Constants.INT_ONE:
                DealWithItApp.showAToast("Please select the Deal Image");
                break;
            case Constants.INT_TWO:
                DealWithItApp.showAToast("Please select the Minimum Guest");
                break;
            case Constants.INT_THREE:
                DealWithItApp.showAToast("Please give the Cost Person");
                break;
            case Constants.INT_FOUR:
                DealWithItApp.showAToast("Please select the Additional");
                break;
            case Constants.INT_FIVE:
                DealWithItApp.showAToast("Please give the Terms & Condition");
                break;
            case Constants.INT_SIX:
                DealWithItApp.showAToast("Please select the Minimum Billing");
                break;
            case Constants.INT_SEVEN:
                DealWithItApp.showAToast("Please give the Discount Percent");
                break;
            case Constants.INT_EIGHT:
                jsonObject = f.sendBasicData(jsonObject,"Minimum Guests");
                validateStepThree();
                break;
            case Constants.INT_NINE:
                jsonObject = f.sendBasicData(jsonObject,"Minimum Billings");
                validateStepThree();
                break;

        }
    }

    private void validateStepThree() {
        EditDealStepThreeFragment f = (EditDealStepThreeFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditDealStepThreeFragment);
        int i = f.validate();
        switch (i) {
            case Constants.INT_ONE:
                DealWithItApp.showAToast("Please select the Start Deal Date");
                break;
            case Constants.INT_TWO:
                DealWithItApp.showAToast("Please select the End Deal Date");
                break;
            case Constants.INT_THREE:
                DealWithItApp.showAToast("Please select the Opening Hour");
                break;
            case Constants.INT_FOUR:
                DealWithItApp.showAToast("Please select the Closing Hour");
                break;
            case Constants.INT_SIX:
                DealWithItApp.showAToast("Please atleast select one day");
                break;
            case Constants.INT_FIVE:
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = f.sendBasicData(jsonObject);

                if (DealWithItApp.isNetworkAvailable()) {
                    Log.d("Object", jsonObject1.toString());
                    new sendDealProfileData().execute(jsonObject1.toString());
                } else {

                }
                break;

        }
    }


    private class sendDealProfileData extends AsyncTask<String, Void, JSONObject> {


        private ProgressDialog progressDialog = new ProgressDialog(EditDealProfile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.updateDeal);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            onDone(jsonObject);
        }


        private void onDone(JSONObject jsonObject) {
            try {
                if (jsonObject != null) {
                    if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                        DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                        Intent intent = new Intent(getApplicationContext(),DealsActivity.class);
                        intent.putExtra(Keys.position,1);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                    } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                        DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    } else {
                        DealWithItApp.showAToast("Something Went Wrong.");
                    }
                } else {
                    DealWithItApp.showAToast("Something went wrong");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void setBusinessData(String string) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .add(R.id.container, new GetAllBusinessProfileFragment().newInstance(string), Constants.GET_ALL_BUSINESS_PROFILE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void passBusinessProfileData(String data, ArrayList<String> arrayBusinessName, ArrayList<String> arrayBusinessIds) {
        try {
            //Log.d("String",data+""+array.toString());
            EditDealStepOneFragment f = (EditDealStepOneFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditDealStepOneFragment);
            f.changeBusinessProfileText(data, arrayBusinessName,arrayBusinessIds );
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private class GetDealProfile extends AsyncTask<String, Void, JSONObject> {

        private ProgressDialog progressDialog = new ProgressDialog(EditDealProfile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

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
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            onDone(jsonObject);
        }


        private void onDone(JSONObject jsonObject) {
            try {
                if (jsonObject != null) {
                    if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                        arrayStepOne = new ArrayList<>();
                        arrayStepTwo = new ArrayList<>();
                        arrayBuisnessName = new ArrayList<>();
                        arrayBuisnessIds = new ArrayList<>();
                        arrayStepThree = new ArrayList<>();
                        arrayDays = new ArrayList<>();
                        JSONObject jsonObject1 =  jsonObject.getJSONObject(Keys.notice);
                        JSONObject jsonObject2 =  jsonObject1.getJSONObject(Keys.Deal);
                        AllPojos stepOne = new AllPojos();
                        stepOne.setDeal_Name(jsonObject2.getString(Keys.deal_name));
                        stepOne.setFull_Description(jsonObject2.getString(Keys.full_description));
                        stepOne.setQuick_Description(jsonObject2.getString(Keys.quick_description));
                        arrayStepOne.add(stepOne);
                        JSONArray jsonArray = jsonObject2.getJSONArray(Keys.businessprofilesNames);
                        for(int i= 0 ;i<jsonArray.length();i++){
                            arrayBuisnessName.add(jsonArray.getString(i));
                        }
                        JSONArray jsonArray3 = jsonObject2.getJSONArray(Keys.businessprofilesIds);
                        for(int i= 0 ;i<jsonArray3.length();i++){
                            arrayBuisnessIds.add(jsonArray3.getString(i));
                        }
                        AllPojos stepTwo = new AllPojos();
                        stepTwo.setDeal_image(jsonObject2.getString(Keys.deal_image));
                        stepTwo.setTerms_Conditions(jsonObject2.getString(Keys.terms_text));
                        stepTwo.setAdditional(jsonObject2.getString(Keys.additional));
                        stepTwo.setMaximum_Booking(jsonObject2.getString(Keys.max_boking));
                        JSONArray jsonArr = jsonObject2.getJSONArray(Keys.minimum_billig);
                        if(jsonArr.length() != 0){
                            JSONObject jsonObject3 = jsonArr.getJSONObject(Constants.DEFAULT_INT);
                            stepTwo.setMinimum_Billing(jsonObject3.getString(Keys.minimum_billig));
                            stepTwo.setDiscount_Person(jsonObject3.getString(Keys.discount_percent));
                        }else {
                            stepTwo.setChecking("Y");
                        }

                        JSONArray jsonArr1 = jsonObject2.getJSONArray(Keys.minimum_guest);
                        if(jsonArr1.length() != 0){
                            JSONObject jsonObject3 = jsonArr1.getJSONObject(Constants.DEFAULT_INT);
                            stepTwo.setMinimum_Guest(jsonObject3.getString(Keys.minimum_guest));
                            stepTwo.setCost_person(jsonObject3.getString(Keys.cost_person));
                        }else{
                            stepTwo.setChecking("N");
                        }


                        arrayStepTwo.add(stepTwo);



                        AllPojos stepThree = new AllPojos();
                        stepThree.setFix(jsonObject2.getString(Keys.fixed));
                        stepThree.setRecurring(jsonObject2.getString(Keys.recurring));
                        stepThree.setRepeat(jsonObject2.getString(Keys.repeat));
                        stepThree.setOpening_Hour(jsonObject2.getString(Keys.opening_hour));
                        stepThree.setClosing_Hour(jsonObject2.getString(Keys.closing_hour));
                        stepThree.setStart_Deal_Date(jsonObject2.getString(Keys.startdealdate));
                        stepThree.setEnd_Deal_date(jsonObject2.getString(Keys.enddealdate));
                        JSONArray jsonArrayDays = jsonObject2.getJSONArray(Keys.days);
                        if(jsonArrayDays.length() != 0){
                            for (int i=0; i < jsonArrayDays.length();i++){
                                arrayDays.add(jsonArrayDays.getString(i));
                            }
                        }
                        arrayStepThree.add(stepThree);


                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_deal_step_one, new EditDealStepOneFragment().newInstance(arrayStepOne, arrayBuisnessName,arrayBuisnessIds), Constants.EditDealStepOneFragment)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_deal_step_two, new EditDealStepTwoFragment().newInstance(arrayStepTwo), Constants.EditDealStepTwoFragment)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_deal_step_three, new EditDealStepThreeFragment().newInstance(arrayStepThree,arrayDays), Constants.EditDealStepThreeFragment)
                                .commit();

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
