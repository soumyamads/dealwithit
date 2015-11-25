package com.snyxius.apps.dealwithit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;


/**
 * Created by snyxius on 9/8/2015.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if(DealWithItApp.readFromPreferences(getApplicationContext(), Keys.profileId,Constants.DEFAULT_STRING) != null){
                    Intent intent = new Intent(SplashActivity.this, DealWithItActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                    SplashActivity.this.finish();
                } else if(!DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id,Constants.DEFAULT_STRING).equals(Constants.DEFAULT_STRING)){
                         Intent intent = new Intent(SplashActivity.this, BeforeBusinessProfileActivity.class);
                         startActivity(intent);
                         overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                         SplashActivity.this.finish();
                       } else if(DealWithItApp.readFromPreferences(getApplicationContext(), Keys.intro,false)) {
                        Intent intent = new Intent(SplashActivity.this, LoginSignupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(Keys.position, Constants.DEFAULT_ONE);
                        startActivity(intent);
                        SplashActivity.this.finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                    }else{
                        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                        SplashActivity.this.finish();
                    }
            }
        }, Constants.SPLASH_TIME_OUT);
    }

}
