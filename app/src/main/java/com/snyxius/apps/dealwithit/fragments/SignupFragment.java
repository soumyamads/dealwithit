package com.snyxius.apps.dealwithit.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.LoginSignupActivity;
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

import java.net.URISyntaxException;


/**
 * Created by snyxius on 10/14/2015.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {


     EditText firstname,lastname,email,mobileno,establshname;
     EditText password,retypepassword;


    public static SignupFragment newInstance(String  sockets) {
        SignupFragment f = new SignupFragment();
        return f;
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.signup_fragment, container, false);
                return rootView;

        }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

    }



            private void initialise(View rootView) {
                rootView.findViewById(R.id.signup_button).setOnClickListener(this);
                firstname = (EditText) rootView.findViewById(R.id.firstname);
                lastname = (EditText) rootView.findViewById(R.id.lastname);
                establshname = (EditText) rootView.findViewById(R.id.establshname);
                email = (EditText) rootView.findViewById(R.id.email);
                mobileno = (EditText) rootView.findViewById(R.id.mobileno);
                password=(ShowHidePasswordEditText)rootView.findViewById(R.id.password);
                password.setCompoundDrawables(null, null, getResources().getDrawable(R.color.colorPrimary), null);
                retypepassword = (EditText) rootView.findViewById(R.id.retypepassword);

            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.signup_button:
                        validate();
                        break;
                }
            }

            public void validate() {
                establshname.clearFocus();
                firstname.clearFocus();
                lastname.clearFocus();
                mobileno.clearFocus();
                email.clearFocus();
                password.clearFocus();
                retypepassword.clearFocus();
                if (establshname.getText().toString().isEmpty()) {
                    establshname.setError("Field Required");
                    establshname.requestFocus();
                } else if (firstname.getText().toString().isEmpty()) {
                    firstname.setError("Field Required");
                    firstname.requestFocus();
                } else if (lastname.getText().toString().isEmpty()) {
                    lastname.setError("Field Required");
                    lastname.requestFocus();
                } else if (mobileno.getText().toString().isEmpty()) {
                    mobileno.setError("Field Required");
                    mobileno.requestFocus();
                }else if (mobileno.getText().toString().startsWith("0")) {
                        mobileno.setError("zero not allowed");
                        mobileno.requestFocus();
                }else if (mobileno.getText().toString().length() != 10) {
                    mobileno.setError("Mobile Number should be 10 digit without zero");
                    mobileno.requestFocus();
                }else if (email.getText().toString().isEmpty()) {
                    email.setError("Field Required");
                    email.requestFocus();
                } else if (!Validater.isValidEmail(email.getText().toString())) {
                    email.setError("Invalid Email");
                    email.requestFocus();
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Field Required");
                    password.requestFocus();
                }
//                else  if (!Validater.isValidPassword(password.getText().toString())) {
//                    password.setError("Invalid Format Password");
//                    password.requestFocus();
//                }
                else if (retypepassword.getText().toString().isEmpty()) {
                    retypepassword.setError("Field Required");
                    retypepassword.requestFocus();
                }
                else if (!password.getText().toString().equals(retypepassword.getText().toString())) {
                    retypepassword.setError("Password Mismatching");
                    retypepassword.requestFocus();
                } else {
                     submit();
                }
            }


            private void submit() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(Keys.email, email.getText().toString());
                    jsonObject.accumulate(Keys.password, password.getText().toString());
                    jsonObject.accumulate(Keys.firstName, firstname.getText().toString());
                    jsonObject.accumulate(Keys.lastName, lastname.getText().toString());
                    jsonObject.accumulate(Keys.mobile, mobileno.getText().toString());
                    jsonObject.accumulate(Keys.socketId, DealWithItApp.readFromPreferences(getActivity(), Keys.socketId, Constants.DEFAULT_STRING));
                    jsonObject.accumulate(Keys.establishmentName, establshname.getText().toString());
                    jsonObject.accumulate(Keys.token, DealWithItApp.readFromPreferences(getActivity(), Keys.token, Constants.DEFAULT_STRING));
                    if (DealWithItApp.isNetworkAvailable()) {
                        new SignUp().execute(jsonObject.toString());
                    } else {
                        DealWithItApp.showAToast("No internet Connection");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



    private class SignUp extends AsyncTask<String, Void, JSONObject> {
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
                        jsonObject = WebRequest.postData(params[0], WebServices.signUp);
                    } catch (Exception e) {
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

            private void onDone(final JSONObject jsonObject) {
                try {
                    if(jsonObject != null) {
                        if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                            DealWithItApp.saveToPreferences(getActivity(), Keys.firstName, jsonObject.getString(Keys.firstName));
                            DealWithItApp.saveToPreferences(getActivity(), Keys.lastName, jsonObject.getString(Keys.lastName));
                            DealWithItApp.saveToPreferences(getActivity(), Keys.id, jsonObject.getString(Keys.id));
                            DealWithItApp.saveToPreferences(getActivity(), Keys.profileId, jsonObject.getString(Keys.profileId));
                            DealWithItApp.saveToPreferences(getActivity(), Keys.dealNo, jsonObject.getString(Keys.dealNo));
                            DialogFragment dialogFrag = OtpDialogFragment.newInstance();
                            dialogFrag.setCancelable(false);
                            dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
                        } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {

                            Handler mHandler = new Handler(getActivity().getMainLooper());
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

