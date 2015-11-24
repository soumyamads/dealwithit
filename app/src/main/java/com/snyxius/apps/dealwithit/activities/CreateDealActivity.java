package com.snyxius.apps.dealwithit.activities;

import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.ImageView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileIncomingDealFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepOneFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepThreeFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepTwoFragment;
import com.snyxius.apps.dealwithit.fragments.GetAllBusinessProfileFragment;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 10/30/2015.
 */
public class CreateDealActivity extends AppCompatActivity implements CreateDealStepOneFragment.StepOneStroke,CreateDealStepTwoFragment.StepTwoStroke,
GetAllBusinessProfileFragment.DataPassListener,CreateDealStepOneFragment.PassData{

    ImageView stepViewImage2,stepViewImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);
        initializeView();
    }


    private void initializeView(){
        stepViewImage2 = (ImageView) findViewById(R.id.stepView_image2);
        stepViewImage3 = (ImageView) findViewById(R.id.stepView_image3);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_createdeal,new CreateDealStepOneFragment(),Constants.CREATE_STEP_ONE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setStepThreeStoke() {
        stepViewImage3.setImageResource(R.drawable.rounded_fill_indicator);
    }

    @Override
    public void sendStepTwoData(JSONObject jsonObject) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_createdeal, new CreateDealStepThreeFragment().newInstance(jsonObject,CreateDealActivity.this), Constants.CREATE_STEP_TWO_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setStepTwoStoke() {
        stepViewImage2.setImageResource(R.drawable.rounded_fill_indicator);
    }

    @Override
    public void sendStepOneData(JSONObject jsonObject) {
        Log.d("String",jsonObject.toString());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_createdeal, new CreateDealStepTwoFragment().newInstance(jsonObject), Constants.CREATE_STEP_TWO_FRAGMENT)
                .addToBackStack(Constants.CREATE_STEP_ONE_FRAGMENT)
                .commit();
    }

    @Override
    public void setBusinessData(String string) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .add(R.id.container, new GetAllBusinessProfileFragment().newInstance(string), Constants.GET_ALL_BUSINESS_PROFILE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void passBusinessProfileData(String data, ArrayList<String> array) {
        try {
            //Log.d("String",data+""+array.toString());
            CreateDealStepOneFragment f = (CreateDealStepOneFragment) getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_ONE_FRAGMENT);
            f.changeBusinessProfileText(data, array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.support.v4.app.Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_TWO_FRAGMENT);
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_ONE_FRAGMENT);
        if(fragment instanceof AddBusinessProfileBasicFragment && fragment1 instanceof AddBusinessProfileDetailFragment){
            stepViewImage3.setImageResource(R.drawable.rounded_stroke_indicator);
        }else if(fragment instanceof AddBusinessProfileBasicFragment){

            stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
        }


    }
}
