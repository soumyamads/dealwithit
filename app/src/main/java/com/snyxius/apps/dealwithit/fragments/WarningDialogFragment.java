package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.BeforeBusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;

import org.json.JSONObject;

/**
 * Created by AMAN on 05-11-2015.
 */
public class WarningDialogFragment extends DialogFragment implements View.OnClickListener {

    public static WarningDialogFragment newInstance() {
        WarningDialogFragment f = new WarningDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.warning_dialog, container, false);
        view.findViewById(R.id.yes).setOnClickListener(this);
        view.findViewById(R.id.no).setOnClickListener(this);
        TextView textView = (TextView)view.findViewById(R.id.textView2);
        textView.setText("Business Profile is present with current deal");
        textView.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.yes){
            if (DealWithItApp.isNetworkAvailable()) {
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate(Keys.id,DealWithItApp.readFromPreferences(getActivity(),Keys.id,Constants.DEFAULT_STRING));
                    jsonObject.accumulate(Keys.deletebusinessprofilesIds,DealWithItApp.readFromPreferences(getActivity(), Keys.deletebusinessprofilesIds, Constants.DEFAULT_STRING));
                    jsonObject.accumulate(Keys.deleteFlag,String.valueOf(Constants.DEFAULT_ONE));
                    new DeleteBusinessId().execute(jsonObject.toString());

                }catch(Exception e){
                    e.printStackTrace();
                }

            } else {
                DealWithItApp.showAToast("Please check internet network");
            }
        }

        if(v.getId() == R.id.no){
                dismiss();
        }

    }

    private class DeleteBusinessId extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.postData(params[0], WebServices.deleteBuisnessProf);
            }catch (Exception e){

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    dismiss();
                    Intent intent = new Intent(getActivity(),BusinessProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                    dismiss();
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
