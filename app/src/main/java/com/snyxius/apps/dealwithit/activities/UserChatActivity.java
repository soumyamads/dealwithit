package com.snyxius.apps.dealwithit.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snyxius on 7/12/15.
 */
public class UserChatActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isShow=false;
    TextView title,desc,userId,timing,cost;
    CircleImageView proPic;
    ImageView editProfile,rightArrow,openClose;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    RelativeLayout rlTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initialise();
        initDrawer();
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
        title=(TextView)findViewById(R.id.title);
        desc=(TextView)findViewById(R.id.desc);
        userId=(TextView)findViewById(R.id.user_id);
        timing=(TextView)findViewById(R.id.timing);
        cost=(TextView)findViewById(R.id.cost);
        proPic=(CircleImageView)findViewById(R.id.profile_pic);
        editProfile=(ImageView)findViewById(R.id.edit_profile);
        rlTop=(RelativeLayout)findViewById(R.id.top);
        rlTop.setOnClickListener(this);
        rightArrow=(ImageView)findViewById(R.id.right_arrow);
        openClose=(ImageView)findViewById(R.id.open_close);
        openClose.setOnClickListener(this);

        title.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
        userId.setVisibility(View.GONE);
        cost.setVisibility(View.GONE);
        proPic.setVisibility(View.GONE);
        editProfile.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if((v.getId()==R.id.open_close)||(v.getId()==R.id.top)){
            if(isShow){
                isShow=false;
                hideDatas();
            }else{
                isShow=true;
                showDatas();
            }
        }
    }


    public void hideDatas(){
        title.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
        userId.setVisibility(View.GONE);
        cost.setVisibility(View.GONE);
        proPic.setVisibility(View.GONE);
        editProfile.setVisibility(View.GONE);
        RotateAnimation anim= new RotateAnimation(0f,-180f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        openClose.startAnimation(anim);

    }
    public void showDatas(){
        title.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);
        userId.setVisibility(View.VISIBLE);
        cost.setVisibility(View.VISIBLE);
        proPic.setVisibility(View.VISIBLE);
        editProfile.setVisibility(View.VISIBLE);
        RotateAnimation anim= new RotateAnimation(0f,180f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setFillBefore(true);
        openClose.startAnimation(anim);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.business_main, menu);
        return true;
    }
}
