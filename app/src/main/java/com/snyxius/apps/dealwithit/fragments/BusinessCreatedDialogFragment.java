package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.AddBusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;

/**
 * Created by AMAN on 05-11-2015.
 */
public class BusinessCreatedDialogFragment extends DialogFragment implements View.OnClickListener {

    public static BusinessCreatedDialogFragment newInstance() {
        BusinessCreatedDialogFragment f = new BusinessCreatedDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_profile_created, container,false);
        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.create_another).setOnClickListener(this);
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
        if(v.getId() == R.id.continue_button){
            Intent intent=new Intent(getActivity(), DealWithItActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        if(v.getId() == R.id.create_another){
            Intent intent = new Intent(getActivity(),BusinessProfileActivity.class);
            startActivity(intent);
            getActivity().finish();
        }




    }
}
