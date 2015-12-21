package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.AdapterRecyclerAnimators;

public class ActivityRecylerAnimators extends Fragment {

    //int containing the duration of the animation run when items are added or removed from the RecyclerView
    public static final int ANIMATION_DURATION = 2000;
    //edit text letting the user type item name to be added to the recylcerview
    private EditText mInput;
    //recyclerview showing all items added by the user
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private AdapterRecyclerAnimators mAdapter;


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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerAnimatedItems);
        mAdapter = new AdapterRecyclerAnimators(getActivity());
        //set an animator on the RecyclerView that works only when items are added or removed
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Invoked after user hits the button to add an Item to the RecyclerView, check the contents of the EditText,
     * if it has valid contents, add the item to the Adapter of the RecyclerView
     *
     * @param view The Button that was clicked after user types text in the EditText
     */
    public void addItem(View view) {
        //check if the EditText has valid contents

            mAdapter.addItem(mInput.getText().toString());

    }






}
