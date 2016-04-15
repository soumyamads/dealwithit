package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;


/**
 * Created by snyxius on 10/14/2015.
 */
public class TermsandConditions extends Fragment implements View.OnClickListener {
    EditText typetext;
    TextView template;
//    sendTemplate template;
    ListView typeList;
    String[] values;
    TandcPassListener mCallback;
//    ArrayList<AllPojos> estTypeListArray;

    static String strings;
    ProgressBar progressBar;
    TextView emptytext;
//    ArrayList<String> arrayList;

    public static TermsandConditions newInstance(String string) {
        strings = string;
        TermsandConditions f = new TermsandConditions();
        return f;
    }



    public void getTermsTemplateSelected(String content) {
        typetext.setText(content);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.termsandcondition, container, false);
        return rootView;

    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        try {
            initialise(view);
//            splitingData();
//            if (DealWithItApp.isNetworkAvailable()) {
//                String s = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate(Keys.id, s);
//                new getAllBusinessProfile().execute(jsonObject.toString());
//            } else {
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    private void initialise(View rootView) {
//        progressBar=(ProgressBar)rootView.findViewById(R.id.pBar);
//        emptytext=(TextView)rootView.findViewById(R.id.empty);
//        emptytext.setVisibility(View.GONE);
//        typeList  =(ListView)rootView.findViewById(R.id.establishment_list);
         template = (TextView)rootView.findViewById(R.id.template);

//        title.setText("Select Business Profile");
        rootView.findViewById(R.id.right).setOnClickListener(this);
        rootView.findViewById(R.id.template).setOnClickListener(this);

        rootView.findViewById(R.id.wrong).setOnClickListener(this);
        typetext=(EditText)rootView.findViewById(R.id.writehere);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right:
                getSelectedTypes();
                break;
            case R.id.wrong:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.template:
                FragmentManager manager = getFragmentManager();

                TempFragment dialog = new TempFragment();
                dialog.show(manager, "tandc");

                break;
        }
    }



    private void getSelectedTypes() {
        try {
//            StringBuffer sb = new StringBuffer();
//            ArrayList<String> selectedBusinessName = new ArrayList<>();
//            ArrayList<String> selectedBusinessIds = new ArrayList<>();

//            for (AllPojos bean : estTypeListArray) {
//                if (bean.isSelected()) {
//                    sb.append(bean.getBusiness_name());
//                    sb.append(",");
//                    String string = bean.getBusiness_name();
//                    String[] splited = string.split("\\s");
//                    selectedBusinessIds.add(bean.getProfile_id());
//                    selectedBusinessName.add(bean.getBusiness_name());
//                }
//            }
//            String s = sb.toString().trim();
            if (typetext.length()>0) {
                mCallback.passTandCData(typetext.getText().toString());
                getActivity().getSupportFragmentManager().popBackStack();

            } else {
//                s = s.substring(0, s.length() - 1);
                DealWithItApp.showAToast("Type anything");

            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public interface TandcPassListener{
         void passTandCData(String data);
    }

}

