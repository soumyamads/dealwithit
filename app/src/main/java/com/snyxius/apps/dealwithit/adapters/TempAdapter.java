package com.snyxius.apps.dealwithit.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.SuccessDialogFragment;
import com.snyxius.apps.dealwithit.fragments.ViewTemplateFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snyxius on 7/4/16.
 */
public class TempAdapter  extends RecyclerView.Adapter<TempAdapter.MyViewHolder> {

    List<AllPojos> mListData;
//    Context context;
AppCompatActivity activity;
    final CharSequence[] items = {
            "View", "Delete", "Email Template"
    };
    List<String> mAnimals;

    public TempAdapter(AppCompatActivity activity,List<AllPojos> mListData) {
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
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
//        if(myViewHolder instanceof MyViewHolder ) {
            MyViewHolder MyViewHolder = (MyViewHolder) myViewHolder;
            MyViewHolder.title.setText(mListData.get(i).getTemplate_name());
            MyViewHolder.description.setText(mListData.get(i).getTemplate_Description());
            MyViewHolder.threedots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
////                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
////                    builder.setTitle("Make your selection");
////                    builder.setItems(items, new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int item) {
////                            // Do something with the selection
//////                            mDoneButton.setText(items[item]);
////                        }
////                    });
////                    AlertDialog alert = builder.create();
////                    alert.show();
                    DealWithItApp.showAToast("clicked");
                    ShowAlertDialogWithListview(i);
                }
            });

//        }
    }
    public void ShowAlertDialogWithListview(final int pos)
    {

        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("SELECT YOUR OPTION");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview

                if (item == 0) {
//                    FragmentManager fragmentManager = activity.getFragmentManager();
                    DialogFragment dialogFrag = ViewTemplateFragment.newInstance(mListData.get(pos).getTemplate_name(),mListData.get(pos).getTemplate_Description());
                    dialogFrag.setCancelable(true);
                    dialogFrag.show(activity.getSupportFragmentManager().beginTransaction(), Constants.SUCCESSDIALOG_FRAGMENT);
                } else if (item == 1) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setCancelable(true);
                    builder.setTitle("Delete");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // How to remove the selected item?
                            deletelist(pos);
                        }

                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });

                    AlertDialog alert = builder.create();
                    alert.show();
//                    profilesAdapter.notifyDataSetChanged();


                } else if (item == 2) {
email(pos);
                }
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

    private void email(int pos){
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
//        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{  "serveroverloadofficial@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mListData.get(pos).getTemplate_name());
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mListData.get(pos).getTemplate_Description());


        emailIntent.setType("message/rfc822");

        try {
           activity.startActivity(Intent.createChooser(emailIntent,
                   "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }




    }
    private void  deletelist(int pos) {
        try {
            JSONObject jsonObject = new JSONObject();
//            String id = DealWithItApp.readFromPreferences(activity, Keys., Constants.DEFAULT_STRING);
            jsonObject.accumulate(Keys.tempId, mListData.get(pos).getTemplate_Id());
            mListData.remove(pos);
            notifyDataSetChanged();


            if (DealWithItApp.isNetworkAvailable()) {
                new deletetemplate().execute(jsonObject.toString());
            } else {
                DealWithItApp.showAToast("No internet Connection");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class deletetemplate extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(activity);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setIndeterminateDrawable(activity.getDrawable(R.drawable.progress));
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                jsonObject = WebRequest.postData(params[0], WebServices.deleteTemplate);
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
//                    JSONObject obj = jsonObject.getJSONObject(Keys.notice);
//                    DealWithItApp.saveToPreferences(activity, Keys.Templatedeleted, jsonObject.getString(Keys.Templatedeleted));

//                    JSONArray jArray = obj.getJSONArray(Keys.Templatedeleted);
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));


//                    estTypeListArray = new ArrayList<>();
//                    if (jArray != null) {
//                        for (int i = 0; i < jArray.length(); i++) {
//                            JSONObject jsonObject1 = jArray.getJSONObject(i);
//                            AllPojos cp = new AllPojos();
//                            cp.setTemplate_Id(jsonObject1.getString(Keys.Id));
//                            cp.setTemplate_name(jsonObject1.getString(Keys.templatename));
//                            cp.setTemplate_Description(jsonObject1.getString(Keys.description_template));
//
//
//                            estTypeListArray.add(cp);
//                        }
//                    }
//                    adapter = new TempAdapter(this,estTypeListArray);
//                    mRecyclerView.setAdapter(adapter);
//                    adapter = new BusinessProfileListAdapter(getApplicationContext(), estTypeListArray);
//                    mRecyclerView.setAdapter(adapter);
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {

                    Handler mHandler = new Handler((Handler.Callback) activity);
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
                    Handler mHandler = new Handler((Handler.Callback) activity);
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
            }else{
                Handler mHandler = new Handler((Handler.Callback) activity);
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