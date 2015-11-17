package com.snyxius.apps.dealwithit.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.AmbienceTypeFragment;
import com.snyxius.apps.dealwithit.fragments.CategoryFragment;
import com.snyxius.apps.dealwithit.fragments.CuisineTypeFragment;
import com.snyxius.apps.dealwithit.fragments.TypeFragment;

import java.util.ArrayList;

/**
 * Created by snyxius on 10/30/2015.
 */
public class BusinessProfileActivity extends AppCompatActivity implements CategoryFragment.DataPassListener,TypeFragment.DataPassListener,
        AmbienceTypeFragment.DataPassListener,CuisineTypeFragment.DataPassListener,
AddBusinessProfileBasicFragment.DetailStroke,AddBusinessProfileDetailFragment.DealStroke,AddBusinessProfileDetailFragment.PassData{

    ImageView stepViewImage2,stepViewImage3;
    TextView stepViewText2,stepViewText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        initializeView();

    }




    private void initializeView(){
        stepViewText2= (TextView) findViewById(R.id.stepView_text2);
        stepViewImage2= (ImageView) findViewById(R.id.stepView_image2);
        stepViewText3= (TextView) findViewById(R.id.stepView_text3);
        stepViewImage3= (ImageView) findViewById(R.id.stepView_image3);
        DealWithItApp.saveToPreferences(getApplicationContext(), Keys.establishmentDetail, Constants.DEFAULT_STRING);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frmaecontainer,new AddBusinessProfileBasicFragment(),Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT)
                .commit();
    }


    @Override
    public void setDetailStoke() {
        stepViewText2.setTypeface(null, Typeface.BOLD);
        stepViewText2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        stepViewImage2.setImageResource(R.drawable.rounded_fill_indicator);
    }


    @Override
    public void setDealStoke() {
        stepViewText3.setTypeface(null, Typeface.BOLD);
        stepViewText3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        stepViewImage3.setImageResource(R.drawable.rounded_fill_indicator);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        android.support.v4.app.Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);

        if(fragment instanceof AddBusinessProfileBasicFragment && fragment1 instanceof AddBusinessProfileDetailFragment){
            stepViewText3.setTypeface(null, Typeface.NORMAL);
            stepViewText3.setTextColor(getResources().getColor(R.color.grey));
            stepViewImage3.setImageResource(R.drawable.rounded_stroke_indicator);
        }else if(fragment instanceof AddBusinessProfileBasicFragment){
            stepViewText2.setTypeface(null, Typeface.NORMAL);
            stepViewText2.setTextColor(getResources().getColor(R.color.grey));
            stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
        }


    }

    @Override
    public void passCategoryData(String data) {
        try {
            AddBusinessProfileBasicFragment f = (AddBusinessProfileBasicFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);
            f.changeCategoryText(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void passTypeData(String data,ArrayList<String> arrayList) {
        try {
            AddBusinessProfileDetailFragment f = (AddBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
            f.changeTypeText(data);
        }catch(Exception e){

        }
    }


    @Override
    public void passAmbienceData(String data, ArrayList<String> array) {
        try {
            AddBusinessProfileDetailFragment f = (AddBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
            f.changeAmbienceText(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void passCuisineData(String data, ArrayList<String> array) {
        try {
            AddBusinessProfileDetailFragment f = (AddBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
            f.changeCuisineText(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setCuisineData(String string) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                    .add(R.id.container, new CuisineTypeFragment().newInstance(string), Constants.CUISINE_FRAGMENT)
                    .addToBackStack(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                    .commit();
//            CuisineTypeFragment f = (CuisineTypeFragment) getSupportFragmentManager().findFragmentByTag(Constants.CUISINE_FRAGMENT);
//            f.getData(string);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
