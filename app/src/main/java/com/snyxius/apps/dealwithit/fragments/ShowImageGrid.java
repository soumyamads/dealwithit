package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by AMAN on 04-10-2015.
 */
public class ShowImageGrid extends Fragment {

        ArrayList<String> imageArray;
        GridView listView;
        private TextView mTitle;
        static ArrayList<String> arrayImages = new ArrayList<>();
       public  static DeletePosition deletePosition;
    ImageAdapter adapter;
        public static   ShowImageGrid newInstance(ArrayList<String > arrayImage) {
            arrayImages = arrayImage;
            Log.d("BasicArray",String.valueOf(arrayImages.size()));
            ShowImageGrid f = new ShowImageGrid();
            return f;
        }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        deletePosition = (DeletePosition) activity;
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.showimagelayout, container, false);
            listView = (GridView) rootView.findViewById(R.id.grid);
            adapter =  new ImageAdapter(getActivity(),arrayImages);
            listView.setAdapter(adapter);

            return rootView;
        }


        private static class ImageAdapter extends BaseAdapter {

            private LayoutInflater inflater;

            private DisplayImageOptions options;

            private ArrayList<String> array;
            private Context mContext;
            ImageAdapter(Context context,ArrayList<String> arrays) {
                inflater = LayoutInflater.from(context);
                mContext = context;
                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.ic_stub)
                        .showImageForEmptyUri(R.drawable.ic_empty)
                        .showImageOnFail(R.drawable.ic_error)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                this.array = arrays;
            }

            @Override
            public int getCount() {
                return array.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final ViewHolder holder;
                View view = convertView;
                if (view == null) {
                    view = inflater.inflate(R.layout.showitemgridimage, parent, false);
                    holder = new ViewHolder();
                    assert view != null;
                    holder.imageView = (ImageView) view.findViewById(R.id.image);
                    holder.cross = (ImageView) view.findViewById(R.id.delete);
                    holder.cross.setVisibility(View.GONE);
                    holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                    view.setTag(holder);
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                            v.setAnimation(shake);
                            holder.cross.setVisibility(View.VISIBLE);
                            return false;
                        }
                    });

                } else {
                    holder = (ViewHolder) view.getTag();
                }

                BitmapDrawable ob = new BitmapDrawable(mContext.getResources(), DealWithItApp.base64ToBitmap(arrayImages.get(position)));
                holder.imageView.setImageDrawable(ob);


                holder.cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePosition.removeItem(position);
                    }
                });
                return view;
            }
        }

        static class ViewHolder {
            ImageView imageView;
            ImageView cross;
            ProgressBar progressBar;
        }



      public  interface DeletePosition{
        void removeItem(int position);
    }

    public void removeItems(int position){

        adapter.notifyDataSetChanged();
    }


    public void addItems(ArrayList<String> arrayList){

        adapter.notifyDataSetChanged();
    }

}
