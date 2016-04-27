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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.activities.CreateDealActivity;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;

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
public class CreateDealStepTwoFragment extends Fragment  implements View.OnClickListener{


    LinearLayout linearguest,linearbilling;
    RelativeLayout termscondtn;
    RadioGroup radioGroup;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageView tick,galry,arrow;
    StepTwoStroke mCallback;
    PassTandcData passTandcData;
    String uploadPicture = "";
    static JSONObject jsonObject = new JSONObject();
    EditText minimum_guest,cost_person,max_boking,additional,terms_text,minimum_billig,discount_percent;
    TextView deal_image_text,terms_texts;
    public static CreateDealStepTwoFragment newInstance(JSONObject Object) {
        jsonObject = Object;
        CreateDealStepTwoFragment f = new CreateDealStepTwoFragment();

        return f;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (StepTwoStroke)activity;
            passTandcData = (PassTandcData)activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_deal_step_two, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
System.out.println("OOO"+jsonObject);
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
        additional = (EditText)view.findViewById(R.id.additional);
//        terms_text = (EditText)view.findViewById(R.id.terms_text);
        terms_texts= (TextView)view.findViewById(R.id.terms_text);
        termscondtn=(RelativeLayout)view.findViewById(R.id.terms_layout);
        minimum_billig = (EditText)view.findViewById(R.id.mimimum_billig);
        discount_percent = (EditText)view.findViewById(R.id.discount_percent);
        deal_image_text = (TextView)view.findViewById(R.id.deal_image_text);

        arrow = (ImageView)view.findViewById(R.id.right_arrow);
        galry = (ImageView)view.findViewById(R.id.galry);
        tick = (ImageView)view.findViewById(R.id.greentk);

        view.findViewById(R.id.deal_image_layout).setOnClickListener(this);
        view.findViewById(R.id.continue_button).setOnClickListener(this);
        view.findViewById(R.id.min_guests).setOnClickListener(this);
        view.findViewById(R.id.min_billing).setOnClickListener(this);
        view.findViewById(R.id.terms_layout).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                validate();
               // sendBasicData();
                break;
            case R.id.deal_image_layout:
                    selectImage();
                break;

            case R.id.terms_layout:
            case  R.id.terms_text:
                passTandcData.setTandcData(terms_texts.getText().toString());

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
        int nh = (int) ( bitmaps.getHeight() * (256.0 / bitmaps.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmaps, 256, nh, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        uploadPicture = Base64.encodeToString(byteArray, Base64.DEFAULT);
        if(!uploadPicture.equals("")){
            deal_image_text.setText("Image Uploaded");
            arrow.setVisibility(View.GONE);
            galry.setVisibility(View.GONE);
            tick.setVisibility(View.VISIBLE);
        }
    }




    public void validate(){

        String radiovalue = ((RadioButton)getActivity().findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        Log.d("RadioValue",radiovalue);

        if(radiovalue.equals("Minimum Guests")){
//            if(uploadPicture.equals("")){
//                DealWithItApp.showAToast("Please select the Deal Image");
//            }else
        if(minimum_guest.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please select the Minimum Guest");
            }else if(cost_person.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please give the Cost Person");
            } else if(additional.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please select the Additional");
            }else if(terms_texts.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please give the Terms & Condition");
            }
            else{
                sendBasicData(radiovalue);
            }

        }

        if(radiovalue.equals("Minimum Billings")){
//            if(uploadPicture.equals("")){
//                DealWithItApp.showAToast("Please select the Deal Image");
//            }else
            if(minimum_billig.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please select the Minimum Billing");
            }else if(discount_percent.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please give the Discount Percent");
            } else if(additional.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please select the Additional");
            }else if(terms_texts.getText().toString().isEmpty()){
                DealWithItApp.showAToast("Please give the Terms & Condition");
            }
            else{
                sendBasicData(radiovalue);
            }

        }


    }


    private void sendBasicData(String value){
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
            jsonObject.accumulate(Keys.terms_text, terms_texts.getText().toString());

            mCallback.setStepThreeStoke();
            mCallback.sendStepTwoData(jsonObject);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface StepTwoStroke{
        void setStepThreeStoke();
        void sendStepTwoData(JSONObject jsonObject);
    }
    public interface PassTandcData{
        void setTandcData(String string);
    }
    public void changeTandCText(String string){
        try {
//            arrayListBusinessProfile = arrayBusinessName;
//            arrayListBusinessProfileIds = arrayBusinessIds;
            terms_texts.setText(string);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}