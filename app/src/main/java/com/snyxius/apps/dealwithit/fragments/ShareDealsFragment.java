package com.snyxius.apps.dealwithit.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.ShareAdapter;
import com.snyxius.apps.dealwithit.extras.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by amanjham on 13/01/16.
 */
public class ShareDealsFragment extends Fragment {

    private RecyclerView mRecyclerList;
    GridLayoutManager mLayoutManager;
    private List<ResolveInfo> listApp;
    static  int count= Constants.DEFAULT_INT;
    public static ShareDealsFragment newInstance(int position) {
        count = position;
        Bundle args = new Bundle();

        ShareDealsFragment fragment = new ShareDealsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyler_view_share, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView(view);
    }

    private void initializeRecyclerView(View view){
        if(count == Constants.INT_ONE) {
//            view.findViewById(R.id.down_arrow1).setVisibility(View.GONE);
        }
        mRecyclerList = (RecyclerView) view.findViewById(R.id.recycler_views);
        mRecyclerList.setHasFixedSize(true);
        listApp = showAllShareApp();
        mLayoutManager = new GridLayoutManager(getActivity(), Constants.INT_ONE);
        mRecyclerList.setLayoutManager(mLayoutManager);
        if (listApp != null) {
            mRecyclerList.setAdapter(new ShareAdapter(getActivity(), listApp, new ShareAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, List<ResolveInfo> mTaskList) {
                    share(listApp.get(position));
                }
            }));

        }

    }

    private void share(ResolveInfo appInfo) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        if (appInfo != null) {
            sendIntent
                    .setComponent(new ComponentName(
                            appInfo.activityInfo.packageName,
                            appInfo.activityInfo.name));
        }
        sendIntent.setType("text/plain");
//        startActivity(Intent.createChooser(sendIntent, "Share"));
        startActivity(sendIntent);
    }

    private List<ResolveInfo> showAllShareApp() {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        intent.setType("text/plain");
        PackageManager pManager = getActivity().getPackageManager();
        mApps = pManager.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }
}
