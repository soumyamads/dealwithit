package com.snyxius.apps.dealwithit.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.extras.Validater;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;
import com.snyxius.apps.dealwithit.fragments.OtpDialogFragment;
import com.snyxius.apps.dealwithit.utils.ShowHidePasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by snyxius on 4/4/16.
 */
public class CreateTemplateActivity extends AppCompatActivity implements  View.OnClickListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Button create;
    EditText templatename,temptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_template);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        

        initialise();
    }

    private void initialise() {
        templatename = (EditText)findViewById(R.id.temp_name);
        temptext = (EditText)findViewById(R.id.temptext);
        create=(Button)findViewById(R.id.create_button);
        findViewById(R.id.create_button).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_button:
                validate();
//                Intent intent=new Intent(CreateTemplateActivity.this,DealTemplatesActivity.class);
//                startActivity(intent);
//                finish();
                break;
        }
    }

    public void validate() {
        templatename.clearFocus();
        temptext.clearFocus();
        
        if (templatename.getText().toString().isEmpty()) {
            templatename.setError("Field Required");
            templatename.requestFocus();
        } else if (temptext.getText().toString().isEmpty()) {
            temptext.setError("Field Required");
            temptext.requestFocus();
        } else {
            submit();
        }
    }


    private void submit() {
        try {
            JSONObject jsonObject = new JSONObject();
            String id = DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id, Constants.DEFAULT_STRING);
            jsonObject.accumulate(Keys.id, id);

            jsonObject.accumulate(Keys.templatename, templatename.getText().toString());
            jsonObject.accumulate(Keys.temptext, temptext.getText().toString());
//
//            jsonObject.accumulate(Keys.socketId, DealWithItApp.readFromPreferences(this, Keys.socketId, Constants.DEFAULT_STRING));
//            jsonObject.accumulate(Keys.establishmentName, templatename.getText().toString());
//            jsonObject.accumulate(Keys.token, DealWithItApp.readFromPreferences(this, Keys.token, Constants.DEFAULT_STRING));
            if (DealWithItApp.isNetworkAvailable()) {
                new createtemplate().execute(jsonObject.toString());
            } else {
                DealWithItApp.showAToast("No internet Connection");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class createtemplate extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(CreateTemplateActivity.this);
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
                jsonObject = WebRequest.postData(params[0], WebServices.createTemplate);
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
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

//                    DialogFragment dialogFrag = OtpDialogFragment.newInstance();
//                    dialogFrag.setCancelable(false);
//                    dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
                    Intent intent=new Intent(CreateTemplateActivity.this,DealTemplatesActivity.class);
                    startActivity(intent);
                    finish();
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
            }else{
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



