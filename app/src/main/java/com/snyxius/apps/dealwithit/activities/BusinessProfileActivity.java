package com.snyxius.apps.dealwithit.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.BusinessProfileListAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.utils.DividerItemDecoration;
import com.snyxius.apps.dealwithit.utils.EndlessRecyclerOnScrollListener;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;
import com.snyxius.apps.dealwithit.utils.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by snyxius on 25/11/15.
 */
public class BusinessProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    RecyclerView mRecyclerView;
    BusinessProfileListAdapter adapter;
    ProgressBar progressBar;
    ArrayList<AllPojos> estTypeListArray;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_drawer, new DrawerFragment(), Constants.DRAWER_FRAGMENT)
                .commit();
        progressBar = (ProgressBar) findViewById(R.id.pBar);
        initDrawer();
        initRecyclerView();

        try {

            if (DealWithItApp.isNetworkAvailable()) {
                String s = DealWithItApp.readFromPreferences(this, Keys.id, Constants.DEFAULT_STRING);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate(Keys.id, s);
                jsonObject.accumulate(Keys.offset, String.valueOf(Constants.DEFAULT_INT));
                jsonObject.accumulate(Keys.limit, String.valueOf(Constants.LIMIT));
                new getAllBusinessProfile().execute(jsonObject.toString());

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //add ItemDecoration
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(2));
        //or
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this));
        //or
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0) {
                            Intent intn = new Intent(BusinessProfileActivity.this, CreateBusinessProfileActivity.class);
                            startActivity(intn);
                            finish();
                        }
                    }
                })
        );

    }

    private class getAllBusinessProfile extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.allBuisnessProf);
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
                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);

                    JSONArray jArray = obj.getJSONArray(Keys.allProfiles);

                    estTypeListArray = new ArrayList<>();
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject1 = jArray.getJSONObject(i);
                            AllPojos cp = new AllPojos();
                            cp.setProfile_id(jsonObject1.getString(Keys.profileId));
                            cp.setBusiness_name(jsonObject1.getString(Keys.business_name));
                            cp.setCover_image(jsonObject1.getString(Keys.cover_image));
                            cp.setLocation_name(jsonObject1.getString(Keys.location_name));
                            cp.setCategory(jsonObject1.getString(Keys.category));
                            estTypeListArray.add(cp);
                        }
                    }

                    adapter = new BusinessProfileListAdapter(getApplicationContext(), estTypeListArray);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
                        @Override
                        public void onLoadMore(int current_page) {
                            try {

                                if (DealWithItApp.isNetworkAvailable()) {
                                    String s = DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id, Constants.DEFAULT_STRING);
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.accumulate(Keys.id, s);
                                    jsonObject.accumulate(Keys.offset, String.valueOf(current_page));
                                    jsonObject.accumulate(Keys.limit, String.valueOf(Constants.LIMIT));
                                    new getScrollAllBusinessProfile().execute(jsonObject.toString());

                                } else {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });

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




    private class getScrollAllBusinessProfile extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.allBuisnessProf);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.setVisibility(View.GONE);
            onScrollDone(jsonObject);
        }
    }

    private void onScrollDone(JSONObject jsonObject) {
        try {

            if (jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);

                    JSONArray jArray = obj.getJSONArray(Keys.allProfiles);


                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject1 = jArray.getJSONObject(i);
                            AllPojos cp = new AllPojos();
                            cp.setProfile_id(jsonObject1.getString(Keys.profileId));
                            cp.setBusiness_name(jsonObject1.getString(Keys.business_name));
                            cp.setCover_image(jsonObject1.getString(Keys.cover_image));
                            cp.setLocation_name(jsonObject1.getString(Keys.location_name));
                            cp.setCategory(jsonObject1.getString(Keys.category));
                            estTypeListArray.add(cp);
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
    private void initDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
