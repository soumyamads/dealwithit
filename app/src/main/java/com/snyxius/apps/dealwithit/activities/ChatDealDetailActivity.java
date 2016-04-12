package com.snyxius.apps.dealwithit.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snyxius on 7/12/15.
 */
public class ChatDealDetailActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener{

    boolean isShow=false;
    TextView title,desc,userId,timing,cost;
    CircleImageView proPic;
    ImageView editProfile,rightArrow,openClose;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.1f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.5f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    Button editprofile_button;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
//    private TextView mTitle;
private LinearLayout mTitleContainer;
//    FrameLayout mFrameParallax;
    ImageView mImageparallax;
    RelativeLayout mImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_deal_detail_view);
        initialise();
        initDrawer();

//        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
//        initParallaxValues();
    }


    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mImageparallax.getLayoutParams();

//        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
//                (CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

//        petDetailsLp.setParallaxMultiplier(0.1f);
//        petBackgroundLp.setParallaxMultiplier(0.3f);
//
//        mImageparallax.setLayoutParams(petDetailsLp);
//        mFrameParallax.setLayoutParams(petBackgroundLp);

//        pDialog=new ProgressDialog(this);
    }




    private void initDrawer() {
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_drawer,new DrawerFragment().newInstance(drawerLayout), Constants.DRAWER_FRAGMENT)
                .commit();
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerToggle.syncState();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerToggle.syncState();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void initialise(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        mTitle          = (TextView) findViewById(R.id.main_textview_title);
//        mFrameParallax=(FrameLayout)findViewById(R.id.frame_container);
        mTitleContainer = (LinearLayout) findViewById(R.id.linear_container);
        mImageLayout=(RelativeLayout)findViewById(R.id.imageLayout);
        editprofile_button=(Button)findViewById(R.id.edit_profile_button);
        findViewById(R.id.edit_profile_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit_profile_button:
                Intent i=new Intent(this,EditDealOffer.class);
                startActivity(i);
                finish();

                break;



        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatmain, menu);
        return true;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        System.out.println("MAX SCROLL: " + maxScroll);
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
//        handleToolbarTitleVisibility(percentage);
    }

    /*private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
//                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
//                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }


    }*/

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                scaleView(mImageLayout, View.INVISIBLE);
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                scaleView(mImageLayout, View.VISIBLE);
//                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {

        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public void scaleView(View v, int visibility) {
        AlphaAnimation anim = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0,1) : new AlphaAnimation(1,0);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(700);
        anim.setInterpolator(new OvershootInterpolator());
//        fab.startAnimation(anim);
//        anim.setFillAfter(true); // Needed to keep the result of the animation
        v.startAnimation(anim);
    }
}
