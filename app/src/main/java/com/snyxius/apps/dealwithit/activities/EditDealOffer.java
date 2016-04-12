package com.snyxius.apps.dealwithit.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snyxius.apps.dealwithit.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by snyxius on 6/4/16.
 */
public class EditDealOffer extends AppCompatActivity implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    int position = 0;
    TextView opening_hour,closing_hour,startdealdate;

    ImageView imageView1, imageView2;
    LinearLayout linearimage;
    private static final int CAMERA_REQUEST = 1888;
    private String selectedImagePath;
    private static final int SELECT_PICTURE = 1;

    //    Button tkphoto,addgallry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_deal_offer_activity);
        initialize();
    }

    private void initialize() {
        findViewById(R.id.dlstart).setOnClickListener(this);
        findViewById(R.id.opening_time_text).setOnClickListener(this);
        findViewById(R.id.closing_time_text).setOnClickListener(this);
        findViewById(R.id.mimimum_guest).setOnClickListener(this);
        findViewById(R.id.cost_person).setOnClickListener(this);
        findViewById(R.id.update_button).setOnClickListener(this);


//        imageView1 = (ImageView)findViewById(R.id.image1);
//        linearimage=(LinearLayout)findViewById(R.id.linearimage);
//        imageView2 = (ImageView)findViewById(R.id.image2);
        opening_hour = (TextView) findViewById(R.id.opening_time_text);
        closing_hour = (TextView) findViewById(R.id.closing_time_text);
        startdealdate = (TextView) findViewById(R.id.dlstart);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opening_time_text:
                position = 1;
                setTime();
                break;

            case R.id.closing_time_text:
                position = 2;
                setTime();
                break;
            case R.id.dlstart:
                position = 3;
                setDate();
                break;
        }
    }

    private void setDate(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }


    private void setTime(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getSupportFragmentManager(), "TimePicker");

    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {




        try {


            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String input_date= date;
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date dt1=format1.parse(input_date);
            DateFormat format2=new SimpleDateFormat("EEE");
            String finalDay=format2.format(dt1);

            if (position == 3) {
                startdealdate.setText(finalDay+", "+date);
            } else if (position == 4) {
//                enddealdate.setText(finalDay+", "+date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time = hourString+":"+minuteString;
        Log.d("Time", time);
        if(position == 1){
            opening_hour.setText(time);
        }else if(position == 2){
            closing_hour.setText(time);
        }
    }



}
