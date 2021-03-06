package com.snyxius.apps.dealwithit.fragments;

import android.app.ProgressDialog;
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
import com.snyxius.apps.dealwithit.utils.ShowHidePasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Created by snyxius on 10/15/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
   private EditText email;
   private ShowHidePasswordEditText password;



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
        rootView.findViewById(R.id.login_button).setOnClickListener(this);
        email=(EditText)rootView.findViewById(R.id.email);
        password=(ShowHidePasswordEditText)rootView.findViewById(R.id.password);
        password.setCompoundDrawables(null, null, getResources().getDrawable(R.color.colorPrimary), null);
        rootView.findViewById(R.id.fgtpaswd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.show:
//                password.setInputType(InputType.TYPE_CLASS_TEXT);
//                break;
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
        private ProgressDialog dialog = new ProgressDialog(getActivity(),R.style.MyTheme);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            dialog.show();

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
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            onForgotDone(jsonObject);
        }
    }

    private void onForgotDone(final JSONObject jsonObject){
        try {
            if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){
                email.setText(Constants.DEFAULT_STRING);
                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
            }else if(jsonObject.getString(Keys.status).equals(Constants.FAILED)){
                Handler mHandler = new Handler(getActivity().getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }else{
                Handler mHandler = new Handler(getActivity().getMainLooper());
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
            }
//            else if (!Validater.isValidPassword(password.getText().toString())){
//                password.setError("Invalid Format Password");
//                password.requestFocus();
//            }
            else{
                    submit();
            }

    }

    private void submit(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.socketId, DealWithItApp.readFromPreferences(getActivity(),Keys.socketId,Constants.DEFAULT_STRING));
            jsonObject.accumulate(Keys.Email, email.getText().toString());
            jsonObject.accumulate(Keys.Password, password.getText().toString());
            jsonObject.accumulate(Keys.token, DealWithItApp.readFromPreferences(getActivity(), Keys.token, Constants.DEFAULT_STRING));
            Log.d("json",jsonObject.toString());
            if(DealWithItApp.isNetworkAvailable()){
                new Login().execute(jsonObject.toString());
            }else{
                DealWithItApp.showAToast("No internet Connection");


            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private class Login extends AsyncTask<String, Void, JSONObject>{
        private ProgressDialog dialog = new ProgressDialog(getActivity(),R.style.MyTheme);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            dialog.show();

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
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            onDone(jsonObject);
        }
    }

    private void onDone(final JSONObject jsonObject){
        try {

            if(jsonObject != null) {
//                    DealWithItApp.saveToPreferences(getActivity(), Keys.firstName, jsonObject.getString(Keys.firstName));
                    if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.saveToPreferences(getActivity(), Keys.lastName, jsonObject.getString(Keys.lastName));
                    DealWithItApp.saveToPreferences(getActivity(), Keys.id, jsonObject.getString(Keys.id));
                    DealWithItApp.saveToPreferences(getActivity(), Keys.profileId, jsonObject.getString(Keys.profileId));
                    DealWithItApp.saveToPreferences(getActivity(), Keys.dealNo, jsonObject.getString(Keys.dealNo));

                    if(!DealWithItApp.readFromPreferences(getActivity(), Keys.profileId,Constants.DEFAULT_STRING).equals(String.valueOf(Constants.DEFAULT_INT))){
                        Intent intent = new Intent(getActivity(), DealWithItActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                        getActivity().finish();
                    }else {
                        DialogFragment dialogFrag = SuccessDialogFragment.newInstance();
                        dialogFrag.setCancelable(false);
                        dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
                    }


                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    Handler mHandler = new Handler(getActivity().getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            DealWithItApp.showAToast("Failed");


                        }
                    });


                } else {
                    Handler mHandler = new Handler(getActivity().getMainLooper());
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
            }else{
                Handler mHandler = new Handler(getActivity().getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DealWithItApp.showAToast("Something Went Wrong.Server is not responding");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}