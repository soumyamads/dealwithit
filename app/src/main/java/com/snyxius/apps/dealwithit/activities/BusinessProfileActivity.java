package com.snyxius.apps.dealwithit.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasicFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDealFragment;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.utils.CustomViewPager;
import com.snyxius.apps.dealwithit.utils.StepsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 10/30/2015.
 */
public class BusinessProfileActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
//    StepsView mStepsView;
    ImageView stepViewImage2;
    TextView mLabel,stepViewText2;//,stepViewText2,stepViewText3;
    private final String[] labels = {Constants.BASIC, Constants.DETAILS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        initializeView();
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddBusinessProfileBasicFragment(), Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT);
        adapter.addFragment(new AddBusinessProfileDetailFragment(), Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT);
        adapter.addFragment(new AddBusinessProfileDealFragment(), Constants.ADDBUSINESSPROFILEDEAL_FRAGMENT);
        viewPager.setAdapter(adapter);
//        stepViewText1.setTypeface(null, Typeface.BOLD);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    stepViewText2.setTypeface(null, Typeface.BOLD);
                    stepViewImage2.setImageResource(R.drawable.rounded_fill_indicator);
                } else {
                    stepViewText2.setTypeface(null, Typeface.NORMAL);
                    stepViewImage2.setImageResource(R.drawable.rounded_stroke_indicator);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {

//            mLabel.setText(mFragmentTitleList.get(position));
//
//            mStepsView
//                    .setLabels(labels)
//                    .setBarColorIndicator(
//                            getResources().getColor(R.color.grey))
//                    .setProgressColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
//                    .setLabelColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
//                    .drawView();

            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void initializeView(){
//        mStepsView = (StepsView) findViewById(R.id.stepsView);
//        mLabel = (TextView) findViewById(R.id.label);
        stepViewText2= (TextView) findViewById(R.id.stepView_text2);
        stepViewImage2= (ImageView) findViewById(R.id.stepView_image2);
//        stepViewText2= (TextView) findViewById(R.id.stepsView_text2);
//        stepViewText3= (TextView) findViewById(R.id.stepsView_text3);
    }

    private void setSelectedPage(int pos){
        if(pos==0){


        }else if(pos==1){


        }else  if(pos==2){


        }
    }

    public void selectPage(int page) {
        viewPager.setCurrentItem(page);
//        mStepsView.setCompletedPosition(page).drawView();
    }
}