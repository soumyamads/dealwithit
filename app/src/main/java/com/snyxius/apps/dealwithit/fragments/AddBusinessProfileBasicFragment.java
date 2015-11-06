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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;
import com.snyxius.apps.dealwithit.adapters.EstablishmentTypeAdapter;

import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */
public class AddBusinessProfileBasicFragment extends Fragment {

    RelativeLayout estType;


    public AddBusinessProfileBasicFragment() {
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
        View rootView = inflater.inflate(R.layout.add_business_profile_basic, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        view.findViewById(R.id.continues).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                initialise(view);
//                intent = new Intent(getActivity(), MerchantProfile.class);
//                startActivityy(intent);
                ((BusinessProfileActivity) getActivity()).selectPage(1);

            }
        });
        view.findViewById(R.id.est_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Dialog dialog = new Dialog(getActivity());
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.setContentView(R.layout.establishment_type_dialog);
//                dialog.setCanceledOnTouchOutside(true);
//
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();//dialog.getWindow().getAttributes();
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
////                lp.dimAmount = 0.60f;
//                dialog.getWindow().setAttributes(lp);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
////
////                window.setAttributes(lp);
//////                   lp.dimAmount = 0.6f;
////                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
////                dialog.getWindow().setGravity(Gravity.CENTER);
////                   lp.gravity = Gravity.CENTER;
//
////                lp.x = -100;   //x position
////                lp.y = -100;   //y position
//
//                ListView jobList  =(ListView)dialog.findViewById(R.id.establishment_list);
//
////                final ListView listview = (ListView) findViewById(R.id.listview);
//                String[] values = new String[] { "Fine Dine", "Lounge", "Bar",
//                        "Family Restaurant", "Quick Service Restaurant", "Club", "Brewberry", "Desert & Bakery",
//                        "Cafe", "Casual Dine", "Banquet" };
//
//                final ArrayList<String> list = new ArrayList<String>();
//                for (int i = 0; i < values.length; ++i) {
//                    list.add(values[i]);
//                }
//                final EstablishmentTypeAdapter adapter = new EstablishmentTypeAdapter(getContext(),values);
//                jobList.setAdapter(adapter);
//                dialog.show();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out,R.anim.push_up_in, R.anim.push_down_out)
                        .replace(R.id.container, new EstablishmentTypeFragment(), "demo")
                        .addToBackStack("est")
                        .commit();
            }
        });
    }

    private void initialise(View rootView){

//        email=(EditText)rootView.findViewById(R.id.email);
//        password=(EditText)rootView.findViewById(R.id.password);
//        fgtpaswd=(TextView)rootView.findViewById(R.id.fgtpaswd);
//        login = (Button) rootView.findViewById(R.id.login_button);
    }
}