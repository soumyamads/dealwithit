package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;

import org.json.JSONObject;

/**
 * Created by amanjham on 27/01/16.
 */
public class TermsAndConditionsUser extends Fragment {

    static int count = Constants.DEFAULT_INT;
    public static TermsAndConditionsUser newInstance(int position) {
        count = position;
        Bundle args = new Bundle();
        TermsAndConditionsUser fragment = new TermsAndConditionsUser();
        fragment.setArguments(args);
        return fragment;
    }
    static JSONObject jsonObject = new JSONObject();

    public static TermsAndConditionsUser newInstance(JSONObject Object) {
        jsonObject = Object;
        TermsAndConditionsUser f = new TermsAndConditionsUser();
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.deals_termsansconditions_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
    }

    private void initialize(View view) {

        if(count == Constants.INT_ONE) {
//            view.findViewById(R.id.down_arrow1).setVisibility(View.GONE);
        }
    }


}
