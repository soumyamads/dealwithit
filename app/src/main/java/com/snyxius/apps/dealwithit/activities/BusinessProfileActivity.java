package com.snyxius.apps.dealwithit.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileBasic;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDeal;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetail;
import com.snyxius.apps.dealwithit.fragments.LoginFragment;
import com.snyxius.apps.dealwithit.fragments.SignupFragment;
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
    StepsView mStepsView;
    TextView mLabel,stepViewText1,stepViewText2,stepViewText3;
    private final String[] labels = {"BASIC", "DETAILS", "INCOMING DEAL"};
//    TextView indicatorText1,indicatorText2,indicatorText3;
//    ImageView indicator1,indicator2,indicator3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        initializeView();
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddBusinessProfileBasic(), "Signup");
        adapter.addFragment(new AddBusinessProfileDetail(), "Login");
        adapter.addFragment(new AddBusinessProfileDeal(), "Login");
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
        stepViewText1.setTypeface(null, Typeface.BOLD);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                setSelectedPage(position);
                if(position==0){
                    stepViewText1.setTypeface(null, Typeface.BOLD);
                }else if(position==1){
                    stepViewText1.setTypeface(null, Typeface.BOLD);
                    stepViewText2.setTypeface(null, Typeface.BOLD);
                }else{
                    stepViewText1.setTypeface(null, Typeface.BOLD);
                    stepViewText2.setTypeface(null, Typeface.BOLD);
                    stepViewText3.setTypeface(null, Typeface.BOLD);

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

            mLabel.setText(mFragmentTitleList.get(position));

            mStepsView
                    .setLabels(labels)
                    .setBarColorIndicator(
                            getResources().getColor(R.color.grey))
                    .setProgressColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setLabelColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .drawView();

            return mFragmentList.get(position);
        }

//        @Override
//        public android.support.v4.app.Fragment getItem(int position) {
//          //  return;
//        }

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
        mStepsView = (StepsView) findViewById(R.id.stepsView);
        mLabel = (TextView) findViewById(R.id.label);
        stepViewText1= (TextView) findViewById(R.id.stepsView_text1);
        stepViewText2= (TextView) findViewById(R.id.stepsView_text2);
        stepViewText3= (TextView) findViewById(R.id.stepsView_text3);
    }

    private void setSelectedPage(int pos){
        if(pos==0){
            mLabel.setText(labels[pos]);

            mStepsView.setCompletedPosition(pos % labels.length)
                    .setLabels(labels)
                    .setBarColorIndicator(
                            getResources().getColor(R.color.grey))
                    .setProgressColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setLabelColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setCompletedPosition(pos)
                    .drawView();
        }else if(pos==1){
            mLabel.setText(labels[pos]);

            mStepsView.setCompletedPosition(pos % labels.length)
                    .setLabels(labels)
                    .setBarColorIndicator(
                            getResources().getColor(R.color.grey))
                    .setProgressColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setLabelColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setCompletedPosition(pos)
                    .drawView();
        }else  if(pos==2){
            mLabel.setText(labels[pos]);

            mStepsView.setCompletedPosition(pos % labels.length)
                    .setLabels(labels)
                    .setBarColorIndicator(
                            getResources().getColor(R.color.grey))
                    .setProgressColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setLabelColorIndicator(getResources().getColor(R.color.colorPrimaryDark))
                    .setCompletedPosition(pos)
                    .drawView();
        }
    }

    public void selectPage(int page) {
        viewPager.setCurrentItem(page);
        mStepsView.setCompletedPosition(page).drawView();
    }
}
