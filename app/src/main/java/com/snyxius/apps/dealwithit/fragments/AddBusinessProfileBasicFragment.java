package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.adapters.PlaceAutocompleteAdapter;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by snyxius on 10/15/2015.
 */


public class AddBusinessProfileBasicFragment extends Fragment implements View.OnClickListener,Picker.PickListener, GoogleApiClient.OnConnectionFailedListener {

    TextView  est_type_text;//,ambience_text,cuisine_text;
    static String s;
    EditText est_name,address,description;
    DetailStroke mCallback;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ArrayList<String> arrayImage = new ArrayList<>();
    String uploadPicture = "";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;
    private static String TAG="BasicFragment";
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure that container activity implement the callback interface
        try {
            mCallback = (DetailStroke)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_business_profile_basic, container, false);
        return rootView;
}


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mAutocompleteView = (AutoCompleteTextView) view.findViewById(R.id.address);
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        mAutocompleteView.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.stopAutoManage(getActivity());
        }
        super.onStop();
    }

    private void initialise(View view){
        view.findViewById(R.id.continues).setOnClickListener(this);
        view.findViewById(R.id.category_layout).setOnClickListener(this);
        view.findViewById(R.id.upload_menu_layout).setOnClickListener(this);
        view.findViewById(R.id.cover_image_layout).setOnClickListener(this);
        view.findViewById(R.id.button_clear).setOnClickListener(this);
        est_type_text = (TextView)view.findViewById(R.id.category_text);
        est_name = (EditText)view.findViewById(R.id.est_name);
        address = (EditText)view.findViewById(R.id.location_name);
        description = (EditText)view.findViewById(R.id.description);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continues:
             // validate();
                sendBasicData();
                break;
            case R.id.category_layout:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new CategoryFragment(), Constants.CATEGORY_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_clear:
                mAutocompleteView.setText("");
                break;
            case R.id.upload_menu_layout:
                new Picker.Builder(getContext(),this,R.style.MIP_theme)
                        .setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
                        .setLimit(6)
                        .build()
                        .startActivity();
                break;
            case R.id.cover_image_layout:
                selectImage();
                break;
        }
    }



    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        sendImages(thumbnail);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Log.d("selectPath",selectedImagePath);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        sendImages(bm);


    }



    @Override
    public void onPickedSuccessfully(ArrayList<ImageEntry> images) {

        Log.d("IMAGES", "Picked images  " + images.toString());

        arrayImage = new ArrayList<>();

        for(int i=0;i<images.size();i++){

            Bitmap bitmaps;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(images.get(i).path, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bitmaps = BitmapFactory.decodeFile(images.get(i).path, options);
            int nh = (int) ( bitmaps.getHeight() * (256.0 / bitmaps.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmaps, 256, nh, true);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            arrayImage.add(encoded);
        }



    }


    @Override
    public void onCancel() {
        Log.i("Images", "User canceled picker activity");
        Toast.makeText(getActivity(), "User canceld picker activtiy", Toast.LENGTH_SHORT).show();
    }




    private void sendImages(Bitmap bitmaps){
        int nh = (int) ( bitmaps.getHeight() * (256.0 / bitmaps.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmaps, 256, nh, true);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        uploadPicture = Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public interface DetailStroke{
        void setDetailStoke();
        void sendCategoryData(String string,JSONObject jsonObject);
    }



    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getActivity(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);


            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
//                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
//                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
//                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    public void changeCategoryText(String string){
        try {
            est_type_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void validate(){
        if(est_name.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Establishment Name");
        }else if(est_type_text.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Category");
        }else if(address.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Location Name");
        }
        else if(mAutocompleteView.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Address");
        }else if(description.getText().toString().isEmpty()){
            DealWithItApp.showAToast("Please select the Description");
        }else if(arrayImage.isEmpty()){
            DealWithItApp.showAToast("Please upload menu first");
        }else if(uploadPicture.equals("")){
            DealWithItApp.showAToast("Please cover picture first");
        }
        else{
            sendBasicData();
        }
    }

    private void sendBasicData(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.business_name,est_name.getText().toString());
            jsonObject.accumulate(Keys.category, est_type_text.getText().toString());
            jsonObject.accumulate(Keys.address,mAutocompleteView.getText().toString());
            jsonObject.accumulate(Keys.description,description.getText().toString());
            jsonObject.accumulate(Keys.location_name, address.getText().toString());
            JSONArray array = new JSONArray(arrayImage);
            jsonObject.accumulate(Keys.menu_images,array);
            jsonObject.accumulate(Keys.cover_image,uploadPicture);
            mCallback.setDetailStoke();
            mCallback.sendCategoryData(est_type_text.getText().toString(), jsonObject);
        }catch (Exception e){

        }
    }

}