package com.snyxius.apps.dealwithit.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.Socket.SocketSingleton;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.AddBusinessProfileDetailFragment;
import com.snyxius.apps.dealwithit.fragments.LoginFragment;
import com.snyxius.apps.dealwithit.fragments.LoginSignupFragment;
import com.snyxius.apps.dealwithit.fragments.SignupFragment;
import com.snyxius.apps.dealwithit.fragments.SuccessDialogFragment;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 10/30/2015.
 */
public class LoginSignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter ;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = "117269319875";
    String regid;
    GoogleCloudMessaging gcm;
    String socket = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        SocketSingleton.get(this).getSocket().connect();
        SocketSingleton.get(this).getSocket().on(Keys.socketId, getSocketId);
        int position = getIntent().getIntExtra(Keys.position,Constants.DEFAULT_INT);
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            regid = getRegistrationId(getApplicationContext());
            Log.d("DeviceId", regid);
            if (regid.isEmpty()) {
                registerInBackground(false, "");


            } else if (!DealWithItApp.readFromPreferences(getApplicationContext(), "isSent", false)) {
                registerInBackground(true,
                        DealWithItApp.readFromPreferences(getApplicationContext(), "key", ""));
            }
        } else {

            Log.i("Debug", "No valid Google Play Services APK found.");
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.wrong).setOnClickListener(this);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }

    private Emitter.Listener getSocketId = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        socket = args[0].toString();
                        DealWithItApp.saveToPreferences(getApplicationContext(),Keys.socketId,socket);
                        Log.d("socketId",socket);
                    } catch (Exception e) {

                    }
                }
            });
        }
    };


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



    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("Debug", "This device is not supported.");
               finish();
            }
            return false;
        }
        return true;
    }


    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("Debug", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("Debug", "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getApplicationContext().getSharedPreferences(SplashActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void registerInBackground(boolean flag, String value) {
        try {
            if (flag) {
                new KeyAsyncTask().execute(value);
            } else {
                new KeyAsyncTask().execute("");
            }

        } catch (Exception e) {
        }
    }

    private class KeyAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject obj = null;
            try {
                if (params[0] != null
                        && params[0].toString().trim().length() > 0) {
                    regid = params[0];

                } else {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(SENDER_ID);

                }

                // You should send the registration ID to your server over
                // HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your
                // app.
                // The request to your server should be authenticated if
                // your app
                // is using accounts.
                obj = sendRegistrationIdToBackend();

                // For this demo: we don't need to send it because the
                // device
                // will send upstream messages to a server that echo back
                // the
                // message using the 'from' address in the message.

                // Persist the regID - no need to register again.
                storeRegistrationId(getApplicationContext(), regid);
            } catch (IOException ex) {
                // If there is an error, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off.
            }
            return obj;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result == null) {
                final SharedPreferences prefs = getGCMPreferences(getApplicationContext());
                int appVersion = getAppVersion(getApplicationContext());
                Log.i("Debug", "Saving regId on app version " + appVersion);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(PROPERTY_APP_VERSION, appVersion);
                editor.commit();
                DealWithItApp.saveToPreferences(getApplicationContext(), "key", regid);
                DealWithItApp.saveToPreferences(getApplicationContext(), "isSent", false);
            }

        }
    }

    private JSONObject sendRegistrationIdToBackend() {
        JSONObject data = null;
        try {
            DealWithItApp.saveToPreferences(getApplicationContext(),Keys.token,regid);
            Log.d("RegidterationId", regid.toString());
            //      String response = WebServices.postData(sendObj.toString(),3);
            // data = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i("Debug", "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.putBoolean("isSent", true);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }



}
