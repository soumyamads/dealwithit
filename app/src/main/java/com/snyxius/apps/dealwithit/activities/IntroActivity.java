package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.fragments.LoginSignupFragment;
import com.snyxius.apps.dealwithit.utils.CirclePageIndicator;

/**
 * Created by snyxius on 10/30/2015.
 */
public class IntroActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        CirclePageIndicator mIndicator  = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            switch(pos) {

                case 0:  return LoginSignupFragment.newInstance("IntroFragment, Instance 1");
                case 1:  return LoginSignupFragment.newInstance("SecondFragment, Instance 1");
                case 2:  return LoginSignupFragment.newInstance("ThirdFragment, Instance 1");
                case 3:  return LoginSignupFragment.newInstance("ThirdFragment, Instance 2");
                case 4:  return LoginSignupFragment.newInstance("ThirdFragment, Instance 3");
                default: return LoginSignupFragment.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
