package com.snyxius.apps.dealwithit.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;

/**
 * Created by snyxius on 4/4/16.
 */
public class CreateTemplateActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_template);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        create=(Button)findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateTemplateActivity.this,DealTemplatesActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }


}