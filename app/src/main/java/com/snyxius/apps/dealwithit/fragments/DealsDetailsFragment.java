package com.snyxius.apps.dealwithit.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.snyxius.apps.dealwithit.R;
import com.snyxius.apps.dealwithit.extras.Keys;
import com.snyxius.apps.dealwithit.pojos.AllPojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amanjham on 27/01/16.
 */
public class DealsDetailsFragment extends Fragment {


    TextView minimumguest;
    LinearLayout details;
    TextView timings;
    TextView expDate;
    TextView startDate;

    LinearLayout beforeReserveLayout;
    RelativeLayout afterReserveLayout;
    TextView bookedDate;
    TextView bookedTime;
    TextView costOrDiscountText;
    static String startingDate;
    static String endingdate;
    static String startingTime;
    static String endingTime;
    static String totalAmountText;
TextView minimumBillGuesttext, minimumBillGuestNum,costperson;

    Button reserve;
    FragmentManager fm ;
//    = getSupportFragmentManager();
//    ArrayList<AllPojos> getDealsdetails;
    AppCompatActivity AppAct;
    static JSONObject jobj2 = new JSONObject();

    public static DealsDetailsFragment newInstance(JSONObject Object) {
        jobj2 = Object;
        DealsDetailsFragment f = new DealsDetailsFragment();
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            AppAct = (AppCompatActivity) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPassListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.deals_details_layout,container,false);
//        ButterKnife.inject(this, v);
        return v;

    }

    public static Bundle startBookFragmnt(){

        Bundle dates;
        dates=new Bundle();

        dates.putString("start_date",startingDate);
        dates.putString("end_date",endingdate);
        dates.putString("start_time", startingTime);
        dates.putString("end_time", endingTime);
        dates.putString("total_amount",totalAmountText);
        return dates;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        minimumBillGuestNum=(TextView)view.findViewById(R.id.minimum_bill_guest_num);
        minimumBillGuesttext=(TextView)view.findViewById(R.id.minimum_bill_guest);
        costperson=(TextView)view.findViewById(R.id.cost_person);
        beforeReserveLayout=(LinearLayout)view.findViewById(R.id.before_reserve_layout);
        afterReserveLayout=(RelativeLayout)view.findViewById(R.id.after_reserve_layout);
        startDate=(TextView)view.findViewById(R.id.start_date);
        expDate=(TextView)view.findViewById(R.id.expiry_date);
        timings=(TextView)view.findViewById(R.id.timing);
        costOrDiscountText=(TextView)view.findViewById(R.id.cost_discount_text);



//        initialize(view);

//        if (savedInstanceState != null) {
//            weekdaysDataSource1 = WeekdaysDataSource.restoreState("wds1", savedInstanceState, AppAct, callback1, null);
//            weekdaysDataSource1.setUnselectedColor(Color.parseColor("#CCCCCC"));
//            weekdaysDataSource1.setSelectedColor(Color.parseColor("#a82b99"));
//        }else

//            setupWeekdaysButtons1();

//        weekdaysDataSource1.setVisible(false);
//        reserve=(Button)view.findViewById(R.id.reserve);
//        reserve.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////
////                Book_button_fragment dialogFragment = new Book_button_fragment();
////                dialogFragment.setArguments(startBookFragmnt());
////                dialogFragment.show(getFragmentManager(), "Sample Fragment");
//            }
//        });

//        getDealsdetails=new ArrayList<>();
//        JSONObject jobj2=

try{
//Toast.makeText(getActivity(),"jsn"+jobj2,Toast.LENGTH_LONG).show();
//    if (jobj2.has(Keys.minimum_guest)) {
//        JSONArray jarr1 = jobj2.getJSONArray(Keys.minimum_guest);
//        JSONObject jobj3 = jarr1.getJSONObject(0);
////        pojos.setMinimum_Guest(jobj3.getString(Keys.minimum_guest));
////        pojos.setCost_person(jobj3.getString(Keys.cost_person));
//
//        minimumBillGuestNum.setText(jobj3.getString(Keys.minimum_guest));
////        minimumBillGuesttext.setText("MINIMUM NO OF GUESTS : ");
//        Toast.makeText(getActivity(),"eeee"+minimumBillGuestNum,Toast.LENGTH_LONG).show();
////        costOrDiscountText.setText("COST PER PERSON");
//        totalAmountText = jobj3.getString(Keys.cost_person);
//        costperson.setText("INR " + jobj3.getString(Keys.cost_person) + "/Guest");
//    } else {
////        pojos.setMinimum_Guest("");
////        pojos.setCost_person("");
//    }
//            jobj2 = new JSONObject(DealWithItApp.readFromPreferences(getActivity(), "DEAL_DETAIL", ""));
            if(jobj2.has(Keys.fixed)) {
                beforeReserveLayout.setVisibility(View.VISIBLE);
                afterReserveLayout.setVisibility(View.GONE);
                AllPojos pojos = new AllPojos();
                pojos.setDeal_name(jobj2.getString(Keys.deal_name));
//                pojos.setAddress(jobj2.getString(Keys.address));
//                pojos.setDescription(jobj2.getString(Keys.description));
                pojos.setRecurring(jobj2.getString(Keys.recurring));
                pojos.setFixed(jobj2.getString(Keys.fixed));

                if (jobj2.getJSONArray(Keys.minimum_guest).length()>0) {
                    JSONArray jarr1 = jobj2.getJSONArray(Keys.minimum_guest);
                    System.out.println("guestl" + jarr1);

                    JSONObject jobj3 = jarr1.getJSONObject(0);
                    pojos.setMinimum_Guest(jobj3.getString(Keys.minimum_guest));
                    pojos.setCost_person(jobj3.getString(Keys.cost_person));

                    minimumBillGuestNum.setText(jobj3.getString(Keys.minimum_guest));
                    minimumBillGuesttext.setText("MINIMUM NO OF GUESTS : ");
                    Toast.makeText(getActivity(),"eeee"+minimumBillGuestNum,Toast.LENGTH_LONG).show();
                    costOrDiscountText.setText("COST PER PERSON");
                    totalAmountText = jobj3.getString(Keys.cost_person);
                    costperson.setText("INR " + jobj3.getString(Keys.cost_person) + "/Guest");
                } else {
                    pojos.setMinimum_Guest("");
                    pojos.setCost_person("");
                }

                if (jobj2.getJSONArray(Keys.minimum_billig).length()>0) {
                    JSONArray jarr2 = jobj2.getJSONArray(Keys.minimum_billig);
                    System.out.println("guest" + jarr2);
                    JSONObject jobj3 = jarr2.getJSONObject(0);
                    pojos.setMinimum_Billing(jobj3.getString(Keys.minimum_billig));
                    pojos.setDiscount_Person(jobj3.getString(Keys.discount_percent));
                    totalAmountText = jobj3.getString(Keys.minimum_billig);
                    minimumBillGuestNum.setText("Rs " + jobj3.getString(Keys.minimum_billig));
                    minimumBillGuesttext.setText("MINIMUM BILL AMOUNT : ");
                    costOrDiscountText.setText("PERCENTAGE DISCOUNT");
                    costperson.setText(jobj3.getString(Keys.discount_percent) + "% OFF");

                } else {
                    pojos.setMinimum_Billing("");
                    pojos.setDiscount_Person("");
                }
                startingDate = jobj2.getString(Keys.startdealdate);
                endingdate = jobj2.getString(Keys.enddealdate);
                startingTime = jobj2.getString(Keys.opening_hour);
                endingTime = jobj2.getString(Keys.closing_hour);

                timings.setText(startingTime + " - " + endingTime);
                expDate.setText(endingdate);
                startDate.setText(startingDate);

                if (jobj2.getString(Keys.recurring).equals("yes")) {

//                    weekdaysDataSource1.setVisible(true);

                    if (jobj2.has(Keys.days)) {

                        JSONArray dateArr = jobj2.getJSONArray(Keys.days);
                        ArrayList<String> dateArrays = new ArrayList<>();
                        for (int j = 0; j < dateArr.length(); j++) {
                            dateArrays.add(dateArr.getString(j));
                        }
                        pojos.setDays(dateArrays);
                    }
                }

            }else {
                afterReserveLayout.setVisibility(View.VISIBLE);
                beforeReserveLayout.setVisibility(View.GONE);

                if (jobj2.has(Keys.minimum_guest)) {
                    JSONArray jarr1 = jobj2.getJSONArray(Keys.minimum_guest);
                    if (jarr1.length() > 0) {
                        JSONObject jobj3 = jarr1.getJSONObject(0);

                        minimumBillGuestNum.setText(jobj3.getString(Keys.minimum_guest));
                        minimumBillGuesttext.setText("MINIMUM NO OF GUESTS : ");
                        costOrDiscountText.setText("COST PER PERSON");
                        totalAmountText = jobj3.getString(Keys.cost_person);
                        costperson.setText("INR " + jobj3.getString(Keys.cost_person) + "/Guest");
                    }
                }

//                if (jobj2.has(Keys.minimum_billig)) {
//                    JSONArray jarr2 = jobj2.getJSONArray(Keys.minimum_billig);
//                    if (jarr2.length() > 0) {
//                        JSONObject jobj3 = jarr2.getJSONObject(0);
//                        totalAmountText = jobj3.getString(Keys.minimum_billig);
//                        minimumBillGuestNum.setText("Rs " + jobj3.getString(Keys.minimum_billig));
//                        minimumBillGuesttext.setText("MINIMUM BILL AMOUNT : ");
//                        costOrDiscountText.setText("PERCENTAGE DISCOUNT");
//                        costperson.setText(jobj3.getString(Keys.discount_percent) + "% OFF");
//                    }
//
//                }

//                bookedDate.setText(jobj2.getString(Keys.select_Date));
//                bookedTime.setText(jobj2.getString(Keys.select_Time));
////                bookedId.setText("BOOKING ID: "+jobj2.getString(Keys.BookingID));
                reserve.setText("CALL MERCHANT");
//                reserve.setTag(jobj2.getString(Keys.Contact_no));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }}
//
//
//
//
//
//    }
//
//    private void initialize(View view){
//
//        try {
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public void setupWeekdaysButtons1() {
//        weekdaysDataSource1 = new WeekdaysDataSource(AppAct, R.id.weekdays_stub)
//                .start(callback1);
//        weekdaysDataSource1.setSelectedColor(Color.parseColor("#a82b99"));
//        weekdaysDataSource1.setUnselectedColor(Color.parseColor("#CCCCCC"));
//    }
//
//    private WeekdaysDataSource.Callback callback1 = new WeekdaysDataSource.Callback() {
//        @Override
//        public void onWeekdaysItemClicked(int attachId,WeekdaysDataItem item) {
////            CheckBox checkBox = (CheckBox) getView().findViewById(R.id.check_all_1);
////            if (checkBox != null) checkBox.setChecked(weekdaysDataSource1.isAllDaysSelected());
////            weekdaysDataSource1.isAllDaysSelected();
//
//            Calendar calendar = Calendar.getInstance();
//            int today = calendar.get(Calendar.DAY_OF_WEEK);
//            if(item.getCalendarDayId()==today&&item.isSelected())
//                Toast.makeText(getActivity(), "Carpe diem", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onWeekdaysSelected(int attachId,ArrayList<WeekdaysDataItem> items) {
//            String selectedDays = getSelectedDaysFromWeekdaysData(items);
//            if (!TextUtils.isEmpty(selectedDays))
//                DealWithItApp.showAToast(selectedDays);
//        }
//    };
//
//
//    private String getSelectedDaysFromWeekdaysData(ArrayList<WeekdaysDataItem> items) {
//        StringBuilder stringBuilder = new StringBuilder();
//        boolean selected = false;
//        for (WeekdaysDataItem dataItem : items
//                ) {
//            if (dataItem.isSelected()) {
//                selected = true;
//                stringBuilder.append(dataItem.getLabel());
//                stringBuilder.append(", ");
//            }
//        }
//        if (selected) {
//            String result = stringBuilder.toString();
//            return result.substring(0, result.lastIndexOf(","));
//        } else return "No days selected";
    }




