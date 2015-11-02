package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;


/**
 * Created by snyxius on 10/15/2015.
 */
public class BookingsFragment extends Fragment {
//    Intent intent;
//    EditText email,password;
//    TextView fgtpaswd;
//    Button login;

    public BookingsFragment() {
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
        View rootView = inflater.inflate(R.layout.no_deal_layout, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
//        view.findViewById(R.id.continues).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
////                initialise(view);
////                intent = new Intent(getActivity(), MerchantProfile.class);
////                startActivityy(intent);
//                ((BusinessProfileActivity)getActivity()).selectPage(1);
//
//            }
//        });
    }

    private void initialise(View rootView){

//        email=(EditText)rootView.findViewById(R.id.email);
//        password=(EditText)rootView.findViewById(R.id.password);
//        fgtpaswd=(TextView)rootView.findViewById(R.id.fgtpaswd);
//        login = (Button) rootView.findViewById(R.id.login_button);
    }
}