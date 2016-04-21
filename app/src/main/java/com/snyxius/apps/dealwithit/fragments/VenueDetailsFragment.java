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
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.SectionDataModel;
import com.snyxius.apps.dealwithit.pojos.VenueModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 27/01/16.
 */
public class VenueDetailsFragment extends Fragment {

    static int count= Constants.DEFAULT_INT;
    RecyclerView recyclerView;

    static JSONObject jsonObject = new JSONObject();

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
        initialize(view);

        if(DealWithItApp.isNetworkAvailable()){
            JSONObject json=new JSONObject();
            try {
                json.accumulate(Keys.dealId,DealWithItApp.readFromPreferences(getContext(), Keys.dealId, ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            new BussinesDetails().execute(json.toString());

        }else{
            DealWithItApp.showAToast("Internet Unavailable");
        }
    }

    private void initialize(View view) {
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_views);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        VenueMainAdapter adapter = new VenueMainAdapter(getActivity(), createDummyData());
        recyclerView.setAdapter(adapter);
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

//    private class BussinesDetails extends AsyncTask<String, Void, JSONObject>
//
//    {
//        private ProgressDialog dialog = new ProgressDialog(getActivity());
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            this.getSupportFragmentManager().beginTransaction()
////                    .add(R.id.container_loading,new ProgressBarFrament(),Constants.PROGRESS_FRAGMENT)
////                    .commit();
//            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//            dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
////            dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
//            dialog.show();
//
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... params) {
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = WebRequest.postData(params[0], WebServices.bussinessdetails);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return jsonObject;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            super.onPostExecute(jsonObject);
////            this.getSupportFragmentManager().beginTransaction()
////                    .remove(this.getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
////                    .commit();
//            if (dialog != null && dialog.isShowing()) {
//                dialog.dismiss();
//                dialog = null;
//            }
//            onDone(jsonObject);
//        }
//    }
//
//    private void onDone(final JSONObject jsonObject) {

//            if(jsonObject != null) {
//                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
////                    Log.e("DATAAAAAAAAa", String.valueOf(jsonObject.getJSONObject(Keys.notice)));
//                    JSONObject jobj=jsonObject.getJSONObject(Keys.notice);
//                    JSONArray jarr=jobj.getJSONArray(Keys.deals);
//                    Log.e("Detailsfrag",jarr.toString());
////                    DealWithItApp.saveToPreferences(getActivity(),"Dealdetails",String.valueOf(jarr));
//
//                    ArrayList<AllPojos> getbussinessdetails=new ArrayList<>();
//                    AllPojos pojos=new AllPojos();
//
//                    for(int i=0;i<jarr.length();i++){
//                        JSONObject jobj2=jarr.getJSONObject(i);
//                        pojos.setLocation_name(jobj2.getString(Keys.location_name));
//                        pojos.setBusiness_name(jobj2.getString(Keys.business_name));
//                        pojos.setDescription(jobj2.getString(Keys.description));
//                        pojos.setCapacity(jobj2.getString(Keys.capacity));
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
//                        try {
//                            DealWithItApp.showAToast("Something Went Wrong. Server is not responding");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
////                    }
////                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}
