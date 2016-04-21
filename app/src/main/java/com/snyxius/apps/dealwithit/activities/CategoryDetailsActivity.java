package com.snyxius.apps.dealwithit.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.ViewPagerAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.CreatedDealDialogFragment;
import com.snyxius.apps.dealwithit.fragments.DealsDetailsFragment;
import com.snyxius.apps.dealwithit.fragments.MapDealsFragment;
import com.snyxius.apps.dealwithit.fragments.ShareDealsFragment;
import com.snyxius.apps.dealwithit.fragments.TermsAndConditionsUser;
import com.snyxius.apps.dealwithit.fragments.VenueDetailsFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snyxius on 11/1/16.
 */
public class CategoryDetailsActivity  extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener,View.OnClickListener {

    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    RelativeLayout imageLayout;
    ImageView mImageparallax, book_button;
    FrameLayout mFrameParallax;
    AppBarLayout mAppBarLayout;
    ViewPager mViewpager;
    CollapsingToolbarLayout collapsingToolbar;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

//    @Optional
//    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    FrameLayout mDrawerList_Left;
    Dialog dialog;
    private boolean isHideToolbarView = false;
    FragmentManager fm = getSupportFragmentManager();
    TextView title, address, description;
    String id, bussinessname, location;

//    @Optional
//    @InjectView(R.id.pBar)
    JSONObject jsonObject;
    ProgressBar pBar;
    Button savelaunch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        Bundle bundle=getIntent().getExtras();
        //get the values out by key
        String name=bundle.getString("Deal");
        System.out.println("mmmmm"+name);
        try {
            jsonObject=new JSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ButterKnife.inject(this);
//        collapsingToolbar=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
//        mAppBarLayout   = (AppBarLayout) findViewById(R.id.appbar);
//        mAppBarLayout.addOnOffsetChangedListener(this);
//        imageLayout=(RelativeLayout)findViewById(R.id.imageLayout);
//        pBar.setVisibility(View.VISIBLE);
//        drawerLayout.setVisibility(View.INVISIBLE);
        mImageparallax = (ImageView) findViewById(R.id.backdrop);
//        mFrameParallax  = (FrameLayout) findViewById(R.id.main_framelayout_title);
        title = (TextView) findViewById(R.id.title);
        address = (TextView) findViewById(R.id.address);
        description = (TextView) findViewById(R.id.description);
        savelaunch=(Button)findViewById(R.id.savelaunch_button);
        savelaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFrag = CreatedDealDialogFragment.newInstance();
                dialogFrag.setCancelable(false);
                dialogFrag.show(getSupportFragmentManager().beginTransaction(), Constants.BUSINESS_PROFILE_CREATED_DIALOG);
            }
        });
        book_button = (ImageView) findViewById(R.id.book_button);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                DealsDetailsFragment.startBookFragmnt();
//                Book_button_fragment dialogFragment = new Book_button_fragment();
//                dialogFragment.setArguments(DealsDetailsFragment.startBookFragmnt());
//                dialogFragment.show(fm, "Sample Fragment");

//                Dialog();
            }
        });
        findViewById(R.id.back).setOnClickListener(this);
//
//        collapsingToolbar.setTitle("");
//
//        initParallaxValues();
//        Bundle bundle = getIntent().getExtras();
//        id = bundle.getString("dealid");
//        bussinessname = bundle.getString("bussinessname");
//        location = bundle.getString("location");

        DealWithItApp.saveToPreferences(getApplicationContext(), Keys.dealId, id);
//        DealWithItApp.showAToast(id+" ");

        setupToolbar();
        initializeTabViewPager();
        initialize();
    }


    private void initializeTabViewPager() {
        mViewpager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);


        setupViewPager(mViewpager);

        setupTabLayout(tabLayout);
    }

    public void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());

        viewPagerAdapter.addFragment(new DealsDetailsFragment().newInstance(jsonObject), R.drawable.ic_tabbar_deals_details);

        viewPagerAdapter.addFragment(new VenueDetailsFragment().newInstance(jsonObject), R.drawable.ic_tabbar_venue_details);

        viewPagerAdapter.addFragment(new MapDealsFragment(), R.drawable.ic_tabbar_map_details);

        viewPagerAdapter.addFragment(new ShareDealsFragment(), R.drawable.ic_tabbar_deals_share);

        viewPagerAdapter.addFragment(new TermsAndConditionsUser().newInstance(jsonObject), R.drawable.ic_tabbar_deals_terms);


        viewPager.setAdapter(viewPagerAdapter);
    }

    public void Dialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.book_button_activity);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();//dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
