package com.snyxius.apps.dealwithit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.snyxius.apps.dealwithit.R;


/**
 * Created by snyxius on 10/14/2015.
 */
public class SignupFragment extends Fragment {
        Intent intent;
       Button signup;
     EditText firstname,lastname,email,mobileno,password;

public SignupFragment() {
        // Required empty public constructor
        }

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_fragment, container, false);
        return rootView;

}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                intent = new Intent(getActivity(), MerchantProfile.class);
//                startActivity(intent);
            }
        });
    }
    private void initialise(View rootView) {
        signup = (Button) rootView.findViewById(R.id.signup_button);
        firstname=(EditText)rootView.findViewById(R.id.firstname);
        lastname=(EditText)rootView.findViewById(R.id.lastname);
        email=(EditText)rootView.findViewById(R.id.email);
        mobileno=(EditText)rootView.findViewById(R.id.mobileno);
        password=(EditText)rootView.findViewById(R.id.password);

    }

    }

