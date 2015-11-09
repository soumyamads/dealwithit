package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.DrawerAdapter;
import com.snyxius.apps.dealwithit.adapters.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 11/9/2015.
 */
public class DrawerFragment extends Fragment {

    DrawerAdapter mAdapter;
    RecyclerView mRecyclerView;
    String[] values = new String[] {"Home", "Create a Deal", "Chats", "Confirmations",
            "QR Code Scanner", "My Active Deals", "All Deals", "Search Booking ID", "Templates",
            "Bussiness Profile", "Add a Bussiness Profile", "Merchant Profile",
            "Settings","Logout","Payment", "about us"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        //This is the code to provide a sectioned list
        List<SectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SectionedRecyclerViewAdapter.Section(1,"DEALS"));
        sections.add(new SectionedRecyclerViewAdapter.Section(9,"PROFILE"));
        sections.add(new SectionedRecyclerViewAdapter.Section(12,"ACCOUNT"));
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14,"Section 4"));
//        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(20,"Section 5"));

        //Add your adapter to the sectionAdapter
        SectionedRecyclerViewAdapter.Section[] dummy = new SectionedRecyclerViewAdapter.Section[sections.size()];
        SectionedRecyclerViewAdapter mSectionedAdapter = new
                SectionedRecyclerViewAdapter(getActivity(),R.layout.drawer_header_item,R.id.menu_text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    private void initialise(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DrawerAdapter(getActivity(),values);
    }
}
