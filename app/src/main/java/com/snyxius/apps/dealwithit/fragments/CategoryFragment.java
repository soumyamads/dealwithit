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
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.CategoryAdapter;
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
public class CategoryFragment extends Fragment implements View.OnClickListener {


    ListView typeList;
    String[] values;
    DataPassListener mCallback;
    ArrayList<EstablishmentTypePojo> estTypeListArray;


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
            new getCategory().execute(WebServices.category);
        }else{

        }

    }
    private void initialise(View rootView) {
        typeList  =(ListView)rootView.findViewById(R.id.establishment_list);
        TextView title = (TextView)rootView.findViewById(R.id.title);
        title.setText("Select Category");
        rootView.findViewById(R.id.right_tick).setOnClickListener(this);
        rootView.findViewById(R.id.left_cross).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_tick:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.left_cross:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private class getCategory extends AsyncTask<String, Void, JSONObject> {

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

            onDone(jsonObject);
        }
    }

    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject.getString(Keys.status).equals(Constants.SUCCESS)){
                JSONObject object = jsonObject.getJSONObject(Keys.notice);
                JSONArray jArray=object.getJSONArray(Keys.category);
                estTypeListArray=new ArrayList<>();
                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        EstablishmentTypePojo cp = new EstablishmentTypePojo();
                        cp.setName(jArray.getString(i));
                        estTypeListArray.add(cp);
                    }
                }

                final CategoryAdapter adapter = new CategoryAdapter(getActivity(),estTypeListArray);
                typeList.setAdapter(adapter);
                typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        mCallback.passCategoryData(estTypeListArray.get(position).getName());
                        getActivity().getSupportFragmentManager().popBackStack();
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




    public interface DataPassListener{
         void passCategoryData(String data);
    }

}

