package com.snyxius.apps.dealwithit.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
 * Created by snyxius on 10/14/2015.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {


     EditText firstname,lastname,email,mobileno,establshname;
     CustomPasswordEditText password,retypepassword;


@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        firstname=(EditText)rootView.findViewById(R.id.firstname);
        lastname=(EditText)rootView.findViewById(R.id.lastname);
        establshname =(EditText)rootView.findViewById(R.id.establshname);
        email=(EditText)rootView.findViewById(R.id.email);
        mobileno=(EditText)rootView.findViewById(R.id.mobileno);
        password=(CustomPasswordEditText)rootView.findViewById(R.id.password);
        retypepassword=(CustomPasswordEditText)rootView.findViewById(R.id.retypepassword);
        rootView.findViewById(R.id.show).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.signup_button:
                validate();
                break;
        }
    }

    private void validate() {
        establshname.clearFocus();
        firstname.clearFocus();
        lastname.clearFocus();
        mobileno.clearFocus();
        email.clearFocus();
        password.clearFocus();

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
        }/* else if (!Validater.isValidMobile(mobileno.getText().toString())) {
            mobileno.setError("Invalid Mobile No");
            mobileno.requestFocus();
        }*/ else if (email.getText().toString().isEmpty()) {
            email.setError("Field Required");
            email.requestFocus();
        } else if (!Validater.isValidEmail(email.getText().toString())) {
            email.setError("Invalid Email");
            email.requestFocus();
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Field Required");
            password.requestFocus();
        }/* else if (!Validater.isValidPassword(password.getText().toString())) {
            password.setError("Invalid Format Password");
            password.requestFocus();
        }*/ else if (!password.getText().toString().equals(retypepassword.getText().toString())) {
            retypepassword.setError("Password Mismatching");
            retypepassword.requestFocus();
        } else {
            submit();
        }
    }


    private void submit(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.email, email.getText().toString());
            jsonObject.accumulate(Keys.password, password.getText().toString());
            jsonObject.accumulate(Keys.firstName, firstname.getText().toString());
            jsonObject.accumulate(Keys.lastName, lastname.getText().toString());
            jsonObject.accumulate(Keys.mobile, mobileno.getText().toString());
            jsonObject.accumulate(Keys.establishmentName, establshname.getText().toString());
            if(DealWithItApp.isNetworkAvailable()){
                new SignUp().execute(jsonObject.toString());
            }else{

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private class SignUp extends AsyncTask<String, Void, JSONObject> {

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
                jsonObject = WebRequest.postData(params[0], WebServices.signUp);
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
            }else if(jsonObject.getString(Keys.status).equals(Constants.FAILED)){
                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

