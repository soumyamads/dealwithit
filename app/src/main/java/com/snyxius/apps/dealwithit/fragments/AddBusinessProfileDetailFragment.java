package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class AddBusinessProfileDetailFragment extends Fragment implements View.OnClickListener,Picker.PickListener {

    BasicStroke mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (BasicStroke)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_business_profile_details, container, false);
        return rootView;
     }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }

    private void initialise(View view){
        view.findViewById(R.id.prev).setOnClickListener(this);
        view.findViewById(R.id.continue_detail).setOnClickListener(this);
        view.findViewById(R.id.upload_menu_layout).setOnClickListener(this);
        view.findViewById(R.id.cover_image_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continue_detail:
                Intent intent = new Intent(getActivity(), DealWithItActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.prev:
                mCallback.setBasicStoke();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.upload_menu_layout:
                new Picker.Builder(getContext(),this,R.style.MIP_theme)
                        .setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
                        .setLimit(6)
                        .build()
                        .startActivity();
                break;
            case R.id.cover_image_layout:
                new Picker.Builder(getContext(),this,R.style.MIP_theme)
                        .setPickMode(Picker.PickMode.SINGLE_IMAGE)
                        .build()
                        .startActivity();
                break;

        }
    }

    @Override
    public void onPickedSuccessfully(ArrayList<ImageEntry> images) {
        Log.d("IMAGES", "Picked images  " + images.toString());
    }

    @Override
    public void onCancel() {
        Log.i("Images", "User canceled picker activity");
        Toast.makeText(getActivity(), "User canceld picker activtiy", Toast.LENGTH_SHORT).show();
    }

    public interface BasicStroke{
        void setBasicStoke();
    }


}