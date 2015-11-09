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
import com.snyxius.apps.dealwithit.extras.Constants;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class CreateDealStepTwoFragment extends Fragment  implements View.OnClickListener{


    RadioButton minimumbilling,minimumguest;
    LinearLayout linearguest,linearbilling;
    RadioGroup radioGroup;
    StepThreeStroke mCallback;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (StepThreeStroke)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    public CreateDealStepTwoFragment() {
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
        View rootView = inflater.inflate(R.layout.create_deal_step_two, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.min_guests) {

                    linearguest.setVisibility(View.VISIBLE);
                    linearbilling.setVisibility(View.GONE);
                } else if (checkedId == R.id.min_billing) {
                    linearguest.setVisibility(View.GONE);
                    linearbilling.setVisibility(View.VISIBLE);
                }
            }


        });

    }

    private void initialise(View view){
        linearbilling=(LinearLayout)view.findViewById(R.id.linearbilling);
        linearguest=(LinearLayout)view.findViewById(R.id.linearguest);
        radioGroup=(RadioGroup)view.findViewById(R.id.myRadioGroup);
        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.min_guests).setOnClickListener(this);
        view.findViewById(R.id.min_billing).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_createdeal, new CreateDealStepThreeFragment(), Constants.CREATE_STEP_TWO_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                mCallback.setStepThreeStoke();
                break;
        }
    }
    public interface StepThreeStroke{
        void setStepThreeStoke();
    }
}