package com.snyxius.apps.dealwithit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.LoginSignupActivity;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;

/**
 * Created by snyxius on 10/30/2015.
 */
public class LoginSignupFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_signup_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
                initialize(view);
    }

    private void initialize(View view){
        view.findViewById(R.id.signup).setOnClickListener(this);
        view.findViewById(R.id.login).setOnClickListener(this);
    }

    public static LoginSignupFragment newInstance(String text) {
        LoginSignupFragment f = new LoginSignupFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.signup:
                intent=new Intent(getActivity(),LoginSignupActivity.class);
                DealWithItApp.saveToPreferences(getActivity(),Keys.intro,true);
                intent.putExtra(Keys.position,Constants.DEFAULT_INT);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                break;
            case R.id.login:
                intent = new Intent(getActivity(),LoginSignupActivity.class);
                DealWithItApp.saveToPreferences(getActivity(),Keys.intro,true);
                intent.putExtra(Keys.position,Constants.DEFAULT_ONE);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                break;
        }
    }
}