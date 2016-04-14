package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.TempAdapter;
import com.snyxius.apps.dealwithit.adapters.TempTitleAdapter;
import com.snyxius.apps.dealwithit.adapters.EstablishmentTypeAdapter;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;
import com.snyxius.apps.dealwithit.utils.DividerItemDecoration;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;
import com.snyxius.apps.dealwithit.utils.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by snyxius on 5/4/16.
 */
public class TempFragment  extends DialogFragment implements
        AdapterView.OnItemClickListener {

//    String[] listitems = { "item01", "item02", "item03", "item04" };
RecyclerView mRecyclerView;
    LinearLayoutManager layoutManager;
    ListView mylist;
    Button done;
    sendTemplate template;

    ArrayList<AllPojos> estTypeListArray;
    TempTitleAdapter adapter;
    int getPositionCheckbox;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            template = (sendTemplate) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.templatelist, null, false);
//        mylist = (ListView) view.findViewById(R.id.list);
        done = (Button) view.findViewById(R.id.button1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=-1;
                if (TempTitleAdapter.clickedPOs!=-1) {
                    pos=TempTitleAdapter.clickedPOs;
                    DealWithItApp.showAToast(TempTitleAdapter.clickedPOs + "");
                }
                if(template!=null)
                template.getTemplateSelected(estTypeListArray.get(pos).getTemplate_Description());

                dismiss();
            }
        });

        mRecyclerView = (RecyclerView)view. findViewById(R.id.rvList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //add ItemDecoration
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(2));
        //or
//        mRecyclerView.addItemDecoration(
//                new DividerItemDecoration(this));
//        //or
//        mRecyclerView.addItemDecoration(
//                new DividerItemDecoration(this, R.drawable.divider));
//        mRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
////                        DealWithItApp.showAToast("Under Construction");
////                        Intent inten=new Intent(DealTemplatesActivity.this, UserChatActivity.class);
////                        startActivity(inten);
//                    }
//                })
//        );

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    public interface sendTemplate{
        public void getTemplateSelected(String content);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, listitems);
//
//
        estTypeListArray = new ArrayList<>();
getlist();
//        AllPojos cp = new AllPojos();
////        cp.setProfile_id("1");
//        cp.setTemplate_name();
//        estTypeListArray.add(cp);
//        AllPojos cp1 = new AllPojos();
//
//        cp1.setProfile_id("1");
//        cp1.setTemplate_name();
//        estTypeListArray.add(cp1);
//        AllPojos cp2 = new AllPojos();
//
//        cp2.setProfile_id("1");
//        cp2.setBusiness_name("template 3");
//        estTypeListArray.add(cp2);

//        TempTitleAdapter adapter=new TempTitleAdapter(getContext(),estTypeListArray);
//        mylist.setAdapter(adapter);



    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position,
//                            long id) {
//
//        dismiss();
////        Toast.makeText(getActivity(), listitems[position], Toast.LENGTH_SHORT)
////                .show();
//    }
    private void getlist() {
        try {
            JSONObject jsonObject = new JSONObject();
            String id = DealWithItApp.readFromPreferences(getActivity(), Keys.id, Constants.DEFAULT_STRING);
            jsonObject.accumulate(Keys.merchantId, id);


            if (DealWithItApp.isNetworkAvailable()) {
                new gettemplatetitle().execute(jsonObject.toString());
            } else {
                DealWithItApp.showAToast("No internet Connection");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



    private class gettemplatetitle extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.getTemplate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            onDone(jsonObject);
        }
    }

    private void onDone(final JSONObject jsonObject) {
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);

                    JSONArray jArray = obj.getJSONArray(Keys.getTemplateList);


                    estTypeListArray = new ArrayList<>();
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject1 = jArray.getJSONObject(i);
                            AllPojos cp = new AllPojos();
                            cp.setTemplate_Id(jsonObject1.getString(Keys.Id));
                            cp.setTemplate_name(jsonObject1.getString(Keys.templatename));
                            cp.setTemplate_Description(jsonObject1.getString(Keys.description_template));


                            estTypeListArray.add(cp);
                        }
                    }
                    adapter = new TempTitleAdapter((AppCompatActivity) getActivity(), estTypeListArray);
                    mRecyclerView.setAdapter(adapter);
//                    adapter = new BusinessProfileListAdapter(getApplicationContext(), estTypeListArray);
//                    mRecyclerView.setAdapter(adapter);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {

                    Handler mHandler = new Handler(getActivity().getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Handler mHandler = new Handler(getActivity().getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DealWithItApp.showAToast("Something Went Wrong.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            else{
                Handler mHandler = new Handler(getActivity().getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DealWithItApp.showAToast("Something Went Wrong. Server is not responding");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

