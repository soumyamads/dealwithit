package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.LoginFragment;
import com.snyxius.apps.dealwithit.fragments.LoginSignupFragment;
import com.snyxius.apps.dealwithit.fragments.SignupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 10/30/2015.
 */
public class LoginSignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

private ViewPagerAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        int position = getIntent().getIntExtra(Keys.position,Constants.DEFAULT_INT);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.wrong).setOnClickListener(this);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignupFragment(), Constants.SIGNUP_FRAGMENT);
        adapter.addFragment(new LoginFragment(), Constants.LOGIN_FRAGMENT);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right:
                Fragment fragment = adapter.getItem(viewPager.getCurrentItem());
                if (fragment instanceof SignupFragment){
                    ((SignupFragment) fragment).validate();
                }else {
                    ((LoginFragment) fragment).validate();
                }
                        break;

            case R.id.wrong:
                onBackPressed();
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private int currentPage;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
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

        public  int getCurrentPage() {
            return currentPage;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            currentPage = position;
            return mFragmentTitleList.get(position);
        }
    }




}
