package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
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


/**
 * Created by snyxius on 10/14/2015.
 */
public class TypeFragment extends Fragment implements View.OnClickListener {


    ListView typeList;
    String[] values;
    DataPassListener mCallback;
    ArrayList<EstablishmentTypePojo> estTypeListArray;
    ProgressBar progressBar;
    TextView emptytext;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (DataPassListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
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
            new getEstType().execute(WebServices.typeDetails);
        }else{

        }

    }
    private void initialise(View rootView) {
        progressBar=(ProgressBar)rootView.findViewById(R.id.pBar);
        emptytext=(TextView)rootView.findViewById(R.id.empty);
        emptytext.setVisibility(View.GONE);
        typeList  =(ListView)rootView.findViewById(R.id.establishment_list);
        TextView title = (TextView)rootView.findViewById(R.id.title);
        title.setText("Select Establishment");
        rootView.findViewById(R.id.right_tick).setOnClickListener(this);
        rootView.findViewById(R.id.left_cross).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_tick:
                getSelectedTypes();
                break;
            case R.id.left_cross:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private class getEstType extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


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
            progressBar.setVisibility(View.GONE);
            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){

                JSONArray jArray=jsonObject.getJSONArray(Keys.notice);
                estTypeListArray=new ArrayList<>();
                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        EstablishmentTypePojo cp = new EstablishmentTypePojo();
                        cp.setName(jArray.getString(i));
                        estTypeListArray.add(cp);
                    }
                }

                final EstablishmentTypeAdapter adapter = new EstablishmentTypeAdapter(getContext(),estTypeListArray);
                typeList.setAdapter(adapter);
                typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        CheckBox chk = (CheckBox) view
                                .findViewById(R.id.est_check_box);
                        EstablishmentTypePojo bean = estTypeListArray.get(position);
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
                emptytext.setVisibility(View.VISIBLE);
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void getSelectedTypes() {
        try {
            StringBuffer sb = new StringBuffer();
            ArrayList<String> selectedTypes = new ArrayList<>();
            for (EstablishmentTypePojo bean : estTypeListArray) {
                if (bean.isSelected()) {
                    sb.append(bean.getName());
                    sb.append(",");
                    String string = bean.getName();
                    String[] splited = string.split("\\s");
                    selectedTypes.add(splited[0]);
                }
            }
            String s = sb.toString().trim();
            if (TextUtils.isEmpty(s)) {
                DealWithItApp.showAToast("Select atleast one Contact");
            } else {
                s = s.substring(0, s.length() - 1);
                mCallback.passTypeData(s, selectedTypes);
                getActivity().getSupportFragmentManager().popBackStack();
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public interface DataPassListener{
         void passTypeData(String data,ArrayList<String> array);
    }

}

