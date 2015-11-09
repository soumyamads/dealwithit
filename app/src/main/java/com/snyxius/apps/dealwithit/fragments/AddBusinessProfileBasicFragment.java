package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */


public class AddBusinessProfileBasicFragment extends Fragment implements View.OnClickListener {

     TextView  est_type_text,ambience_text,cuisine_text;
    static String s;
    DetailStroke mCallback;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (DetailStroke)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_business_profile_basic, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }

    private void initialise(View view){
        view.findViewById(R.id.continues).setOnClickListener(this);
        view.findViewById(R.id.est_type).setOnClickListener(this);
        view.findViewById(R.id.cuisine_layout).setOnClickListener(this);
        view.findViewById(R.id.ambience_layout).setOnClickListener(this);
        est_type_text = (TextView)view.findViewById(R.id.est_type_text);
        ambience_text = (TextView)view.findViewById(R.id.ambience_text);
        cuisine_text = (TextView)view.findViewById(R.id.cuisine_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continues:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.frmaecontainer,new AddBusinessProfileDetailFragment(),Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                        .commit();
                mCallback.setDetailStoke();
                break;
            case R.id.est_type:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new EstablishmentTypeFragment(), Constants.ESTABLISHMENTTYPE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.cuisine_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new CuisineTypeFragment(), Constants.CUISINE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.ambience_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new AmbienceTypeFragment(), Constants.AMBINENCE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();

                break;
        }
    }

    public void changeEstablishmentText(String string,ArrayList<String> arrayList){
        try {
            est_type_text.setText(string);
            JSONArray jsonArray = new JSONArray(arrayList);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.type, jsonArray);
            Log.v("array", jsonObject.toString());
            DealWithItApp.saveToPreferences(getActivity(), Keys.establishmentDetail, jsonObject.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeAmbienceText(String string,ArrayList<String> arrayList){
        try {
            ambience_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeCuisineText(String string,ArrayList<String> arrayList){
        try {
            cuisine_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public interface DetailStroke{
        void setDetailStoke();
    }

}