package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Constants;

/**
 * Created by AMAN on 05-11-2015.
 */
public class ViewTemplateFragment extends DialogFragment implements View.OnClickListener {
    TextView titles,contents;
//    ImageView close;
    Button cancel;
    public static ViewTemplateFragment newInstance(String title,String content) {
        ViewTemplateFragment f = new ViewTemplateFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);

        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewtemplate, container,false);
//        view.findViewById(R.id.gotit_button).setOnClickListener(this);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }
    private void initialize(View view){
        titles=(TextView)view.findViewById(R.id.titles);
        contents=(TextView)view.findViewById(R.id.descriptions);
        titles.setText(getArguments().getString("title"));
        contents.setText(getArguments().getString("content"));
//        close=(ImageView)view.findViewById(R.id.close);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        cancel=(Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.gotit_button){
////            Intent intent=new Intent(getActivity(), BeforeBusinessProfileActivity.class);
////            startActivity(intent);
////            getActivity().finish();
//            DialogFragment dialogFrag = SuccessDialogFragment.newInstance();
//            dialogFrag.setCancelable(false);
//            dialogFrag.show(getFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
//        }

    }
}
