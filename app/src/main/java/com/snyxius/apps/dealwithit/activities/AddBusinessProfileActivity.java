package com.snyxius.apps.dealwithit.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.DrawerAdapter;
import com.snyxius.apps.dealwithit.adapters.SectionedRecyclerViewAdapter;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nihas on 02-11-2015.
 */
public class AddBusinessProfileActivity extends AppCompatActivity{

    Button addBusinessProfile;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_start_deal_layout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_drawer,new DrawerFragment(), Constants.DRAWER_FRAGMENT)
                .commit();
        initDrawer();







        addBusinessProfile=(Button)findViewById(R.id.add_business_profile);
        addBusinessProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten=new Intent(AddBusinessProfileActivity.this,BusinessProfileActivity.class);
                startActivity(inten);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.business_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
