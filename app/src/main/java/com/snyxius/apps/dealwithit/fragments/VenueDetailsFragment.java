package com.snyxius.apps.dealwithit.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.VenueMainAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.pojos.SectionDataModel;
import com.snyxius.apps.dealwithit.pojos.VenueModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;

/**
 * Created by amanjham on 27/01/16.
 */
public class VenueDetailsFragment extends Fragment {

    static int count= Constants.DEFAULT_INT;
    RecyclerView recyclerView;
    private ArrayList<AllPojos> arrayBasic;
    private ArrayList<AllPojos> arrayDetails;
    private ArrayList<AllPojos> arrayDeals;
    private ArrayList<String> arrayMenuImages = new ArrayList<>();
    private ArrayList<String> arrayPhotosImages = new ArrayList<>();
    private ArrayList<String> arraytype;
    private ArrayList<String> arrayCuisine;
    private ArrayList<String> arrayAmbiance;    static JSONObject jsonObject = new JSONObject();

    public static VenueDetailsFragment newInstance(JSONObject Object) {
        jsonObject = Object;
        VenueDetailsFragment f = new VenueDetailsFragment();
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyler_view_venue_details,container,false);
    }

    public static VenueDetailsFragment newInstance(int position) {
        count = position;
        Bundle args = new Bundle();
        VenueDetailsFragment fragment = new VenueDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arraytype = new ArrayList<>();
        initialize(view);

        try {
              JSONArray jsonArray=jsonObject.getJSONArray(Keys.businessprofilesIds);
            for(int i=0; i<jsonArray.length();i++){
                arraytype.add(jsonArray.getString(i));
                arraytype.get(0);
                System.out.println("arrytp"+arraytype.get(0));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(DealWithItApp.isNetworkAvailable()){
            JSONObject json=new JSONObject();
            try {
                json.accumulate(Keys.userId,DealWithItApp.readFromPreferences(getContext(), Keys.userId, ""));
                json.accumulate(Keys.businessprofilesIdss,arraytype.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new BussinesDetails().execute(json.toString());

        }else{
            DealWithItApp.showAToast("Internet Unavailable");
        }
    }

    private void initialize(View view) {
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_views);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


//        if(count == Constants.INT_ONE) {
//            view.findViewById(R.id.down_arrow1).setVisibility(View.GONE);
//        }
    }

    public ArrayList<SectionDataModel> createDummyData() {

        ArrayList<SectionDataModel> allsampleData = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {

            SectionDataModel dm = new SectionDataModel();

            if(i == 1) {
                dm.setHeaderTitle("PHOTOS");
            }else if(i == 2){
                dm.setHeaderTitle("MENU");
            }
            ArrayList<VenueModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new VenueModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allsampleData.add(dm);

        }
        return allsampleData;
    }

    private class BussinesDetails extends AsyncTask<String, Void, JSONObject>

    {
        private ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            this.getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container_loading,new ProgressBarFrament(),Constants.PROGRESS_FRAGMENT)
//                    .commit();
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
//            dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.getOneBuisnessProf);
            }catch (Exception e){
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
//            this.getSupportFragmentManager().beginTransaction()
//                    .remove(this.getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
//                    .commit();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            onDone(jsonObject);
        }
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
                    DealWithItApp.saveToPreferences(getActivity(), Keys.category,jsonObject2.getString(Keys.category));

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

                    if(DealWithItApp.readFromPreferences(getActivity(), Keys.category, "").equals(Keys.Restaurant)) {
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
                    }else  if(DealWithItApp.readFromPreferences(getActivity(), Keys.category,"").equals(Keys.Activities)) {
                        JSONArray jsonArray1 = jsonObject2.getJSONArray(Keys.Activ);
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            arraytype.add(jsonArray1.getString(i));
                        }

                    }else  if(DealWithItApp.readFromPreferences(getActivity(), Keys.category,"").equals(Keys.Spa)) {
                        JSONArray jsonArray1 = jsonObject2.getJSONArray(Keys.Spa);
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            arraytype.add(jsonArray1.getString(i));
                        }

                        JSONArray jsonArray2 = jsonObject2.getJSONArray(Keys.Services);
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            arrayCuisine.add(jsonArray2.getString(i));
                        }
                    }else  if(DealWithItApp.readFromPreferences(getActivity(), Keys.category,"").equals(Keys.Halls)) {
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

DealWithItApp.showAToast(arrayBasic.get(0).getBusiness_name());
                    VenueMainAdapter adapter = new VenueMainAdapter(getActivity(),arrayBasic);

                    recyclerView.setAdapter(adapter);



//                    ArrayList<AllPojos> getbussinessdetails=new ArrayList<>();
////                    AllPojos pojos=new AllPojos();
//
////                    for(int i=0;i<jarr.length();i++){
//                        JSONObject jobj2=jsonObject1.getJSONObject(Keys.Profile);
//                        pojos.setLocation_name(jobj2.getString(Keys.location_name));
//                        pojos.setBusiness_name(jobj2.getString(Keys.business_name));
//                        pojos.setDescription(jobj2.getString("Description"));
////                        pojos.setCapacity(jobj2.getString(Keys.capacity));
////                        pojos.setCover_image(jobj2.getString(Keys.cover_image));
//
//
//                        JSONArray jarr1=jobj2.getJSONArray(Keys.cusine);
////                        JSONArray dateArr = jobj2.getJSONArray(Keys.days);
//                        ArrayList<String> cuisineArrays = new ArrayList<>();
//                        for (int j = 0; j < jarr1.length(); j++) {
//                            cuisineArrays.add(jarr1.getString(j));
//                        }
//                        pojos.setCuisine(cuisineArrays);
//
//
//                        JSONArray jarr2=jobj2.getJSONArray(Keys.type);
////                        JSONArray dateArr = jobj2.getJSONArray(Keys.days);
//                        ArrayList<String> typeArrays = new ArrayList<>();
//                        for (int k = 0; k < jarr2.length(); k++) {
//                            typeArrays.add(jarr2.getString(k));
//                        }
//                        pojos.setType(typeArrays);
//
//                        getbussinessdetails.add(pojos);
//
////                    }
//
//                    VenueMainAdapter adapter = new VenueMainAdapter(getActivity(), getbussinessdetails);
//                    recyclerView.setAdapter(adapter);





//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.edit_business_basiccontainer, new EditBusinessProfileBasicFragment().newInstance(arrayBasic, arrayMenuImages, arrayPhotosImages), Constants.EditBusinessProfileBasicFragment)
//                            .commit();
//
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.gridcontainer, new GridImageFragment().newInstance(arrayMenuImages), Constants.GRIDIMAGE_FRAGMENT)
//                            .commit();
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.gridphotoscontainer, new ShowImageGrid().newInstance(arrayPhotosImages), Constants.ShowImageGrid)
//                            .commit();
//
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.edit_business_profile_detailscontainer, new EditBusinessProfileDetailFragment().newInstance(arrayDetails, arrayAmbiance, arrayCuisine, arraytype), Constants.EditBusinessProfileDetailFragment)
//                            .commit();
//
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.edit_business_profile_dealscontainer, new EditBusinessIncomingDeals().newInstance(arrayDeals), Constants.EditBusinessProfileIncomingDealFragment)
//                            .commit();

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

