package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class CreateDealStepThreeFragment extends Fragment implements View.OnClickListener {


    RadioButton recurring,fixedrate;
    LinearLayout recuringlayout;
    RadioGroup radioGroup;
    StepOneStroke mCallback;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (StepOneStroke) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }


    public CreateDealStepThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.create_deal_step_three, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_recurring) {
                    recuringlayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radio_fixed) {
                    recuringlayout.setVisibility(View.GONE);
                }
            }


        });

    }

    private void initialise(View view){
        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.radio_fixed).setOnClickListener(this);
        view.findViewById(R.id.radio_recurring).setOnClickListener(this);
        radioGroup=(RadioGroup)view.findViewById(R.id.dateGroup);
        recuringlayout=(LinearLayout)view.findViewById(R.id.recurring_layout);
    }

    @Override
    public void onClick(View v) {

    }
    public interface StepOneStroke{
        void setStepThreeStoke();
    }
}