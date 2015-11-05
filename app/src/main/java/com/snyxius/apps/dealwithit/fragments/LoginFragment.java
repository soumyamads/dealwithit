package com.snyxius.apps.dealwithit.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.extras.Validater;
import com.snyxius.apps.dealwithit.utils.CustomPasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by snyxius on 10/15/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText email;
    CustomPasswordEditText password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        password=(CustomPasswordEditText)rootView.findViewById(R.id.password);
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
                    .add(R.id.container,new ProgressBarFrament(),Constants.PROGRESS_FRAGMENT)
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
     private void validate(){
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
                    .add(R.id.container,new ProgressBarFrament(),Constants.PROGRESS_FRAGMENT)
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
             if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){
                 DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                 DealWithItApp.saveToPreferences(getActivity(),Keys.id,jsonObject.getString(Keys.id));
                 DialogFragment dialogFrag = SuccessDialogFragment.newInstance();
                 dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
            }else if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){
                 DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
             }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}