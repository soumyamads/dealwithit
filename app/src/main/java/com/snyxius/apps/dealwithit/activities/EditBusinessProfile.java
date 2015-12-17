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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.CategoryFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepOneFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepThreeFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepTwoFragment;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileIncomingDealFragment;
import com.snyxius.apps.dealwithit.fragments.ShowImageGrid;
import com.snyxius.apps.dealwithit.fragments.SuccessDialogFragment;
import com.snyxius.apps.dealwithit.fragments.WarningDialogFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 11/12/15 AD.
 */
public class EditBusinessProfile extends AppCompatActivity implements View.OnClickListener, CategoryFragment.DataPassListener,ShowImageGrid.DeletePosition,EditBusinessProfileBasicFragment.AddMenuImages{


    private Dialog dialogs;
    private ArrayList<AllPojos> arrayBasic;
    private ArrayList<AllPojos> arrayDetails;
    private ArrayList<String> arrayMenuImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_profile);
        if(savedInstanceState == null){
            if (DealWithItApp.isNetworkAvailable()) {
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(),Keys.id,Constants.DEFAULT_STRING));
                    jsonObject.accumulate(Keys.businessprofilesIdss,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.businessprofilesIdss, Constants.DEFAULT_STRING));
                    new GetBusinessProfile().execute(jsonObject.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }

            } else {
                DealWithItApp.showAToast("Please check internet network");
            }

        }

        initialize();

    }

    private void initialize(){
        Log.d(Keys.businessprofilesIdss,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.businessprofilesIdss, Constants.DEFAULT_STRING));
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
                        jsonObject.accumulate(Keys.businessprofilesIdss,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.businessprofilesIdss, Constants.DEFAULT_STRING));
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
            case R.id.save:
                EditBusinessProfileBasicFragment f = (EditBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileBasicFragment);
                int i = f.validateBusiness();
                Log.d("POSITON",String.valueOf(i));
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

    @Override
    public void passCategoryData(String data) {
        try {
            EditBusinessProfileBasicFragment f = (EditBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileBasicFragment);
            f.changeCategoryText(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeItem(int position) {
        arrayMenuImages.remove(position);
        ShowImageGrid f = (ShowImageGrid) getSupportFragmentManager().findFragmentByTag(Constants.GRIDIMAGE_FRAGMENT);
        f.removeItems(position);
    }

    @Override
    public void addItems(ArrayList<String> arrayList) {
        arrayMenuImages.addAll(arrayList);
        ShowImageGrid f = (ShowImageGrid) getSupportFragmentManager().findFragmentByTag(Constants.GRIDIMAGE_FRAGMENT);
        f.addItems(arrayMenuImages);
    }


    private class GetBusinessProfile extends AsyncTask<String, Void, JSONObject> {

        private ProgressDialog progressDialog = new ProgressDialog(EditBusinessProfile.this);

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
                return WebRequest.postData(params[0], WebServices.getOneBuisnessProf);
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
                          arrayBasic = new ArrayList<>();
                          arrayMenuImages = new ArrayList<>();
                          JSONObject jsonObject1 =  jsonObject.getJSONObject(Keys.notice);
                          JSONObject jsonObject2 =  jsonObject1.getJSONObject(Keys.Profile);
                          AllPojos pojos = new AllPojos();
                          pojos.setDescription(jsonObject2.getString(Keys.description));
                          pojos.setBusiness_name(jsonObject2.getString(Keys.business_name));
                          pojos.setCategory(jsonObject2.getString(Keys.category));
                          pojos.setLocation_name(jsonObject2.getString(Keys.location_name));
                          JSONArray jsonArray = jsonObject2.getJSONArray(Keys.menu_images);
                          for(int i=0; i<jsonArray.length();i++){
                              arrayMenuImages.add(jsonArray.getString(i));
                          }
                          pojos.setCover_image(jsonObject2.getString(Keys.cover_image));
                          arrayBasic.add(pojos);

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_business_basiccontainer, new EditBusinessProfileBasicFragment().newInstance(arrayBasic, arrayMenuImages), Constants.EditBusinessProfileBasicFragment)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.gridcontainer, new ShowImageGrid().newInstance(arrayMenuImages), Constants.GRIDIMAGE_FRAGMENT)
                                .commit();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_business_profile_detailscontainer, new EditBusinessProfileDetailFragment(), Constants.EditBusinessProfileDetailFragment)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_business_profile_dealscontainer, new EditBusinessProfileIncomingDealFragment(), Constants.EditBusinessProfileIncomingDealFragment)
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
