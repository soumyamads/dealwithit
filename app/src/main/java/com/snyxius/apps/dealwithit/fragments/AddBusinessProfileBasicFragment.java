package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;
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


public class AddBusinessProfileBasicFragment extends Fragment implements View.OnClickListener,Picker.PickListener {

     TextView  est_type_text;//,ambience_text,cuisine_text;
    static String s;
    DetailStroke mCallback;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

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
    }

    private void initialise(View view){
        view.findViewById(R.id.continues).setOnClickListener(this);
        view.findViewById(R.id.est_type).setOnClickListener(this);
        view.findViewById(R.id.upload_menu_layout).setOnClickListener(this);
        view.findViewById(R.id.cover_image_layout).setOnClickListener(this);
        est_type_text = (TextView)view.findViewById(R.id.est_type_text);
//        ambience_text = (TextView)view.findViewById(R.id.ambience_text);
//        cuisine_text = (TextView)view.findViewById(R.id.cuisine_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continues:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frmaecontainer,new AddBusinessProfileDetailFragment(),Constants.ADDBUSINESSPROFILEDETAIL_FRAGMENT)
                        .addToBackStack(Constants.ADDBUSINESSPROFILEBASIC_FRAGMENT)
                        .commit();
                mCallback.setDetailStoke();
                break;
            case R.id.est_type:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
                        .add(R.id.container, new EstablishmentTypeFragment(), Constants.ESTABLISHMENTTYPE_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;
//            case R.id.cuisine_layout:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
//                        .add(R.id.container, new CuisineTypeFragment(), Constants.CUISINE_FRAGMENT)
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            case R.id.ambience_layout:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.push_up_in, R.anim.push_down_out, R.anim.push_up_in, R.anim.push_down_out)
//                        .add(R.id.container, new AmbienceTypeFragment(), Constants.AMBINENCE_FRAGMENT)
//                        .addToBackStack(null)
//                        .commit();
//
//                break;
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

    public void changeEstablishmentText(String string,ArrayList<String> arrayList){
        try {
            est_type_text.setText(string);
            JSONArray jsonArray = new JSONArray(arrayList);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(Keys.type, jsonArray);
            Log.v("array", jsonObject.toString());
            DealWithItApp.saveToPreferences(getActivity(), Keys.establishmentDetail, jsonObject.toString());
        }catch (Exception e){
            e.printStackTrace();
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



    }



    @Override
    public void onPickedSuccessfully(ArrayList<ImageEntry> images) {
        Log.d("IMAGES", "Picked images  " + images.toString());
    }


    @Override
    public void onCancel() {
        Log.i("Images", "User canceled picker activity");
        Toast.makeText(getActivity(), "User canceld picker activtiy", Toast.LENGTH_SHORT).show();
    }



    public void changeAmbienceText(String string,ArrayList<String> arrayList){
        try {
//            ambience_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void changeCuisineText(String string,ArrayList<String> arrayList){
        try {
//            cuisine_text.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public interface DetailStroke{
        void setDetailStoke();
    }


}