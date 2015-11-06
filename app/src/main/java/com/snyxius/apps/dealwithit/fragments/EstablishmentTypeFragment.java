package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.AddBusinessProfileActivity;
import com.snyxius.apps.dealwithit.adapters.EstablishmentTypeAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/14/2015.
 */
public class EstablishmentTypeFragment extends Fragment {

    ImageView right_tick,left_cross;
    ListView jobList;

    public EstablishmentTypeFragment() {
        // Required empty public constructor
        }

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.establishment_type_dialog, container, false);
        return rootView;

}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

        if (DealWithItApp.isNetworkAvailable()) {
            new getEstType().execute(WebServices.type);
        }else{

        }

        String[] values = new String[] { "Fine Dine", "Lounge", "Bar",
                "Family Restaurant", "Quick Service Restaurant", "Club", "Brewberry", "Desert & Bakery",
                "Cafe", "Casual Dine", "Banquet" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final EstablishmentTypeAdapter adapter = new EstablishmentTypeAdapter(getContext(),values);
        jobList.setAdapter(adapter);

        right_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        left_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
    private void initialise(View rootView) {
        jobList  =(ListView)rootView.findViewById(R.id.establishment_list);
        right_tick=(ImageView)rootView.findViewById(R.id.right_tick);
        left_cross=(ImageView)rootView.findViewById(R.id.left_cross);

    }

    private class getEstType extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_loading,new ProgressBarFrament(), Constants.PROGRESS_FRAGMENT)
                    .commit();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.getData(params[0]);
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

