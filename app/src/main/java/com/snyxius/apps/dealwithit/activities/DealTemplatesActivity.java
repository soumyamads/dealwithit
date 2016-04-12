package com.snyxius.apps.dealwithit.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.BusinessProfileListAdapter;
import com.snyxius.apps.dealwithit.adapters.TempAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.utils.DividerItemDecoration;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;
import com.snyxius.apps.dealwithit.utils.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 4/4/16.
 */
public class DealTemplatesActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Button create;
    String[] listitems = { "item01", "item02", "item03", "item04" };
    private RecyclerView mRecyclerView;

//    ListView mylist;
    TextView empty;
    private LinearLayoutManager layoutManager;

    Button done;
    ArrayList<AllPojos> estTypeListArray;
    TempAdapter adapter;
    List<AllPojos> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_template);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_drawer, new DrawerFragment(), Constants.DRAWER_FRAGMENT)
                .commit();

        initDrawer();
        initRecyclerView();
        getlist();

    }
    private void initDrawer() {
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
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





//        TempAdapter adapter=new TempAdapter();
//        mylist.setAdapter((ListAdapter) adapter);
//
////        mylist.setOnItemClickListener(this);
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
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        estTypeListArray = new ArrayList<>();

//        AllPojos cp = new AllPojos();
//        cp.setMax_guest("Summer Fest");
//        cp.setCost_per_person("lorem ipsum dolor.....");
//        list.add(cp);
//        AllPojos cp1 = new AllPojos();
//
//        cp1.setMax_guest("Summer Fest");
//        cp1.setCost_per_person("lorem ipsum dolor.....");
//        list.add(cp1);
//        AllPojos cp2 = new AllPojos();
        empty = (TextView)findViewById(R.id.empty);
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
//        String[] listItems = mItemData.split(" ");
//        Collections.addAll(list, listItems);
//        adapter = new TempAdapter(list);
//        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        DealWithItApp.showAToast("Under Construction");
//                        Intent inten=new Intent(DealTemplatesActivity.this, UserChatActivity.class);
//                        startActivity(inten);
                    }
                })
        );

    }





    private void getlist() {
        try {
            JSONObject jsonObject = new JSONObject();
            String id = DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id, Constants.DEFAULT_STRING);
            jsonObject.accumulate(Keys.merchantId, id);


            if (DealWithItApp.isNetworkAvailable()) {
                new gettemplate().execute(jsonObject.toString());
            } else {
                DealWithItApp.showAToast("No internet Connection");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class gettemplate extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(DealTemplatesActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.getTemplate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            onDone(jsonObject);
        }
    }

    private void onDone(final JSONObject jsonObject) {
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);

                    JSONArray jArray = obj.getJSONArray(Keys.getTemplateList);


                    estTypeListArray = new ArrayList<>();
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject1 = jArray.getJSONObject(i);
                            AllPojos cp = new AllPojos();
                            cp.setTemplate_Id(jsonObject1.getString(Keys.Id));
                            cp.setTemplate_name(jsonObject1.getString(Keys.templatename));
                            cp.setTemplate_Description(jsonObject1.getString(Keys.description_template));


                            estTypeListArray.add(cp);
                        }
                    }
                    adapter = new TempAdapter(this,estTypeListArray);
                    mRecyclerView.setAdapter(adapter);
//                    adapter = new BusinessProfileListAdapter(getApplicationContext(), estTypeListArray);
//                    mRecyclerView.setAdapter(adapter);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {

                    Handler mHandler = new Handler(this.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Handler mHandler = new Handler(this.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast("Something Went Wrong.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }else{
                Handler mHandler = new Handler(this.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DealWithItApp.showAToast("Something Went Wrong. Server is not responding");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}