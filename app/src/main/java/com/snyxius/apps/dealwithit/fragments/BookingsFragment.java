package com.snyxius.apps.dealwithit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;


/**
 * Created by snyxius on 10/15/2015.
 */
public class BookingsFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        return rootView;
     }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }

    private void initialise(View view){

    }

    @Override
    public void onClick(View v) {

    }
}