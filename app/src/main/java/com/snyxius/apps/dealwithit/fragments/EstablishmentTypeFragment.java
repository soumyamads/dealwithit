package com.snyxius.apps.dealwithit.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.AddBusinessProfileActivity;
import com.snyxius.apps.dealwithit.adapters.EstablishmentTypeAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.EstablishmentTypePojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by snyxius on 10/14/2015.
 */
public class EstablishmentTypeFragment extends Fragment {

    ImageView right_tick,left_cross;
    ListView jobList;
    String[] values;
    ArrayList<EstablishmentTypePojo> phoneList;

    public EstablishmentTypeFragment() {
        // Required empty public constructor
        }

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.establishment_type_dialog, container, false);
        return rootView;

}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);

        if (DealWithItApp.isNetworkAvailable()) {
            new getEstType().execute(WebServices.type);
        }else{

        }

//        String[] values = new String[] { "Fine Dine", "Lounge", "Bar",
//                "Family Restaurant", "Quick Service Restaurant", "Club", "Brewberry", "Desert & Bakery",
//                "Cafe", "Casual Dine", "Banquet" };



        right_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                getSelectedTypes();
            }
        });

        left_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
    private void initialise(View rootView) {
        jobList  =(ListView)rootView.findViewById(R.id.establishment_list);
        right_tick=(ImageView)rootView.findViewById(R.id.right_tick);
        left_cross=(ImageView)rootView.findViewById(R.id.left_cross);

    }

    private class getEstType extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_loading,new ProgressBarFrament(), Constants.PROGRESS_FRAGMENT)
                    .commit();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                return WebRequest.getData(params[0]);
            }catch (Exception e){

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(getActivity().getSupportFragmentManager().findFragmentByTag(Constants.PROGRESS_FRAGMENT))
                    .commit();
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){


//                values = getStringArray(jsonObject.getJSONArray(Keys.notice));
//                DealWithItApp.showAToast(values.toString());

//                final ArrayList<String> list = new ArrayList<String>();
//                for (int i = 0; i < values.length; ++i) {
//                    list.add(values[i]);
//                }
                JSONArray jArray=jsonObject.getJSONArray(Keys.notice);
                phoneList=new ArrayList<EstablishmentTypePojo>();
                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        EstablishmentTypePojo cp = new EstablishmentTypePojo();
                        cp.setName(jArray.getString(i));
                        phoneList.add(cp);
                    }
                }

                final EstablishmentTypeAdapter adapter = new EstablishmentTypeAdapter(getContext(),phoneList);
                jobList.setAdapter(adapter);
                jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {

                        CheckBox chk = (CheckBox) view
                                .findViewById(R.id.est_check_box);
                        EstablishmentTypePojo bean = phoneList.get(position);
                        if (bean.isSelected()) {
                            bean.setSelected(false);
                            chk.setChecked(false);
                        } else {
                            bean.setSelected(true);
                            chk.setChecked(true);
                        }

                    }
                });
            }else if(jsonObject.getString(Keys.status).equals(Constants.FAILED)){
                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String[] getStringArray(JSONArray jsonArray){
        String[] stringArray = null;
        int length = jsonArray.length();
        if(jsonArray!=null){
            stringArray = new String[length];
            for(int i=0;i<length;i++){
                stringArray[i]= jsonArray.optString(i);
            }
        }
        return stringArray;
    }

    private void getSelectedTypes() {
        // TODO Auto-generated method stub

        StringBuffer sb = new StringBuffer();
        ArrayList<String> selectedTypes=new ArrayList<>();
        for (EstablishmentTypePojo bean : phoneList) {

            if (bean.isSelected()) {
                sb.append(bean.getName());
                sb.append(",");
                selectedTypes.add(bean.getName());
            }
        }

        String s = sb.toString().trim();

        if (TextUtils.isEmpty(s)) {
            DealWithItApp.showAToast("Select atleast one Contact");
        } else {

            s = s.substring(0, s.length() - 1);
            DealWithItApp.showAToast("Selected Types : " + s);

        }

        //Establishment establis = (Establishment) getActivity();
        //establis.setEstablishType(selectedTypes);
    }

    interface Establishment{
        void setEstablishType(ArrayList<String> array);
    }


    }

