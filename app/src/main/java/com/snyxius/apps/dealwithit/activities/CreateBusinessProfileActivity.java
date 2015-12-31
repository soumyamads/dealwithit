package com.snyxius.apps.dealwithit.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment.DetailStroke;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileIncomingDealFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.AmbienceTypeFragment;
import com.snyxius.apps.dealwithit.fragments.BusinessProfileIncomingDealDialogFragment;
import com.snyxius.apps.dealwithit.fragments.CategoryFragment;
import com.snyxius.apps.dealwithit.fragments.CuisineTypeFragment;
import com.snyxius.apps.dealwithit.fragments.TypeFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by snyxius on 10/30/2015.
 */
public class CreateBusinessProfileActivity extends AppCompatActivity implements CategoryFragment.DataPassListener,TypeFragment.DataPassListener,
        AmbienceTypeFragment.DataPassListener,CuisineTypeFragment.DataPassListener,
DetailStroke,AddBusinessProfileDetailFragment.DealStroke,AddBusinessProfileDetailFragment.PassData, View.OnClickListener,BusinessProfileIncomingDealDialogFragment.IncomingDeals{

    ImageView stepViewImage2,stepViewImage3;
    TextView stepViewText2,stepViewText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_profile);
        initializeView();
    }


    private void initializeView(){
        stepViewText2= (TextView) findViewById(R.id.stepView_text2);
        stepViewImage2= (ImageView) findViewById(R.id.stepView_image2);
        stepViewText3= (TextView) findViewById(R.id.stepView_text3);
        stepViewImage3= (ImageView) findViewById(R.id.stepView_image3);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.wrong).setOnClickListener(this);
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
    public void sendCategoryData(String string, JSONObject jsonObject) {
        DealWithItApp.saveToPreferences(getApplicationContext(), Keys.category, string);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frmaecontainer,new AddBusinessProfileDetailFragment().newInstance(jsonObject),Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                .addToBackStack(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT)
                .commit();
    }


    @Override
    public void setDealStoke() {
        stepViewText3.setTypeface(null, Typeface.BOLD);
        stepViewText3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        stepViewImage3.setImageResource(R.drawable.rounded_fill_indicator);
    }

    @Override
    public void sendDetailsCategoryData(JSONObject jsonObject) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frmaecontainer,new AddBusinessProfileIncomingDealFragment().newInstance(jsonObject),Constants.ADDBUSINESSPROFILEDEAL_FRAGMENT)
                .addToBackStack(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                .commit();
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
            f.changeTypeText(data, arrayList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void passAmbienceData(String data, ArrayList<String> array) {
        try {
            AddBusinessProfileDetailFragment f = (AddBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
            f.changeAmbienceText(data, array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void passCuisineData(String data, ArrayList<String> array) {
        try {
            AddBusinessProfileDetailFragment f = (AddBusinessProfileDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
            f.changeCuisineText(data, array);
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setAmbienceData(String string) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                    .add(R.id.container, new AmbienceTypeFragment().newInstance(string), Constants.AMBINENCE_FRAGMENT)
                    .addToBackStack(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                    .commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setTypeData(String string) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                .add(R.id.container, new TypeFragment().newInstance(string), Constants.TYPE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right:
             Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frmaecontainer);
                if(fragment instanceof AddBusinessProfileBasicFragment){
                    Log.d("fragment","1 instance");
                    ((AddBusinessProfileBasicFragment) fragment).validate();
                }else if(fragment instanceof AddBusinessProfileDetailFragment){
                    ((AddBusinessProfileDetailFragment) fragment).validate();
                    Log.d("fragment", "2 instance");
                }else if(fragment instanceof AddBusinessProfileIncomingDealFragment){
                    ((AddBusinessProfileIncomingDealFragment) fragment).validate();
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frmaecontainer);
        if(fragment instanceof AddBusinessProfileBasicFragment){
            stepViewText2.setTypeface(null, Typeface.NORMAL);
            stepViewText2.setTextColor(getResources().getColor(R.color.grey));
            stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
        }else if(fragment instanceof AddBusinessProfileDetailFragment){
            stepViewText3.setTypeface(null, Typeface.NORMAL);
           stepViewText3.setTextColor(getResources().getColor(R.color.grey));
            stepViewImage3.setImageResource(R.drawable.rounded_stroke_indicator);
        }else if(fragment instanceof AddBusinessProfileIncomingDealFragment){
            Log.d("fragment", "3 instance");
        }else{
            Log.d("fragment","no instance");
        }


//        android.support.v4.app.Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
//        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);
//        if(fragment instanceof AddBusinessProfileBasicFragment && fragment1 instanceof AddBusinessProfileDetailFragment ){
//            stepViewText3.setTypeface(null, Typeface.NORMAL);
//            stepViewText3.setTextColor(getResources().getColor(R.color.grey));
//            stepViewImage3.setImageResource(R.drawable.rounded_stroke_indicator);
//        }else if(fragment instanceof AddBusinessProfileBasicFragment){
//            stepViewText2.setTypeface(null, Typeface.NORMAL);
//            stepViewText2.setTextColor(getResources().getColor(R.color.grey));
//            stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
//        }


    }

    @Override
    public void sendDealsCategoryData(ArrayList<AllPojos> data) {
        AddBusinessProfileIncomingDealFragment f = (AddBusinessProfileIncomingDealFragment) getSupportFragmentManager().findFragmentByTag(Constants.ADDBUSINESSPROFILEDEAL_FRAGMENT);
       f.addItem(data);
    }



}
