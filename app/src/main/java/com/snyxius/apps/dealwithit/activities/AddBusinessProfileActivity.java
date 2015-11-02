package com.snyxius.apps.dealwithit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.snyxius.apps.dealwithit.R;

/**
 * Created by Nihas on 02-11-2015.
 */
public class AddBusinessProfileActivity extends AppCompatActivity{

    Button addBusinessProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_start_deal_layout);

        addBusinessProfile=(Button)findViewById(R.id.add_business_profile);
        addBusinessProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten=new Intent(AddBusinessProfileActivity.this,BusinessProfileActivity.class);
                startActivity(inten);
                finish();
            }
        });


    }
}
