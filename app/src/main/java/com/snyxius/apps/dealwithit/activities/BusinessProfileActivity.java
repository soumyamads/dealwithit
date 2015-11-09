package com.snyxius.apps.dealwithit.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.AmbienceTypeFragment;
import com.snyxius.apps.dealwithit.fragments.CuisineTypeFragment;
import com.snyxius.apps.dealwithit.fragments.EstablishmentTypeFragment;

import java.util.ArrayList;

/**
 * Created by snyxius on 10/30/2015.
 */
public class BusinessProfileActivity extends AppCompatActivity implements EstablishmentTypeFragment.DataPassListener,
        CuisineTypeFragment.DataPassListener,AmbienceTypeFragment.DataPassListener,
AddBusinessProfileBasicFragment.DetailStroke,AddBusinessProfileDetailFragment.BasicStroke{

    ImageView stepViewImage2;
    TextView stepViewText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        initializeView();

    }


    @Override
    public void passEstablishData(String data,ArrayList<String> arrayList) {
        try {
            AddBusinessProfileBasicFragment f = (AddBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);
            f.changeEstablishmentText(data, arrayList);
        }catch(Exception e){

           }
        }


    private void initializeView(){
        stepViewText2= (TextView) findViewById(R.id.stepView_text2);
        stepViewImage2= (ImageView) findViewById(R.id.stepView_image2);
        DealWithItApp.saveToPreferences(getApplicationContext(), Keys.establishmentDetail,Constants.DEFAULT_STRING);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frmaecontainer,new AddBusinessProfileBasicFragment(),Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT)
                .commit();
    }


    @Override
    public void setDetailStoke() {
        stepViewText2.setTypeface(null, Typeface.BOLD);
        stepViewImage2.setImageResource(R.drawable.rounded_fill_indicator);
    }


    @Override
    public void setBasicStoke() {
        stepViewText2.setTypeface(null, Typeface.NORMAL);
        stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
    }

    @Override
    public void passAmbienceData(String data, ArrayList<String> array) {
        try {
            AddBusinessProfileBasicFragment f = (AddBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);
            f.changeAmbienceText(data, array);

        }catch(Exception e){

        }

    }

    @Override
    public void passCuisineData(String data, ArrayList<String> array) {
        try {
            AddBusinessProfileBasicFragment f = (AddBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);
            f.changeCuisineText(data, array);

            }catch(Exception e){

        }

    }
}
