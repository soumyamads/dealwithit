package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;
import com.snyxius.apps.dealwithit.extras.Constants;


/**
 * Created by snyxius on 10/15/2015.
 */
public class AddBusinessProfileDealFragment extends Fragment implements View.OnClickListener{
//    Intent intent;
//    EditText email,password;
//    TextView fgtpaswd;
//    Button login;

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

    }

    private void initialise(View view){
        view.findViewById(R.id.continue_deal).setOnClickListener(this);
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