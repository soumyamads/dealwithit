package com.snyxius.apps.dealwithit.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.TempAdapter;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.utils.DividerItemDecoration;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;
import com.snyxius.apps.dealwithit.utils.VerticalSpaceItemDecoration;

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
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        estTypeListArray = new ArrayList<>();

        AllPojos cp = new AllPojos();
        cp.setMax_guest("Summer Fest");
        cp.setCost_per_person("lorem ipsum dolor.....");
        list.add(cp);
        AllPojos cp1 = new AllPojos();

        cp1.setMax_guest("Summer Fest");
        cp1.setCost_per_person("lorem ipsum dolor.....");
        list.add(cp1);
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
        adapter = new TempAdapter(list);
        mRecyclerView.setAdapter(adapter);
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
        initDrawer();

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
    private void initRecyclerView(View view) {


    }


}