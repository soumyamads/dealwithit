package com.snyxius.apps.dealwithit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.api.WebRequest;
import com.snyxius.apps.dealwithit.api.WebServices;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.DrawerFragment;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snyxius on 11/12/2015.
 */
public class MerchantProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;

    private LinearLayout mTitleContainer;
    private RelativeLayout mImageLayout;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private ImageView mImageparallax;
    private FrameLayout mFrameParallax;
    private CoordinatorLayout cordLayout;
    private Toolbar mToolbar;
    ProgressDialog pDialog;
    EditText userFirstName,userLastName,userNumber,userEmail,userEstName;
    Button editButton;
    CircleImageView proImage;
    String uploadPicture = "";
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
//    ImageView proImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_profile_layout);

        bindActivity();



        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        initParallaxValues();
        initParallaxValues();
        initDrawer();


        mAppBarLayout.addOnOffsetChangedListener(this);

        if (DealWithItApp.isNetworkAvailable()) {
            new getProfile().execute(WebServices.getUserProfile);
        }else{

        }
    }

    private void initDrawer() {
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_drawer,new DrawerFragment().newInstance(drawerLayout), Constants.DRAWER_FRAGMENT)
                .commit();
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerToggle.syncState();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerToggle.syncState();
                supportInvalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



    private void bindActivity() {
        mToolbar        = (Toolbar) findViewById(R.id.toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.appbar);
        mImageparallax  = (ImageView) findViewById(R.id.backdrop);
        mFrameParallax  = (FrameLayout) findViewById(R.id.main_framelayout_title);
        mImageLayout=(RelativeLayout)findViewById(R.id.imageLayout);
        cordLayout=(CoordinatorLayout)findViewById(R.id.main_content);

        userFirstName=(EditText)findViewById(R.id.user_firstname);
        userLastName=(EditText)findViewById(R.id.user_lastname);
        userEmail=(EditText)findViewById(R.id.user_email);
        userNumber=(EditText)findViewById(R.id.user_number);
        userEstName=(EditText)findViewById(R.id.user_designation);

        editButton=(Button)findViewById(R.id.edit_profile);
        editButton.setOnClickListener(this);

        proImage=(CircleImageView)findViewById(R.id.proImage);
        proImage.setOnClickListener(this);

        mToolbar.setTitle("");
//        proImage=(ImageView)findViewById(R.id.pro_image);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setIcon(null);
    }

    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mImageparallax.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mImageparallax.setLayoutParams(petDetailsLp);
        mFrameParallax.setLayoutParams(petBackgroundLp);

        pDialog=new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_profile:
                validate();
                break;
            case R.id.proImage:
                selectImage();
                break;
        }
    }


    public void validate(){
        userFirstName.clearFocus();
        userLastName.clearFocus();
        userEstName.clearFocus();
        userEmail.clearFocus();
        userNumber.clearFocus();

        if (userFirstName.getText().toString().isEmpty()) {
            userFirstName.setError("Field Required");
            userFirstName.requestFocus();
        }else if(userLastName.getText().toString().isEmpty()){
            userLastName.setError("Field Required");
            userLastName.requestFocus();
        }else if(userEstName.getText().toString().isEmpty()){
            userEstName.setError("Field Required");
            userEstName.requestFocus();
        }else if(userEmail.getText().toString().isEmpty()){
            userEmail.setError("Field Required");
            userEmail.requestFocus();
        }else if(userNumber.getText().toString().isEmpty()){
            userNumber.setError("Field Required");
            userNumber.requestFocus();
        }else{
            submit();
        }
    }

    public void submit(){
        try {
            JSONObject jobj = new JSONObject();
            jobj.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id,""));
            jobj.accumulate(Keys.firstName,userFirstName.getText().toString());
            jobj.accumulate(Keys.lastName,userLastName.getText().toString());
            jobj.accumulate(Keys.establishmentName,userEstName.getText().toString());
            jobj.accumulate(Keys.email,userEmail.getText().toString());
            jobj.accumulate(Keys.mobile,userNumber.getText().toString());
            jobj.accumulate(Keys.userImage,uploadPicture);
            if (DealWithItApp.isNetworkAvailable()) {
                new setProfile().execute(jobj.toString());
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
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
        proImage.setImageBitmap(bitmaps);
        int nh = (int) ( bitmaps.getHeight() * (256.0 / bitmaps.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmaps, 256, nh, true);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        uploadPicture = Base64.encodeToString(byteArray, Base64.DEFAULT);

    }



    private class getProfile extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cordLayout.setVisibility(View.GONE);
            pDialog.setMessage("Loading..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {
                JSONObject jobj = new JSONObject();
                jobj.accumulate(Keys.id,DealWithItApp.readFromPreferences(getApplicationContext(), Keys.id,""));
                return WebRequest.postData(String.valueOf(jobj), params[0]);
            }catch (Exception e){

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            cordLayout.setVisibility(View.VISIBLE);
            onDone(jsonObject);
        }
    }


    private void onDone(JSONObject jsonObject){
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    JSONObject object = jsonObject.getJSONObject(Keys.notice);
                    JSONObject object2 = object.getJSONObject(Keys.profile);
                    if (object2 != null) {

                            userFirstName.setText(object2.getString(Keys.firstName));
                            userLastName.setText(object2.getString(Keys.lastName));
                            userNumber.setText(object2.getString(Keys.mobile));
                            userEmail.setText(object2.getString(Keys.email));
                            userEstName.setText(object2.getString(Keys.establishmentName));
//                            mTitle.setText(object2.getString(Keys.firstName));
                        if(!object2.getString(Keys.userImage).equals(""))
                            proImage.setImageBitmap(DealWithItApp.base64ToBitmap(object2.getString(Keys.userImage)));
                        else
                            proImage.setImageResource(R.drawable.null_circle_image);
                    }
                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                        DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private class setProfile extends AsyncTask<String, Void, JSONObject> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Updating..");
            pDialog.setCancelable(false);
            pDialog.show();
            cordLayout.setVisibility(View.GONE);

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObject = null;
            try {

                return WebRequest.postData(params[0],WebServices.updateUserProfile);
            }catch (Exception e){

                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            cordLayout.setVisibility(View.VISIBLE);
            onDone2(jsonObject);
        }
    }

    private void onDone2(JSONObject jsonObject){
        try {
            if(jsonObject != null) {
                if (jsonObject.getString(Keys.status).equals(Constants.SUCCESS)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));

                } else if (jsonObject.getString(Keys.status).equals(Constants.FAILED)) {
                    DealWithItApp.showAToast(jsonObject.getString(Keys.notice));
                } else {
                    DealWithItApp.showAToast("Something Went Wrong.");
                }
            }else{
                DealWithItApp.showAToast("Something Went Wrong.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }


    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                scaleView(mImageLayout, View.INVISIBLE);
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                scaleView(mImageLayout, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {

        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public void scaleView(View v, int visibility) {
        AlphaAnimation anim = (visibility == View.VISIBLE)
        ? new AlphaAnimation(0,1) : new AlphaAnimation(1,0);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(700);
        anim.setInterpolator(new OvershootInterpolator());
//        fab.startAnimation(anim);
//        anim.setFillAfter(true); // Needed to keep the result of the animation
        v.startAnimation(anim);
    }
}
