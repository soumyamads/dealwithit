package com.snyxius.apps.dealwithit.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.BusinessIncomingDealsAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by snyxius on 10/15/2015.
 */

public class AddBusinessProfileIncomingDealFragment extends Fragment {


    static  JSONObject jsonObject = new JSONObject();
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private BusinessIncomingDealsAdapter mAdapter;

    ArrayList<AllPojos> bookingsData = new ArrayList<>();

    public static   AddBusinessProfileIncomingDealFragment newInstance(JSONObject Object) {
        jsonObject = Object;
        AddBusinessProfileIncomingDealFragment f = new AddBusinessProfileIncomingDealFragment();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.business_incoming_recycler_view,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerAnimatedItems);
        mAdapter = new BusinessIncomingDealsAdapter(getActivity(), bookingsData, new BusinessIncomingDealsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<AllPojos> mTaskList) {
                if (view.getId() == R.id.button_delete) {
                    Log.d("Position",String.valueOf(position));
                    bookingsData.remove(position - 1);
                    mAdapter.notifyItemRemoved(position - 1);

                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callBack(view, position);
            }
        }));
    }


    private void callBack(View view,int position) {
        int value = 1+bookingsData.size();
        Log.d("Position","  "+value+" position "+position);
        if(position == 0) {
            DialogFragment dialogFrag = BusinessProfileIncomingDealDialogFragment.newInstance(Constants.INT_ONE);
            dialogFrag.setCancelable(false);
            dialogFrag.show(getFragmentManager().beginTransaction(), Constants.EditBusinessProfileIncomingDealDialogFragment);

        }


        if(position == value) {

            validate();
        }


    }

    public void addItem(ArrayList<AllPojos> data) {
        bookingsData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }





    public void validate(){
        if(bookingsData.isEmpty()){
            DealWithItApp.showAToast("Add atleast one incoming deals");
        }else{
            sendBasicData();
        }
    }






    public void sendBasicData(){
        try{

            JSONArray jsonArray = new JSONArray();

            for(int i=0;i<bookingsData.size();i++){

                JSONObject jsonObjectDeal = new JSONObject();
                jsonObjectDeal.put(Keys.max_guest, bookingsData.get(i).getMax_guest());
                jsonObjectDeal.put(Keys.cost_per_person, bookingsData.get(i).getCost_per_person());
                jsonObjectDeal.put(Keys.alcohol, bookingsData.get(i).getAlcohol());
                jsonObjectDeal.put(Keys.deal_offering, bookingsData.get(i).getDeal_offering());
                jsonArray.put(jsonObjectDeal);
            }

                jsonObject.accumulate(Keys.incoming_deals, jsonArray);
                String id = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
                JSONObject object = new JSONObject();
                object.accumulate(Keys.profile, jsonObject);
                object.accumulate(Keys.id, id);
                JSONObject object1 = new JSONObject();
                object1.accumulate(Keys.business, object);
                if (DealWithItApp.isNetworkAvailable()) {
                    Log.d("Object",object1.toString());
                    new sendBusinessProfileData().execute(object1.toString());
                } else {

                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private class sendBusinessProfileData extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);


            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_loading, new ProgressBarFrament(), Constants.PROGRESS_FRAGMENT)
                    .commit();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.saveBuisnessProf);
            }catch (Exception e){
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
//            progressBar.setVisibility(View.GONE);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(getActivity().getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
                    .commit();
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject) {
        try{
            if(jsonObject != null){
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    DealWithItApp.saveToPreferences(getActivity(),Keys.profileId,jsonObject.getString(Keys.profileId));
                    DialogFragment dialogFrag = BusinessCreatedDialogFragment.newInstance();
                    dialogFrag.setCancelable(false);
                    dialogFrag.show(getFragmentManager().beginTransaction(), Constants.BUSINESS_PROFILE_CREATED_DIALOG);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something went wrong");
            }

        }catch (Exception e){
                e.printStackTrace();
        }
    }



}