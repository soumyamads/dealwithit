package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.EditIncomingDealsAdapter;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditBusinessIncomingDeals extends Fragment implements View.OnClickListener{

    //int containing the duration of the animation run when items are added or removed from the RecyclerView
    public static final int ANIMATION_DURATION = 2000;
    //edit text letting the user type item name to be added to the recylcerview
    private EditText mInput;
    //recyclerview showing all items added by the user
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private EditIncomingDealsAdapter mAdapter;




    static  ArrayList<AllPojos> bookingsData = new ArrayList<>();

    public static   EditBusinessIncomingDeals newInstance(ArrayList<AllPojos> data) {
        bookingsData = data;
        EditBusinessIncomingDeals f = new EditBusinessIncomingDeals();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_incoming_recycler_view,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        view.findViewById(R.id.add_incoming_deals).setOnClickListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerAnimatedItems);
        mAdapter = new EditIncomingDealsAdapter(getActivity(), bookingsData, new EditIncomingDealsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<AllPojos> mTaskList) {
                if (view.getId() == R.id.button_delete) {
                    Log.d("Position", String.valueOf(position));
                    mTaskList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void addItem(ArrayList<AllPojos> data) {
        bookingsData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }



    public int validate(){
        if(bookingsData.isEmpty()){
           return Constants.INT_ONE;
        }else{
            return Constants.INT_TWO;

        }
    }






    public JSONArray sendBasicData(){
        try{

            JSONArray jsonArray = new JSONArray();

            for(int i=0;i<bookingsData.size();i++){

                JSONObject jsonObjectDeal = new JSONObject();
                jsonObjectDeal.put(Keys.max_guest, bookingsData.get(i).getMax_guest());
                jsonObjectDeal.put(Keys.cost_per_person, bookingsData.get(i).getCost_per_person());
                jsonObjectDeal.put(Keys.alcohol, bookingsData.get(i).getAlcohol());
                jsonObjectDeal.put(Keys.deal_offering, bookingsData.get(i).getDeal_offering());
                jsonArray.put(jsonObjectDeal);
            }

            return jsonArray;



        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_incoming_deals:
                DialogFragment dialogFrag = BusinessProfileIncomingDealDialogFragment.newInstance(Constants.INT_TWO);
                dialogFrag.setCancelable(false);
                dialogFrag.show(getFragmentManager().beginTransaction(), Constants.EditBusinessProfileIncomingDealDialogFragment);
                break;
        }
    }
}
