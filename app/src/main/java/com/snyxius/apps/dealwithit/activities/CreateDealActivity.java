package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.*;
import com.snyxius.apps.dealwithit.fragments.TermsandConditions;


import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by snyxius on 10/30/2015.
 */
public class CreateDealActivity extends AppCompatActivity implements CreateDealStepOneFragment.StepOneStroke,CreateDealStepTwoFragment.StepTwoStroke,
GetAllBusinessProfileFragment.DataPassListener,CreateDealStepOneFragment.PassData, View.OnClickListener,CreateDealStepTwoFragment.PassTandcData,TermsandConditions.TandcPassListener,TempFragment.sendTemplate,TempFragment.sendTemplateTandC{

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
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.wrong).setOnClickListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_createdeal,new CreateDealStepOneFragment(),Constants.CREATE_STEP_ONE_FRAGMENT)
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right:
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_createdeal);
                if(fragment instanceof CreateDealStepOneFragment){
                    Log.d("fragment","1 instance");
                    ((CreateDealStepOneFragment) fragment).validate();
                }else if(fragment instanceof CreateDealStepTwoFragment){
                    ((CreateDealStepTwoFragment) fragment).validate();
                    Log.d("fragment", "2 instance");
                }else if(fragment instanceof CreateDealStepThreeFragment){
                    ((CreateDealStepThreeFragment) fragment).validate();
                    Log.d("fragment", "3 instance");
                }else{
                    Log.d("fragment","no instance");
                }
                break;

            case R.id.wrong:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_createdeal);
        if(fragment instanceof CreateDealStepOneFragment){
            stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
        }else if(fragment instanceof CreateDealStepTwoFragment){
            stepViewImage3.setImageResource(R.drawable.rounded_stroke_indicator);
        }else if(fragment instanceof CreateDealStepThreeFragment){
            Log.d("fragment", "3 instance");
        }else{
            Log.d("fragment","no instance");
        }

//        android.support.v4.app.Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_TWO_FRAGMENT);
//        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_ONE_FRAGMENT);
//        if(fragment instanceof AddBusinessProfileBasicFragment && fragment1 instanceof AddBusinessProfileDetailFragment){
//            stepViewImage3.setImageResource(R.drawable.rounded_stroke_indicator);
//        }else if(fragment instanceof AddBusinessProfileBasicFragment){
//
//            stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
//        }


    }

    @Override
    public void passBusinessProfileData(String data, ArrayList<String> arrayBusinessName, ArrayList<String> arrayBusinessIds) {
        try {
            //Log.d("String",data+""+array.toString());
            CreateDealStepOneFragment f = (CreateDealStepOneFragment) getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_ONE_FRAGMENT);
            f.changeBusinessProfileText(data, arrayBusinessName, arrayBusinessIds);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void setTandcData(String string) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .add(R.id.container, new TermsandConditions().newInstance(string), Constants.TERMSANDCONDITION_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void passTandCData(String data) {
        try {
            //Log.d("String",data+""+array.toString());
            CreateDealStepTwoFragment f = (CreateDealStepTwoFragment) getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_TWO_FRAGMENT);
            f.changeTandCText(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getTemplateSelected(String content) {
        try {
            //Log.d("String",data+""+array.toString());
//            TermsandConditions f1 = (TermsandConditions) getSupportFragmentManager().findFragmentByTag(Constants.TERMSANDCONDITION_FRAGMENT);
//            f1.getTemplateSelected(content);

            CreateDealStepOneFragment f = (CreateDealStepOneFragment) getSupportFragmentManager().findFragmentByTag(Constants.CREATE_STEP_ONE_FRAGMENT);
            f.getTemplateSelected(content);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void getTemplateTandCSelected(String content) {
        TermsandConditions f = (TermsandConditions) getSupportFragmentManager().findFragmentByTag(Constants.TERMSANDCONDITION_FRAGMENT);
        f.getTermsTemplateSelected(content);
    }


//    @Override
//    public void getTermsTemplateSelected(String content) {
//        try {
//            //Log.d("String",data+""+array.toString());
////            TermsandConditions f1 = (TermsandConditions) getSupportFragmentManager().findFragmentByTag(Constants.TERMSANDCONDITION_FRAGMENT);
////            f1.getTemplateSelected(content);
//
//            TermsandConditions f = (TermsandConditions) getSupportFragmentManager().findFragmentByTag(Constants.TERMSANDCONDITION_FRAGMENT);
//            f.getTermsTemplateSelected(content);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
