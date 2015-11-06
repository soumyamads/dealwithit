package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;


/**
 * Created by snyxius on 10/15/2015.
 */
public class CreateDealStepTwoFragment extends Fragment  {


    RelativeLayout estType;


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
        view.findViewById(R.id.continues).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                initialise(view);
//                intent = new Intent(getActivity(), MerchantProfile.class);
//                startActivityy(intent);
                ((BusinessProfileActivity) getActivity()).selectPage(1);

            }
        });
        view.findViewById(R.id.est_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out,R.anim.push_up_in, R.anim.push_down_out)
                        .replace(R.id.container, new EstablishmentTypeFragment(), "demo")
                        .addToBackStack("est")
                        .commit();
            }
        });
    }

    private void initialise(View rootView){

//        email=(EditText)rootView.findViewById(R.id.email);
//        password=(EditText)rootView.findViewById(R.id.password);
//        fgtpaswd=(TextView)rootView.findViewById(R.id.fgtpaswd);
//        login = (Button) rootView.findViewById(R.id.login_button);
    }
}