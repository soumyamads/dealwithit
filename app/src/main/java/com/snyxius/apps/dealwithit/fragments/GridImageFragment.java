package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;

import java.util.ArrayList;

/**
 * Created by AMAN on 04-10-2015.
 */
public class GridImageFragment extends Fragment {

        ArrayList<String> imageArray;
        GridView listView;
        private TextView mTitle;
        static ArrayList<String> arrayImages = new ArrayList<>();
       public  static DeletePosition deletePosition;
    ImageAdapter adapter;
        public static GridImageFragment newInstance(ArrayList<String > arrayImage) {
            arrayImages = arrayImage;
            Log.d("BasicArray",String.valueOf(arrayImages.size()));
            GridImageFragment f = new GridImageFragment();
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
                   // Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                    //view.setAnimation(shake);
                    holder = new ViewHolder();
                    assert view != null;
                    holder.imageView = (ImageView) view.findViewById(R.id.image);
                    holder.cross = (ImageView) view.findViewById(R.id.delete);
                    holder.cross.setVisibility(View.VISIBLE);
                    holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                    view.setTag(holder);

                } else {
                    holder = (ViewHolder) view.getTag();
                }

                BitmapDrawable ob = new BitmapDrawable(mContext.getResources(), DealWithItApp.base64ToBitmap(arrayImages.get(position)));
                holder.imageView.setImageDrawable(ob);

                holder.cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePosition.removeGridItem(position);
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
        void removeGridItem(int position);
    }

    public void removeItems(int position){

        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
    }


    public void addItems(ArrayList<String> arrayList){

        adapter.notifyDataSetChanged();
    }



}
