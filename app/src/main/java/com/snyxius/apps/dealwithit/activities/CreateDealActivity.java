package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepOneFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepThreeFragment;
import com.snyxius.apps.dealwithit.fragments.CreateDealStepTwoFragment;
import com.snyxius.apps.dealwithit.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 10/30/2015.
 */
public class CreateDealActivity extends AppCompatActivity implements CreateDealStepOneFragment.StepTwoStroke,CreateDealStepTwoFragment.StepThreeStroke,CreateDealStepThreeFragment.StepOneStroke{
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    ImageView stepViewImage2,stepViewImage3;
    private final String[] labels = {Constants.BASIC, Constants.DETAILS};

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
                .add(R.id.container_createdeal,new CreateDealStepOneFragment(),Constants.CREATE_STEP_THREE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setStepThreeStoke() {
        stepViewImage3.setImageResource(R.drawable.rounded_fill_indicator);

    }

    @Override
    public void setStepTwoStoke() {
        stepViewImage2.setImageResource(R.drawable.rounded_fill_indicator);

    }
}
