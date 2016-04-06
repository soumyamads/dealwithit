package com.snyxius.apps.dealwithit.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.snyxius.apps.dealwithit.adapters.BusinessProfileAdapter;
import com.snyxius.apps.dealwithit.adapters.EstablishmentTypeAdapter;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;

/**
 * Created by snyxius on 5/4/16.
 */
public class TempFragment  extends DialogFragment implements
        AdapterView.OnItemClickListener {

    String[] listitems = { "item01", "item02", "item03", "item04" };

    ListView mylist;
    Button done;
    ArrayList<AllPojos> estTypeListArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.templatelist, null, false);
        mylist = (ListView) view.findViewById(R.id.list);
        done = (Button) view.findViewById(R.id.button1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, listitems);
//
//
        estTypeListArray = new ArrayList<>();

        AllPojos cp = new AllPojos();
        cp.setProfile_id("1");
        cp.setBusiness_name("template 1");
        estTypeListArray.add(cp);
        AllPojos cp1 = new AllPojos();

        cp1.setProfile_id("1");
        cp1.setBusiness_name("template 2");
        estTypeListArray.add(cp1);
        AllPojos cp2 = new AllPojos();

        cp2.setProfile_id("1");
        cp2.setBusiness_name("template 3");
        estTypeListArray.add(cp2);

        BusinessProfileAdapter adapter=new BusinessProfileAdapter(getContext(),estTypeListArray);
        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        dismiss();
        Toast.makeText(getActivity(), listitems[position], Toast.LENGTH_SHORT)
                .show();
    }

}