//    private void onDone(final JSONObject jsonObject) {
//        try {
//            if(jsonObject != null) {
//                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
////                    Log.e("DATAAAAAAAAa", String.valueOf(jsonObject.getJSONObject(Keys.notice)));
//                    JSONObject jobj=jsonObject.getJSONObject(Keys.notice);
//                    JSONArray jarr=jobj.getJSONArray(Keys.Profile);
//                    Log.e("Detailsfrag", jarr.toString());
//                    DealWithItApp.saveToPreferences(getActivity(), "Dealdetails", String.valueOf(jarr));
//
//                    ArrayList<AllPojos> getbussinessdetails=new ArrayList<>();
//                    AllPojos pojos=new AllPojos();
//
//                    for(int i=0;i<jarr.length();i++){
//                        JSONObject jobj2=jarr.getJSONObject(i);
//                        pojos.setLocation_name(jobj2.getString(Keys.location_name));
//                        pojos.setBusiness_name(jobj2.getString(Keys.business_name));
//                        pojos.setDescription(jobj2.getString("Description"));
//                        pojos.setCapacity(jobj2.getString(Keys.capacity));
//                        pojos.setCapacity(jobj2.getString(Keys.category));
//                        pojos.setCapacity(jobj2.getString(Keys.address));
//
//
////                        pojos.setCover_image(jobj2.getString(Keys.cover_image));
//
//
//                        JSONArray jarr1=jobj2.getJSONArray(Keys.cusine);
////                        JSONArray dateArr = jobj2.getJSONArray(Keys.days);
//                        ArrayList<String> cuisineArrays = new ArrayList<>();
//                        for (int j = 0; j < jarr1.length(); j++) {
//                            cuisineArrays.add(jarr1.getString(j));
//                        }
//                        pojos.setCuisine(cuisineArrays);
//
//
//                        JSONArray jarr2=jobj2.getJSONArray(Keys.type);
////                        JSONArray dateArr = jobj2.getJSONArray(Keys.days);
//                        ArrayList<String> typeArrays = new ArrayList<>();
//                        for (int k = 0; k < jarr2.length(); k++) {
//                            typeArrays.add(jarr2.getString(k));
//                        }
//                        pojos.setType(typeArrays);
//
//                        getbussinessdetails.add(pojos);
//
//                    }
//
//                    VenueMainAdapter adapter = new VenueMainAdapter(getActivity(), getbussinessdetails);
//                    recyclerView.setAdapter(adapter);
////                    costperson.setText(pojos.getCost_person());
////                    minimumguest.setText(pojos.getMinimum_Guest());
//
//                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
//
//                    Handler mHandler = new Handler(getActivity().getMainLooper());
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } else {
//                    Handler mHandler = new Handler(getActivity().getMainLooper());
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                DealWithItApp.showAToast("Something Went Wrong.");
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }else{
////                Handler mHandler = new Handler(getActivity().getMainLooper());
////                mHandler.post(new Runnable() {
////                    @Override
////                    public void run() {
//                try {
//                    DealWithItApp.showAToast("Something Went Wrong. Server is not responding");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                    }
////                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}

