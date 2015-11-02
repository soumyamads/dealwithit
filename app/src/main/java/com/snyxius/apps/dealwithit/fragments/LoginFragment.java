package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;


/**
 * Created by snyxius on 10/15/2015.
 */
public class LoginFragment extends Fragment {
    Intent intent;
    EditText email,password;
    TextView fgtpaswd;
    Button login;

    public LoginFragment() {
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
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                initialise(view);
//                intent = new Intent(getActivity(), MerchantProfile.class);
//                startActivityy(intent);

                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.success_dialog_layout);
                dialog.setCanceledOnTouchOutside(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();//dialog.getWindow().getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.dimAmount=0.60f;
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

//                window.setAttributes(lp);
//                   lp.dimAmount = 0.6f;
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setGravity(Gravity.CENTER);
//                   lp.gravity = Gravity.CENTER;

//                lp.x = -100;   //x position
//                lp.y = -100;   //y position
                dialog.show();
            }
        });
    }

    private void initialise(View rootView){

        email=(EditText)rootView.findViewById(R.id.email);
        password=(EditText)rootView.findViewById(R.id.password);
        fgtpaswd=(TextView)rootView.findViewById(R.id.fgtpaswd);
        login = (Button) rootView.findViewById(R.id.login_button);
    }
}