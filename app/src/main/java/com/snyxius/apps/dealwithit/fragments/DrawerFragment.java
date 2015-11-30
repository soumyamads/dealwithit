package com.snyxius.apps.dealwithit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.Socket.SocketSingleton;
import com.snyxius.apps.dealwithit.activities.BusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.CreateBusinessProfileActivity;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;
import com.snyxius.apps.dealwithit.activities.DealWithItActivity;
import com.snyxius.apps.dealwithit.activities.DealsActivity;
import com.snyxius.apps.dealwithit.activities.MerchantProfileActivity;
import com.snyxius.apps.dealwithit.activities.SplashActivity;
import com.snyxius.apps.dealwithit.adapters.DrawerAdapter;
import com.snyxius.apps.dealwithit.adapters.SectionedRecyclerViewAdapter;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.utils.RecyclerItemClickListener;

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
            "Business Profile", "Add a Business Profile", "Merchant Profile",
            "Settings","Logout","Payment"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.pBar);
        progressBar.setVisibility(View.GONE);
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
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                switch (position){
                    case 0:intent=new Intent(getActivity(), DealWithItActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;

                    case 2:
                       intent = new Intent(getActivity(), CreateDealActivity.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(getActivity(), DealWithItActivity.class);
                        startActivity(intent);
                        break;


                    case 4:
                        intent = new Intent(getActivity(), DealWithItActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        DealWithItApp.showAToast("Under Construction");
                        break;



                    case 6:
                        intent=new Intent(getActivity(), DealsActivity.class);
                        startActivity(intent);
                        break;


                    case 7:
                        intent = new Intent(getActivity(), DealsActivity.class);
                        startActivity(intent);
                        break;

                    case 8:
                        intent = new Intent(getActivity(), DealsActivity.class);
                        startActivity(intent);
                        break;

                    case 9:
                        DealWithItApp.showAToast("Under Construction");
                        break;

                    case 11:
                        intent=new Intent(getActivity(), BusinessProfileActivity.class);
                        startActivity(intent);
                        break;

                    case 12:
                        intent=new Intent(getActivity(), CreateBusinessProfileActivity.class);
                        startActivity(intent);
                        break;


                    case 13:
                        intent = new Intent(getActivity(), MerchantProfileActivity.class);
                        startActivity(intent);
                        break;

                    case 15:
                        DealWithItApp.showAToast("Under Construction");
                        break;

                    case 16:
                        logout();
                        break;
                    case 17:
                        DealWithItApp.showAToast("Under Construction");
                        break;

                    default:
                        break;
                }
            }
        }));
    }


    private void logout(){
            SocketSingleton.get(getActivity()).getSocket().disconnect();
            DealWithItApp.clearSharedPrefData(getActivity());
            DealWithItApp.saveToPreferences(getActivity(), Keys.intro, true);
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
    }
    private void initialise(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DrawerAdapter(getActivity(),values);
    }
}
