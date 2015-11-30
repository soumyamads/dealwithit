package com.snyxius.apps.dealwithit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


/**
 * Created by snyxius on 10/15/2015.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.no_deal_layout, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }

    private void initialise(View view){
        view.findViewById(R.id.create_deal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.create_deal){
            Intent intent = new Intent(getActivity(), CreateDealActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}