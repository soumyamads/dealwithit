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

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.AddBusinessProfileActivity;


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

                Button gotIt=(Button)dialog.findViewById(R.id.gotit_button);
                gotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent inten=new Intent(getActivity(), AddBusinessProfileActivity.class);
                        startActivity(inten);
                        getActivity().finish();
                    }
                });
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

