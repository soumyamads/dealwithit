package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;
import com.snyxius.apps.dealwithit.extras.Constants;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class CreateDealStepOneFragment extends Fragment implements View.OnClickListener {


    RelativeLayout estType;
    TextView est_type_text;

    StepTwoStroke mCallback;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (StepTwoStroke) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }


    public CreateDealStepOneFragment() {
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
        View rootView = inflater.inflate(R.layout.create_deal_step_one, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

    }

    private void initialise(View view) {

        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.select_business_layout).setOnClickListener(this);
        est_type_text = (TextView) view.findViewById(R.id.est_type_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_createdeal, new CreateDealStepTwoFragment(), Constants.CREATE_STEP_ONE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                mCallback.setStepTwoStoke();
                break;
//            case R.id.est_type:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
//                        .add(R.id.container, new EstablishmentTypeFragment(), Constants.ESTABLISHMENTTYPE_FRAGMENT)
//                        .addToBackStack(null)
//                        .commit();
//                break;
            case R.id.select_business_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new ExpandableFragment(), "demo")
                        .addToBackStack(null)
                        .commit();

        }
    }

    public interface StepTwoStroke {
        void setStepTwoStoke();
    }
}
