package com.snyxius.apps.dealwithit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 7/4/16.
 */
public class TempAdapter  extends RecyclerView.Adapter<TempAdapter.MyViewHolder> {

    List<AllPojos> mListData;
//    Context context;
    Activity activity;
    final CharSequence[] items = {
            "View", "Delete", "Email Template"
    };
    List<String> mAnimals;

    public TempAdapter(Activity activity,List<AllPojos> mListData) {
        this.mListData = mListData;
        this.activity=activity;
         mAnimals = new ArrayList<String>();
        mAnimals.add("View");
        mAnimals.add("Delete");
        mAnimals.add("Email Template");

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newtemplatelist,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        if(myViewHolder instanceof MyViewHolder ) {
            MyViewHolder MyViewHolder = (MyViewHolder) myViewHolder;
            MyViewHolder.title.setText(mListData.get(i).getTemplate_name());
            MyViewHolder.description.setText(mListData.get(i).getTemplate_Description());
            MyViewHolder.threedots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("Make your selection");
//                    builder.setItems(items, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int item) {
//                            // Do something with the selection
////                            mDoneButton.setText(items[item]);
//                        }
//                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();
                    ShowAlertDialogWithListview();
                }
            });

        }
    }
    public void ShowAlertDialogWithListview()
    {

        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("SELECT YOUR OPTION");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView threedots;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.heading);
            description=(TextView)itemView.findViewById(R.id.content);
            threedots=(ImageView)itemView.findViewById(R.id.dots);



        }
    }
}