//                window.setAttributes(lp);
        lp.dimAmount = 0.50f;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        lp.gravity = Gravity.BOTTOM;
        lp.x = -100;   //x position
        lp.y = -100;   //y position
        dialog.show();

    }

    public void setupTabLayout(TabLayout tabLayout) {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewpager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(viewPagerAdapter.getTabView(i));
        }
        tabLayout.requestFocus();
    }


    private void setupToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        mDrawerList_Left=(FrameLayout)findViewById(R.id.drawer_list_left);
//
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View drawerView) {
//                if(drawerView.equals(mDrawerList_Left)) {
//                    getSupportActionBar().setTitle(getTitle());
//                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//                    actionBarDrawerToggle.syncState();
//                }
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                //  if(drawerView.equals(mDrawerList_Right)) {
//                getSupportActionBar().setTitle(getString(R.string.app_name));
//                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//                actionBarDrawerToggle.syncState();
//                // }
//            }
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                // Avoid normal indicator glyph behaviour. This is to avoid glyph movement when opening the right drawer
//                //super.onDrawerSlide(drawerView, slideOffset);
//            }
//        };
//
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//
//    }
//
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        actionBarDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        actionBarDrawerToggle.onConfigurationChanged(newConfig);
//    }


    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mImageparallax.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mImageparallax.setLayoutParams(petDetailsLp);
        mFrameParallax.setLayoutParams(petBackgroundLp);

//        pDialog=new ProgressDialog(this);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);

    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                scaleView(imageLayout, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                scaleView(imageLayout, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {

        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }


    public void scaleView(View v, int visibility) {
        AlphaAnimation anim = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0, 1) : new AlphaAnimation(1, 0);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(300);
        anim.setInterpolator(new OvershootInterpolator());
//        fab.startAnimation(anim);
//        anim.setFillAfter(true); // Needed to keep the result of the animation
        v.startAnimation(anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initialize() {

        try {
            JSONObject jobj = new JSONObject();
            jobj.accumulate(Keys.dealId, id);
            jobj.accumulate(Keys.userId, DealWithItApp.readFromPreferences(getApplicationContext(), Keys.userId, ""));
            Log.d("post", jobj.toString());
            if (DealWithItApp.isNetworkAvailable()) {
//                new Details().execute(jobj.toString());
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class Details extends AsyncTask<String, Void, JSONObject>

    {
        private ProgressDialog dialog = new ProgressDialog(CategoryDetailsActivity.this);


        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.dealdetailsModified);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
//            this.getSupportFragmentManager().beginTransaction()
//                    .remove(this.getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
//                    .commit();
//            pBar.setVisibility(View.GONE);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            onDone(jsonObject);
        }
    }

    private void onDone(final JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {

                    drawerLayout.setVisibility(View.VISIBLE);
                    Log.e("DATAAAAAAAAa", String.valueOf(jsonObject.getJSONObject(Keys.notice)));

                    JSONObject jobj = jsonObject.getJSONObject(Keys.notice);
                    if (jobj.has(Keys.deals)) {
                        JSONArray jarr = jobj.getJSONArray(Keys.deals);

                        ArrayList<AllPojos> getDealsdetails = new ArrayList<>();
                        AllPojos pojos = new AllPojos();

//                    for(int i=0;i<jarr.length();i++){
                        JSONObject jobj2 = jarr.getJSONObject(0);
//                        mImageparallax.setImageD(jobj2.get(Keys.deal_image));
                        DealWithItApp.saveToPreferences(getApplicationContext(), "DEAL_DETAIL", jobj2.toString());

                        setupViewPager(mViewpager);
                        setupTabLayout(tabLayout);

                        title.setText(jobj2.getString(Keys.deal_name));
                        if (jobj2.has(jobj2.getString(Keys.address)))
                            address.setText(jobj2.getString(Keys.address));
                        description.setText(jobj2.getString(Keys.description));
                        if (jobj2.has(jobj2.getString(Keys.deal_image)))
                            mImageparallax.setImageBitmap(DealWithItApp.base64ToBitmap(jobj2.getString(Keys.deal_image)));
                    }
//                        getDealsdetails.add(pojos);


//                    }


                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {

                    Handler mHandler = new Handler(this.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Handler mHandler = new Handler(this.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast("Something Went Wrong.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } else {
                Handler mHandler = new Handler(this.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DealWithItApp.showAToast("Something Went Wrong. Server is not responding");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}