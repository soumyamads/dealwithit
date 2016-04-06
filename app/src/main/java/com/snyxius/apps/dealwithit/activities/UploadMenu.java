package com.snyxius.apps.dealwithit.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.applications.DealWithItApp;
import com.snyxius.apps.dealwithit.extras.Constants;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.fragments.CategoryFragment;

import net.yazeed44.imagepicker.model.ImageEntry;
import net.yazeed44.imagepicker.util.Picker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snyxius on 30/3/16.
 */
public class UploadMenu extends  AppCompatActivity implements View.OnClickListener {

    ImageView imageView1, imageView2;
    LinearLayout linearimage;
    private static final int CAMERA_REQUEST = 1888;
    private String selectedImagePath;
    private static final int SELECT_PICTURE = 1;

    //    Button tkphoto,addgallry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadmenu);
        initialize();
    }

    private void initialize() {
        findViewById(R.id.tkphoto).setOnClickListener(this);
        findViewById(R.id.addgallery).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.wrong).setOnClickListener(this);

        imageView1 = (ImageView) findViewById(R.id.image1);
        linearimage = (LinearLayout) findViewById(R.id.linearimage);
//        imageView2 = (ImageView)findViewById(R.id.image2);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tkphoto:
                takeImageFromCamera(v);
                break;
            case R.id.addgallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                break;

            case R.id.wrong:
//                Intent i=new Intent(this, AddBusinessProfileBasicFragment.class);
//                startActivity(i);
                finish();
                break;
            case R.id.right:
//                Intent intent1=new Intent(this, AddBusinessProfileBasicFragment.class);
//                startActivity(intent1);
                finish();
                break;
        }
    }

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
//           if(imageView1.getResources()!=null){
            linearimage.setVisibility(View.VISIBLE);
            imageView1.setImageBitmap(mphoto);


        }


        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                imageView1.setImageURI(selectedImageUri);
            }
        }
//        else {
//            imageView1.setImageBitmap(mphoto);
//
//        }}
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}