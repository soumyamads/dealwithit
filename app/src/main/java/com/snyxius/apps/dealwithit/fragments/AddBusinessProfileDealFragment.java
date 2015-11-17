package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;
import com.snyxius.apps.dealwithit.extras.Constants;


/**
 * Created by snyxius on 10/15/2015.
 */
public class AddBusinessProfileDealFragment extends Fragment implements View.OnClickListener{
//    Intent intent;
    EditText leftIndexValue,rightIndexValue;
//    TextView fgtpaswd;
//    Button login;
// Initializes the RangeBar in the application
private RangeBar rangebar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
//            mBasicCallback = (AddBusinessProfileBasicFragment.BasicStroke)activity;
//            mDealCallback = (AddBusinessProfileDealFragment.DealStroke)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }


//    public AddBusinessProfileDealFragment() {
//        // Required empty public constructor
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.add_business_profile_deals, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        rangebar.setPinColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setTickColor(getResources().getColor(R.color.grey));
        rangebar.setConnectingLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setBarColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setSelectorColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                leftIndexValue.setText(leftPinValue);
                rightIndexValue.setText(rightPinValue);
            }
        });


    }

    private void initialise(View view){
        view.findViewById(R.id.continue_deal).setOnClickListener(this);
        rangebar = (RangeBar) view.findViewById(R.id.rangebar1);
//        rangebar.setTickStart(5);
//        rangebar.setTickInterval(1);
//        rangebar.setTickEnd(100);
        // Gets the index value TextViews
        leftIndexValue = (EditText) view.findViewById(R.id.leftIndexValue);
        rightIndexValue = (EditText) view.findViewById(R.id.rightIndexValue);
//        email=(EditText)rootView.findViewById(R.id.email);
//        password=(EditText)rootView.findViewById(R.id.password);
//        fgtpaswd=(TextView)rootView.findViewById(R.id.fgtpaswd);
//        login = (Button) rootView.findViewById(R.id.login_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_detail:

                Intent intent = new Intent(getActivity(), DealWithItActivity.class);
                startActivity(intent);
                getActivity().finish();

                break;
        }

    }






}