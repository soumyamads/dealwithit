package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.BeforeBusinessProfileActivity;

/**
 * Created by AMAN on 05-11-2015.
 */
public class QuickDescriptionDialogFragment extends DialogFragment implements View.OnClickListener {

    public static QuickDescriptionDialogFragment newInstance() {
        QuickDescriptionDialogFragment f = new QuickDescriptionDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quickdescription_dialog, container,false);
//        view.findViewById(R.id.gotit_button).setOnClickListener(this);
                view.findViewById(R.id.gotittext).setOnClickListener(this);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.gotittext){
           dismiss();
        }

    }
}
