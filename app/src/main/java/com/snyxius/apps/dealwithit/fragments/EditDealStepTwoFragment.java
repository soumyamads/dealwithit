package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

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
public class EditDealStepTwoFragment extends Fragment  implements View.OnClickListener{


    LinearLayout linearguest,linearbilling;
    RadioGroup radioGroup;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    StepTwoStroke mCallback;
    String uploadPicture = "";

    private static  ArrayList<AllPojos> arrayStepSecond;
    EditText minimum_guest,cost_person,max_boking,additional,terms_text,minimum_billig,discount_percent;
    ImageView cover_image;

    public static EditDealStepTwoFragment newInstance(ArrayList<AllPojos> Object) {
        arrayStepSecond = Object;
        EditDealStepTwoFragment f = new EditDealStepTwoFragment();
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_deal_step_two, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.min_guests) {
                    linearguest.setVisibility(View.VISIBLE);
                    linearbilling.setVisibility(View.GONE);
                } else if (checkedId == R.id.min_billing) {
                    linearguest.setVisibility(View.GONE);
                    linearbilling.setVisibility(View.VISIBLE);
                }
            }


        });

    }

    private void initialise(View view){
        linearbilling=(LinearLayout)view.findViewById(R.id.linearbilling);
        linearguest=(LinearLayout)view.findViewById(R.id.linearguest);
        radioGroup=(RadioGroup)view.findViewById(R.id.myRadioGroup);
        minimum_guest = (EditText)view.findViewById(R.id.mimimum_guest);
        cost_person = (EditText)view.findViewById(R.id.cost_person);
        max_boking = (EditText)view.findViewById(R.id.max_boking);
        max_boking.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getMaximum_Booking());
        additional = (EditText)view.findViewById(R.id.additional);
        additional.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getAdditional());
        terms_text = (EditText)view.findViewById(R.id.terms_text);
        terms_text.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getTerms_Conditions());
        minimum_billig = (EditText)view.findViewById(R.id.mimimum_billig);
        discount_percent = (EditText)view.findViewById(R.id.discount_percent);
        cover_image = (ImageView)view.findViewById(R.id.cover_image);
        view.findViewById(R.id.deal_image_layout).setOnClickListener(this);
        uploadPicture = arrayStepSecond.get(Constants.DEFAULT_INT).getDeal_image();
        BitmapDrawable ob = new BitmapDrawable(getActivity().getResources(), DealWithItApp.base64ToBitmap(arrayStepSecond.get(Constants.DEFAULT_INT).getDeal_image()));
        cover_image.setImageDrawable(ob);
        if(arrayStepSecond.get(Constants.DEFAULT_INT).getChecking().equals("Y")){
            radioGroup.check(R.id.min_guests);
            minimum_guest.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getMinimum_Guest());
            cost_person.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getCost_person());
        }else if(arrayStepSecond.get(Constants.DEFAULT_INT).getChecking().equals("N")){
            radioGroup.check(R.id.min_billing);
            minimum_billig.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getMinimum_Billing());
            discount_percent.setText(arrayStepSecond.get(Constants.DEFAULT_INT).getDiscount_Person());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deal_image_layout:
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
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        Log.d("selectPath", selectedImagePath);

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
    private void sendImages(Bitmap bitmaps){
        cover_image.setImageBitmap(bitmaps);
        int nh = (int) ( bitmaps.getHeight() * (256.0 / bitmaps.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmaps, 256, nh, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        uploadPicture = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }




    public int validate(){

        String radiovalue = ((RadioButton)getActivity().findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        Log.d("RadioValue",radiovalue);

        if(radiovalue.equals("Minimum Guests")){
            if(uploadPicture.equals("")){
                return Constants.INT_ONE;
            }else if(minimum_guest.getText().toString().isEmpty()){
                return Constants.INT_TWO;
            }else if(cost_person.getText().toString().isEmpty()){
                return Constants.INT_THREE;
            } else if(additional.getText().toString().isEmpty()){
                return Constants.INT_FOUR;
            }else if(terms_text.getText().toString().isEmpty()){
                return Constants.INT_FIVE;
            }
            else{
                return Constants.INT_EIGHT;
            }

        }else {
            if(uploadPicture.equals("")){
                return Constants.INT_ONE;

            }else if(minimum_billig.getText().toString().isEmpty()){
                return Constants.INT_SIX;
            }else if(discount_percent.getText().toString().isEmpty()){
                return Constants.INT_SEVEN;
            } else if(additional.getText().toString().isEmpty()){
                return Constants.INT_FOUR;
            }else if(terms_text.getText().toString().isEmpty()){
                return Constants.INT_FIVE;
            }
            else{
                return Constants.INT_NINE;
            }

        }

    }


    public JSONObject sendBasicData(JSONObject jsonObject,String value){
        try{
            jsonObject.accumulate(Keys.deal_image, uploadPicture);
            if(value.equals("Minimum Guests")){
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate(Keys.minimum_guest,minimum_guest.getText().toString());
                jsonobject.accumulate(Keys.cost_person,cost_person.getText().toString());
                jsonArray.put(jsonobject);
                jsonObject.accumulate(Keys.minimum_guest, jsonArray);
                JSONArray jsonArray1 = new JSONArray();
                jsonObject.accumulate(Keys.minimum_billig,jsonArray1);
            }else if (value.equals("Minimum Billings")){

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate(Keys.minimum_billig, minimum_billig.getText().toString());
                jsonobject.accumulate(Keys.discount_percent, discount_percent.getText().toString());
                jsonArray.put(jsonobject);
                jsonObject.accumulate(Keys.minimum_billig, jsonArray);
                JSONArray jsonArray1 = new JSONArray();
                jsonObject.accumulate(Keys.minimum_guest,jsonArray1);
            }

            jsonObject.accumulate(Keys.max_boking, max_boking.getText().toString());
            jsonObject.accumulate(Keys.additional, additional.getText().toString());
            jsonObject.accumulate(Keys.terms_text, terms_text.getText().toString());

           return jsonObject;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public interface StepTwoStroke{
        void setStepThreeStoke();
        void sendStepTwoData(JSONObject jsonObject);
    }
}