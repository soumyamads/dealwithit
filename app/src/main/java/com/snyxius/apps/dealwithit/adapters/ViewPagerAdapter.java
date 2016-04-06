package com.snyxius.apps.dealwithit.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMAN on 04-08-2015.
 */
 public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();
    private List<Integer> mFragmentIcons = new ArrayList<>();
    private List<Integer> mFragmentCount = new ArrayList<>();

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    public void addFragment(Fragment fragment, String title, int drawable) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mFragmentIcons.add(drawable);
    }
    public void addFragment(Fragment fragment, String title, int drawable,int count) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mFragmentIcons.add(drawable);
        mFragmentCount.add(count);
    }

    public void addFragment(Fragment fragment, int drawable) {
        mFragments.add(fragment);
        mFragmentIcons.add(drawable);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//
//        return mFragmentTitles.get(position);
//    }

    public View getTabView(int position) {
        View tab = LayoutInflater.from(mContext).inflate(R.layout.tabbar_view, null);
        if(mFragmentCount.size() != 0){
            TextView messages_count = (TextView) tab.findViewById(R.id.messages_count);
            if(mFragmentCount.get(position) == Constants.DEFAULT_INT){
                tab.findViewById(R.id.count_layout).setVisibility(View.GONE);
                messages_count.setVisibility(View.GONE);
            }else {
                tab.findViewById(R.id.count_layout).setVisibility(View.VISIBLE);
                messages_count.setVisibility(View.VISIBLE);
                messages_count.setText(String.valueOf(mFragmentCount.get(position)));
            }
        }

        if(mFragmentTitles.size() != 0) {
            TextView tabText = (TextView) tab.findViewById(R.id.tabText);
            tabText.setVisibility(View.VISIBLE);
            tabText.setText(mFragmentTitles.get(position));
        }

        ImageView tabImage = (ImageView) tab.findViewById(R.id.tabImage);
        tabImage.setBackgroundResource(mFragmentIcons.get(position));
        if (position == 0) {
            tab.setSelected(true);
        }
        return tab;
    }
}