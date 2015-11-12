package com.snyxius.apps.dealwithit.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.snyxius.apps.dealwithit.R;

/**
 * Created by snyxius on 11/12/2015.
 */
public class MerchantProfileActivity extends AppCompatActivity {

    AppBarLayout mAppBar;
    private static final float THRESHOLD_PERCENTAGE = 0.2F;
    ImageView proImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_profile_layout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        proImage=(ImageView)findViewById(R.id.pro_image);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Nihas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAppBar = (AppBarLayout) findViewById(R.id.appbar);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float progressPercentage = (float) (Math.abs(verticalOffset)/maxScroll);

                if (progressPercentage >= THRESHOLD_PERCENTAGE) {
                    //start an alpha animation on your ImageView here (i.e. fade out)
                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    proImage.startAnimation(rotate);
                } else {
                    //Add an opposite animation here (i.e. it fades back in again)
                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    proImage.startAnimation(rotate);
                }
            }
        });
    }
}
