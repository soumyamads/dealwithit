package com.snyxius.apps.dealwithit.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileIncomingDealFragment;
import com.snyxius.apps.dealwithit.fragments.AmbienceTypeFragment;
import com.snyxius.apps.dealwithit.fragments.BusinessCreatedDialogFragment;
import com.snyxius.apps.dealwithit.fragments.BusinessProfileIncomingDealDialogFragment;
import com.snyxius.apps.dealwithit.fragments.CategoryFragment;
import com.snyxius.apps.dealwithit.fragments.CuisineTypeFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessIncomingDeals;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.EditBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.GridImageFragment;
import com.snyxius.apps.dealwithit.fragments.ProgressBarFrament;
import com.snyxius.apps.dealwithit.fragments.ShowImageGrid;
import com.snyxius.apps.dealwithit.fragments.TypeFragment;
import com.snyxius.apps.dealwithit.fragments.WarningDialogFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 11/12/15 AD.
 */
public class EditBusinessProfile extends AppCompatActivity implements View.OnClickListener, CategoryFragment.DataPassListener,ShowImageGrid.DeletePosition,GridImageFragment.DeletePosition,EditBusinessProfileBasicFragment.AddMenuImages,
        EditBusinessProfileDetailFragment.PassData,TypeFragment.DataPassListener,
        AmbienceTypeFragment.DataPassListener,CuisineTypeFragment.DataPassListener,BusinessProfileIncomingDealDialogFragment.EditIncomingDeals{


    private Dialog dialogs;
    private ArrayList<AllPojos> arrayBasic;
    private ArrayList<AllPojos> arrayDetails;
    private ArrayList<AllPojos> arrayDeals;
    private ArrayList<String> arrayMenuImages = new ArrayList<>();
    private ArrayList<String> arrayPhotosImages = new ArrayList<>();
    private ArrayList<String> arraytype;
    private ArrayList<String> arrayCuisine;
    private ArrayList<String> arrayAmbiance;
    JSONObject jsonObject1 = new JSONObject();
//    String category_name;

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
                validateBasic();
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
                validateBasic();
                break;
        }
    }

    private void validateBasic() {
        EditBusinessProfileBasicFragment f = (EditBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileBasicFragment);
        int i = f.validateBusiness();
        switch(i){
            case Constants.INT_ONE:
                DealWithItApp.showAToast("Please select the Establishment Name");
                break;
            case Constants.INT_TWO:
                DealWithItApp.showAToast("Please select the Category");
                break;
            case Constants.INT_THREE:
                DealWithItApp.showAToast("Please select the Location Name");
                break;
            case Constants.INT_FOUR:
                DealWithItApp.showAToast("Please select the Address");
                break;
            case Constants.INT_FIVE:
                DealWithItApp.showAToast("Please select the Description");
                break;
            case Constants.INT_SIX:
                DealWithItApp.showAToast("Please upload menu first");
                break;
            case Constants.INT_SEVEN:
                DealWithItApp.showAToast("Please cover picture first");
                break;
            case Constants.INT_EIGHT:
                Log.d("Data", f.sendBasicData().toString());
                jsonObject1  =  f.sendBasicData();
                validateDetails();
                break;

        }


    }

    private void validateDetails() {
        EditBusinessProfileDetailFragment f = (EditBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileDetailFragment);
        int i = f.validate();
        switch(i){
            case Constants.INT_ONE:
                DealWithItApp.showAToast("Please select the Type");
                break;
            case Constants.INT_TWO:
                DealWithItApp.showAToast("Please select the Cuisine");
                break;
            case Constants.INT_THREE:
                DealWithItApp.showAToast("Please select the Ambience");
                break;
            case Constants.INT_FOUR:
                DealWithItApp.showAToast("Please select First start Hour Slot");
                break;
            case Constants.INT_FIVE:
                DealWithItApp.showAToast("Please select the Description");
                break;
            case Constants.INT_SIX:
                DealWithItApp.showAToast("Please select Second start Hour Slot");
                break;
            case Constants.INT_SEVEN:
                DealWithItApp.showAToast("Please select Second end Hour Slot");
                break;
            case Constants.INT_EIGHT:
                DealWithItApp.showAToast("Please select the Maximum Seatings");
                break;
            case Constants.INT_NINE:

                jsonObject1  =  f.sendBasicData(jsonObject1);

                validateIncomingDeals();
                break;

        }


    }


    private void validateIncomingDeals(){
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.edit_business_profile_dealscontainer);

            if (fragment instanceof EditBusinessIncomingDeals) {
                Log.d("fragment", " instance");

                int i = ((EditBusinessIncomingDeals) fragment).validate();

                switch (i) {
                    case Constants.INT_ONE:
                        DealWithItApp.showAToast("Please add atleast one incoming Deal");
                        break;
                    case Constants.INT_TWO:

                        JSONArray jsonArray = ((EditBusinessIncomingDeals) fragment).sendBasicData();
                        jsonObject1.accumulate(Keys.incoming_deals, jsonArray);
                        String id = DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id, Constants.DEFAULT_STRING);
                        JSONObject object = new JSONObject();
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.accumulate(Keys.profile,jsonObject1);
                        object.accumulate(Keys.business, jsonObject2);
                        object.accumulate(Keys.id, id);
                        object.accumulate(Keys.businessprofilesIdss, DealWithItApp.readFromPreferences(getApplicationContext(), Keys.businessprofilesIdss, Constants.DEFAULT_STRING));

                        if (DealWithItApp.isNetworkAvailable()) {
                            Log.d("Object", object.toString());
                            new sendBusinessProfileData().execute(object.toString());
                        } else {

                        }
                        break;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
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
        Log.d("ImageSizeBeforeRemoving",String.valueOf(arrayMenuImages.size()));

        arrayPhotosImages.remove(position);
        Log.d("ImageSizeAfterRemoving", String.valueOf(arrayMenuImages.size()));
        ShowImageGrid f = (ShowImageGrid) getSupportFragmentManager().findFragmentByTag(Constants.ShowImageGrid);
        f.removeItems(position);

    }

    @Override
    public void addItems(ArrayList<String> arrayList,int position) {

//        Log.d("ImageSizeGridAdding",String.valueOf(arrayList.size()));
//        arrayMenuImages.clear();
//        arrayMenuImages.addAll(arrayList);
//        Log.d("ImageSizeGrid", String.valueOf(arrayMenuImages.size()));
        if(position == Constants.INT_ONE) {
            GridImageFragment f = (GridImageFragment) getSupportFragmentManager().findFragmentByTag(Constants.GRIDIMAGE_FRAGMENT);
            f.addItems(arrayMenuImages);
        }else if(position == Constants.INT_TWO){
            ShowImageGrid f = (ShowImageGrid) getSupportFragmentManager().findFragmentByTag(Constants.ShowImageGrid);
            f.addItems(arrayPhotosImages);
        }
    }

    @Override
    public void setCuisineData(String string) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                    .add(R.id.container, new CuisineTypeFragment().newInstance(string), Constants.CUISINE_FRAGMENT)
                    .addToBackStack(Constants.EditBusinessProfileDetailFragment)
                    .commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setAmbienceData(String string) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                    .add(R.id.container, new AmbienceTypeFragment().newInstance(string), Constants.AMBINENCE_FRAGMENT)
                    .addToBackStack(Constants.EditBusinessProfileDetailFragment)
                    .commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setTypeData(String string) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                    .add(R.id.container, new TypeFragment().newInstance(string), Constants.TYPE_FRAGMENT)
                    .addToBackStack(Constants.EditBusinessProfileDetailFragment)
                    .commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void passAmbienceData(String data, ArrayList<String> array) {
        try {
            EditBusinessProfileDetailFragment f = (EditBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileDetailFragment);
            f.changeAmbienceText(data, array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void passCuisineData(String data, ArrayList<String> array) {
        try {
            EditBusinessProfileDetailFragment f = (EditBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileDetailFragment);
            f.changeCuisineText(data, array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void passTypeData(String data, ArrayList<String> array) {
        try {
            EditBusinessProfileDetailFragment f = (EditBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileDetailFragment);
            f.changeTypeText(data, array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendDealsCategoryData(ArrayList<AllPojos> data) {
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.edit_business_profile_dealscontainer);

            if(fragment instanceof EditBusinessIncomingDeals){
                Log.d("fragment"," instance");
                ((EditBusinessIncomingDeals) fragment).addItem(data);
            }

          //  EditBusinessIncomingDeals f = (EditBusinessIncomingDeals) getSupportFragmentManager().findFragmentByTag(Constants.EditBusinessProfileIncomingDealFragment);
           // f.addItem(data);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeGridItem(int position) {
        Log.d("ImageSizeBeforeRemoving", String.valueOf(arrayMenuImages.size()));

        arrayMenuImages.remove(position);
        Log.d("ImageSizeAfterRemoving", String.valueOf(arrayMenuImages.size()));
        GridImageFragment f = (GridImageFragment) getSupportFragmentManager().findFragmentByTag(Constants.GRIDIMAGE_FRAGMENT);
        f.removeItems(position);
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
                          arrayPhotosImages = new ArrayList<>();
                          arraytype = new ArrayList<>();
                          arrayCuisine = new ArrayList<>();
                          arrayAmbiance = new ArrayList<>();
                          arrayDetails = new ArrayList<>();
                          arrayDeals = new ArrayList<>();
                          JSONObject jsonObject1 =  jsonObject.getJSONObject(Keys.notice);
                          JSONObject jsonObject2 =  jsonObject1.getJSONObject(Keys.Profile);
                          AllPojos pojos = new AllPojos();
                          pojos.setDescription(jsonObject2.getString(Keys.description));
                          pojos.setBusiness_name(jsonObject2.getString(Keys.business_name));
//                        category_name=jsonObject2.getString(Keys.category);
                        DealWithItApp.saveToPreferences(getApplicationContext(), Keys.category,jsonObject2.getString(Keys.category));

                          pojos.setCategory(jsonObject2.getString(Keys.category));
                          pojos.setAddress(jsonObject2.getString(Keys.Address));
                          pojos.setLocation_name(jsonObject2.getString(Keys.location_name));
                          JSONArray jsonArray = jsonObject2.getJSONArray(Keys.menu_images);
                          for(int i=0; i<jsonArray.length();i++){
                              arrayMenuImages.add(jsonArray.getString(i));
                          }
                        JSONArray jsonArray5 = jsonObject2.getJSONArray(Keys.venue_images);
                        for(int i=0; i<jsonArray5.length();i++){
                            arrayPhotosImages.add(jsonArray5.getString(i));
                        }
                          pojos.setCover_image(jsonObject2.getString(Keys.cover_image));
                          arrayBasic.add(pojos);

                        if(DealWithItApp.readFromPreferences(getApplicationContext(), Keys.category, "").equals(Keys.Restaurant)) {
                            JSONArray jsonArray1 = jsonObject2.getJSONArray(Keys.type);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                arraytype.add(jsonArray1.getString(i));
                            }

                            JSONArray jsonArray2 = jsonObject2.getJSONArray(Keys.cusine);
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                arrayCuisine.add(jsonArray2.getString(i));
                            }
                            JSONArray jsonArray3 = jsonObject2.getJSONArray(Keys.ambience);
                            for (int i = 0; i < jsonArray3.length(); i++) {
                                arrayAmbiance.add(jsonArray3.getString(i));
                            }
                        }else  if(DealWithItApp.readFromPreferences(getApplicationContext(), Keys.category,"").equals(Keys.Activities)) {
                            JSONArray jsonArray1 = jsonObject2.getJSONArray(Keys.Activ);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                arraytype.add(jsonArray1.getString(i));
                            }

                        }else  if(DealWithItApp.readFromPreferences(getApplicationContext(), Keys.category,"").equals(Keys.Spa)) {
                            JSONArray jsonArray1 = jsonObject2.getJSONArray(Keys.Spa);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                arraytype.add(jsonArray1.getString(i));
                            }

                            JSONArray jsonArray2 = jsonObject2.getJSONArray(Keys.Services);
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                arrayCuisine.add(jsonArray2.getString(i));
                            }
                        }else  if(DealWithItApp.readFromPreferences(getApplicationContext(), Keys.category,"").equals(Keys.Halls)) {
                            JSONArray jsonArray1 = jsonObject2.getJSONArray(Keys.HallType);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                arraytype.add(jsonArray1.getString(i));
                            }

                            JSONArray jsonArray2 = jsonObject2.getJSONArray(Keys.Feautures);
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                arrayCuisine.add(jsonArray2.getString(i));
                            }
                        }

                        AllPojos detailsPojos = new AllPojos();
                        detailsPojos.setTiming_slot_1_start(jsonObject2.getString(Keys.timing_slot_1_start));
                        detailsPojos.setTiming_slot_1_end(jsonObject2.getString(Keys.timing_slot_1_end));
                        detailsPojos.setTiming_slot_2_start(jsonObject2.getString(Keys.timing_slot_2_start));
                        detailsPojos.setTiming_slot_2_end(jsonObject2.getString(Keys.timing_slot_2_end));
                        detailsPojos.setMax_seating(jsonObject2.getString(Keys.max_seating));
                        arrayDetails.add(detailsPojos);


                        JSONArray jsonArray4 = jsonObject2.getJSONArray(Keys.incoming_deals);
                        for (int i = 0; i< jsonArray4.length(); i++){
                            JSONObject jsonObject3 = jsonArray4.getJSONObject(i);
                            AllPojos dealsPojo = new AllPojos();
                            dealsPojo.setMax_guest(jsonObject3.getString(Keys.max_guest));
                            dealsPojo.setCost_per_person(jsonObject3.getString(Keys.cost_per_person));
                            dealsPojo.setAlcohol(jsonObject3.getString(Keys.alcohol));
                            dealsPojo.setDeal_offering(jsonObject3.getString(Keys.deal_offering));
                            arrayDeals.add(dealsPojo);

                        }



                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_business_basiccontainer, new EditBusinessProfileBasicFragment().newInstance(arrayBasic, arrayMenuImages, arrayPhotosImages), Constants.EditBusinessProfileBasicFragment)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.gridcontainer, new GridImageFragment().newInstance(arrayMenuImages), Constants.GRIDIMAGE_FRAGMENT)
                                .commit();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.gridphotoscontainer, new ShowImageGrid().newInstance(arrayPhotosImages), Constants.ShowImageGrid)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_business_profile_detailscontainer, new EditBusinessProfileDetailFragment().newInstance(arrayDetails, arrayAmbiance, arrayCuisine, arraytype), Constants.EditBusinessProfileDetailFragment)
                                .commit();

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.edit_business_profile_dealscontainer, new EditBusinessIncomingDeals().newInstance(arrayDeals), Constants.EditBusinessProfileIncomingDealFragment)
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


    private class sendBusinessProfileData extends AsyncTask<String, Void, JSONObject> {




        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.updateBuisnessProf);
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
                        DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                        Intent intent = new Intent(getApplicationContext(),BusinessProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
}
