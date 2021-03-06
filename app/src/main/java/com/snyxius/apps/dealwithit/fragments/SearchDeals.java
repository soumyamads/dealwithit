package com.snyxius.apps.dealwithit.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.DealsAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.utils.DividerItemDecoration;
import com.snyxius.apps.dealwithit.utils.EndlessRecyclerOnScrollListener;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;
import com.snyxius.apps.dealwithit.utils.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 04/12/15 AD.
 */
public class SearchDeals extends Fragment {



    ArrayList<AllPojos> dealsArray;
    ArrayList<AllPojos> businessArray;
    ArrayList<ArrayList<AllPojos>> mainArray;
    ArrayList<String> days;
    ArrayList<ArrayList<String>> daysArray;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private DealsAdapter adapter;
    private TextView empty;
    private static  String search1;


    public static   SearchDeals newInstance(String string) {
        search1 = string;
        SearchDeals f = new SearchDeals();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }

    private void initialize(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.pBar);
        progressBar.setVisibility(View.GONE);
        initRecyclerView(view);
        search(search1);
    }

    private void search(String search1){
        if(!search1.isEmpty()){
            search1 = DealWithItApp.replace(search1);
            try {
                if (DealWithItApp.isNetworkAvailable()) {
                    String s = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(Keys.id, s);
                    jsonObject.accumulate(Keys.offset,String.valueOf(Constants.DEFAULT_INT));
                    jsonObject.accumulate(Keys.limit,String.valueOf(Constants.LIMIT));
                    jsonObject.accumulate(Keys.search,search1);
                    Log.d("post", jsonObject.toString());
                    new getDeals().execute(jsonObject.toString());
                } else {
                    DealWithItApp.showAToast("Please check internet network");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            DealWithItApp.showAToast("First Filled Field");
        }
    }




    private void initRecyclerView(View view) {
        empty = (TextView)view.findViewById(R.id.empty);
        empty.setVisibility(View.GONE);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //add ItemDecoration
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(2));
        //or
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity()));
        //or
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.divider));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DealWithItApp.showAToast("Under Construction");
                    }
                })
        );

    }
    private class getDeals extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.getDeals);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.setVisibility(View.GONE);
            onDone(jsonObject);
        }
    }


    private void onDone(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    if(empty.getVisibility() == View.VISIBLE){
                        empty.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);
                    JSONArray jArray = obj.getJSONArray(Keys.allDeals);
                    dealsArray = new ArrayList<>();
                    mainArray = new ArrayList<>();
                    daysArray = new ArrayList<>();
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject1 = jArray.getJSONObject(i);
                            AllPojos cp = new AllPojos();
                            cp.setDeal_name(jsonObject1.getString(Keys.deal_name));
                            cp.setDeal_image(jsonObject1.getString(Keys.deal_image));
                            cp.setRecurring(jsonObject1.getString(Keys.recurring));
                            cp.setFixed(jsonObject1.getString(Keys.fixed));
                            cp.setClosing_hour(jsonObject1.getString(Keys.closing_hour));
                            cp.setOpening_hour(jsonObject1.getString(Keys.opening_hour));
                            if(jsonObject1.has(Keys.startdealdate)){
                                cp.setStartdealdate(jsonObject1.getString(Keys.startdealdate));
                            }
                            if(jsonObject1.has(Keys.enddealdate)){
                                cp.setEnddealdate(jsonObject1.getString(Keys.enddealdate));
                            }
                            JSONArray jsonArray = jsonObject1.getJSONArray(Keys.businessprofilesIds);
                            businessArray = new ArrayList<>();
                            for(int j= 0 ; j<jsonArray.length();j++){
                                JSONObject json = jsonArray.getJSONObject(j);
                                AllPojos pojos = new AllPojos();
                                pojos.setBusiness_name(json.getString(Keys.business_name));
                                pojos.setLocation_name(json.getString(Keys.location_name));
                                businessArray.add(pojos);
                            }
                            days = new ArrayList<>();
                            if(jsonObject1.has(Keys.days)){
                                JSONArray array = jsonObject1.getJSONArray(Keys.days);
                                for(int k=0 ; k< array.length();k++ ){
                                    days.add(array.getString(k));
                                }
                            }
                            daysArray.add(days);
                            dealsArray.add(cp);
                            mainArray.add(businessArray);
                        }
                    }
                    if(!dealsArray.isEmpty()) {
                        adapter = new DealsAdapter(getActivity(), dealsArray, mainArray, daysArray);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
                            @Override
                            public void onLoadMore(int current_page) {
                                try {
                                    Log.d("post1", String.valueOf(current_page));
                                    if (DealWithItApp.isNetworkAvailable()) {
                                        String s = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.accumulate(Keys.id, s);
                                        jsonObject.accumulate(Keys.offset, String.valueOf(current_page));
                                        jsonObject.accumulate(Keys.limit, String.valueOf(Constants.LIMIT));
                                        jsonObject.accumulate(Keys.search, search1);
                                        Log.d("post1", jsonObject.toString());
                                        new ScrollGetDeals().execute(jsonObject.toString());
                                    } else {

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }else{
                        empty.setText(View.VISIBLE);
                    }

                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                    mRecyclerView.setVisibility(View.GONE);


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


    private class ScrollGetDeals extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.getDeals);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            onScrollDone(jsonObject);
        }
    }

    private void onScrollDone(JSONObject jsonObject) {
        try {

            if (jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);
                    JSONArray jArray = obj.getJSONArray(Keys.allDeals);
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject1 = jArray.getJSONObject(i);
                            AllPojos cp = new AllPojos();
                            cp.setDeal_name(jsonObject1.getString(Keys.deal_name));
                            cp.setDeal_image(jsonObject1.getString(Keys.deal_image));
                            cp.setRecurring(jsonObject1.getString(Keys.recurring));
                            cp.setFixed(jsonObject1.getString(Keys.fixed));
                            cp.setClosing_hour(jsonObject1.getString(Keys.closing_hour));
                            cp.setOpening_hour(jsonObject1.getString(Keys.opening_hour));
                            if(jsonObject1.has(Keys.startdealdate)){
                                cp.setStartdealdate(jsonObject1.getString(Keys.startdealdate));
                            }
                            if(jsonObject1.has(Keys.enddealdate)){
                                cp.setEnddealdate(jsonObject1.getString(Keys.enddealdate));
                            }
                            JSONArray jsonArray = jsonObject1.getJSONArray(Keys.businessprofilesIds);
                            businessArray = new ArrayList<>();
                            for(int j= 0 ; j<jsonArray.length();j++){
                                JSONObject json = jsonArray.getJSONObject(j);
                                AllPojos pojos = new AllPojos();
                                pojos.setBusiness_name(json.getString(Keys.business_name));
                                pojos.setLocation_name(json.getString(Keys.location_name));
                                businessArray.add(pojos);
                            }
                            days = new ArrayList<>();
                            if(jsonObject1.has(Keys.days)){
                                JSONArray array = jsonObject1.getJSONArray(Keys.days);
                                for(int k=0 ; k< array.length();k++ ){
                                    days.add(array.getString(k));
                                }
                            }
                            daysArray.add(days);
                            dealsArray.add(cp);
                            mainArray.add(businessArray);
                        }
                    }

                    adapter.notifyDataSetChanged();

                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {

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
