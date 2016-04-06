package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;

/**
 * Created by AMAN on 05-11-2015.
 */
public class OtpDialogFragment extends DialogFragment implements View.OnClickListener {

    public static OtpDialogFragment newInstance() {
        OtpDialogFragment f = new OtpDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otp_dialog_layout, container,false);
        view.findViewById(R.id.gotit_button).setOnClickListener(this);
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
        if(v.getId() == R.id.gotit_button){
//            Intent intent=new Intent(getActivity(), BeforeBusinessProfileActivity.class);
//            startActivity(intent);
//            getActivity().finish();
            DialogFragment dialogFrag = SuccessDialogFragment.newInstance();
            dialogFrag.setCancelable(false);
            dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
        }

    }
}
