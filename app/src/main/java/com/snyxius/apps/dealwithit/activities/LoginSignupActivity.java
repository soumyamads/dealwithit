package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.LoginFragment;
import com.snyxius.apps.dealwithit.fragments.SignupFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 10/30/2015.
 */
public class LoginSignupActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Bundle getExtraIntent;
    int page_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        getExtraIntent=getIntent().getExtras();
        page_go=getExtraIntent.getInt("page");
        viewPager.setCurrentItem(page_go);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignupFragment(), Constants.SIGNUP_FRAGMENT);
        adapter.addFragment(new LoginFragment(), Constants.LOGIN_FRAGMENT);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
