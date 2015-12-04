package com.snyxius.apps.dealwithit.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;
import com.snyxius.apps.dealwithit.activities.SplashActivity;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.extras.Validater;
import com.snyxius.apps.dealwithit.utils.CustomPasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Created by snyxius on 10/15/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
   private EditText email;
   private EditText password;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = "117269319875";
    String regid;
    GoogleCloudMessaging gcm;


    public static LoginFragment newInstance(String  sockets) {

        LoginFragment f = new LoginFragment();
        return f;
    }


       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }

    private void initialise(View rootView){
        rootView.findViewById(R.id.show).setOnClickListener(this);
        rootView.findViewById(R.id.login_button).setOnClickListener(this);
        email=(EditText)rootView.findViewById(R.id.email);
        password=(EditText)rootView.findViewById(R.id.password);
        rootView.findViewById(R.id.fgtpaswd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.login_button:
                    validate();
                break;
            case R.id.fgtpaswd:
                fgtvalidate();
                break;
        }
    }

    private void fgtvalidate(){
        email.clearFocus();
        password.clearFocus();
        if(email.getText().toString().isEmpty()){
            email.setError("Field Required");
            email.requestFocus();
        }else if(!Validater.isValidEmail(email.getText().toString())){
            email.setError("Invalid Email");
            email.requestFocus();
        }else{
                forgotsubmit();
        }
    };

    private void forgotsubmit(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.email, email.getText().toString());
            if(DealWithItApp.isNetworkAvailable()){
                new Forgot().execute(jsonObject.toString());
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class Forgot extends AsyncTask<String,Void,JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_loading,new ProgressBarFrament(),Constants.PROGRESS_FRAGMENT)
                    .commit();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.forgotPass);
            }catch (Exception e){
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(getActivity().getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
                    .commit();
            onForgotDone(jsonObject);
        }
    }

    private void onForgotDone(JSONObject jsonObject){
        try {
            if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){
                email.setText(Constants.DEFAULT_STRING);
                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
            }else if(jsonObject.getString(Keys.status).equals(Constants.FAILED)){
                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
     public void validate(){
         email.clearFocus();
         password.clearFocus();
            if(email.getText().toString().isEmpty()){
                email.setError("Field Required");
                email.requestFocus();
            }else if(!Validater.isValidEmail(email.getText().toString())){
                email.setError("Invalid Email");
                email.requestFocus();
            }else if(password.getText().toString().isEmpty()){
                password.setError("Field Required");
                password.requestFocus();
            }/* else if (!Validater.isValidPassword(password.getText().toString())){
                password.setError("Invalid Format Password");
                password.requestFocus();
            }*/else{
                    submit();
            }

    }

    private void submit(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.socketId, DealWithItApp.readFromPreferences(getActivity(),Keys.socketId,Constants.DEFAULT_STRING));
            jsonObject.accumulate(Keys.Email, email.getText().toString());
            jsonObject.accumulate(Keys.Password, password.getText().toString());
            Log.d("json",jsonObject.toString());
            if(DealWithItApp.isNetworkAvailable()){
                new Login().execute(jsonObject.toString());
            }else{

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private class Login extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_loading,new ProgressBarFrament(),Constants.PROGRESS_FRAGMENT)
                    .commit();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.signIn);
            }catch (Exception e){
                e.printStackTrace();
            }
                return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(getActivity().getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
                    .commit();
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    DealWithItApp.saveToPreferences(getActivity(), Keys.id, jsonObject.getString(Keys.id));
                    DealWithItApp.saveToPreferences(getActivity(), Keys.profileId, jsonObject.getString(Keys.profileId));

                    if (checkPlayServices()) {
                        gcm = GoogleCloudMessaging.getInstance(getActivity());
                        regid = getRegistrationId(getActivity());
                        Log.d("DeviceId", regid);
                        if (regid.isEmpty()) {
                            registerInBackground(false, "");


                        } else if (!DealWithItApp.readFromPreferences(getActivity(), "isSent", false)) {
                            registerInBackground(true,
                                    DealWithItApp.readFromPreferences(getActivity(), "key", ""));
                        }
                    } else {

                        Log.i("Debug", "No valid Google Play Services APK found.");
                    }


                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something Went Wrong.Server is not responding");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("Debug", "This device is not supported.");
                getActivity().finish();
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
        return getActivity().getSharedPreferences(SplashActivity.class.getSimpleName(),
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
                        gcm = GoogleCloudMessaging.getInstance(getActivity());
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
                storeRegistrationId(getActivity(), regid);
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
                final SharedPreferences prefs = getGCMPreferences(getActivity());
                int appVersion = getAppVersion(getActivity());
                Log.i("Debug", "Saving regId on app version " + appVersion);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(PROPERTY_APP_VERSION, appVersion);
                editor.commit();

                DealWithItApp.saveToPreferences(getActivity(), "key", regid);
                DealWithItApp.saveToPreferences(getActivity(), "isSent", false);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!DealWithItApp.readFromPreferences(getActivity(), Keys.profileId,Constants.DEFAULT_STRING).equals(String.valueOf(Constants.DEFAULT_INT))
                            && !DealWithItApp.readFromPreferences(getActivity(), Keys.profileId,Constants.DEFAULT_STRING).equals(Constants.DEFAULT_STRING)){
                        Intent intent = new Intent(getActivity(), DealWithItActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                        getActivity().finish();
                    }else {
                        DialogFragment dialogFrag = SuccessDialogFragment.newInstance();
                        dialogFrag.setCancelable(false);
                        dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
                    }
                }

            }, 1000);


        }
    }

    private JSONObject sendRegistrationIdToBackend() {
        JSONObject data = null;
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("device_id", regid);
            obj.accumulate("device","");
            obj.accumulate("user_id", DealWithItApp.readFromPreferences(getActivity(), Keys.id, ""));
            JSONObject sendObj = new JSONObject();
            sendObj.accumulate("newDevice", obj);
            Log.d("RegidterationId", sendObj.toString());
      //      String response = WebServices.postData(sendObj.toString(),3);
      // data = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
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