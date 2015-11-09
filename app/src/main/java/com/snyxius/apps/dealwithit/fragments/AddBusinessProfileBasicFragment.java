package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */


public class AddBusinessProfileBasicFragment extends Fragment implements View.OnClickListener {

    static TextView  est_type_text;
    static String s;
    DetailStroke mCallback;
ArrayList<String> arrayList = new ArrayList<>();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continues:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.frmaecontainer,new AddBusinessProfileDetailFragment(),Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                        .addToBackStack(null)
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
                        .add(R.id.container, new ExpandableFragment(), "demo")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.ambience_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new ExpandableFragment(), "demo")
                        .addToBackStack(null)
                        .commit();

                break;
        }
    }

    public void changetext(String string){
        est_type_text.setText(string);
    }



    public interface DetailStroke{
        void setDetailStoke();
    }

}