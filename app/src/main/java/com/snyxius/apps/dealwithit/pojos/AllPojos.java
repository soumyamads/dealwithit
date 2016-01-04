package com.snyxius.apps.dealwithit.pojos;

import java.util.ArrayList;

/**
 * Created by snyxius on 11/6/2015.
 */
public class AllPojos {
    String name;
    String dealId;

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    private boolean selected; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    String business_name = "Business_Name";
    String category = "category";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    String location_name = "Location_Name";
    String profile_id = "profileId";
    String cover_image = "Cover_Image";

    String deal_name = "Deal_Name";
    String deal_image = "Deal_Image";
    String recurring = "Recurring";

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public String getDeal_image() {
        return deal_image;
    }

    public void setDeal_image(String deal_image) {
        this.deal_image = deal_image;
    }



    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }



    public String getOpening_hour() {
        return opening_hour;
    }

    public void setOpening_hour(String opening_hour) {
        this.opening_hour = opening_hour;
    }

    public String getClosing_hour() {
        return closing_hour;
    }

    public void setClosing_hour(String closing_hour) {
        this.closing_hour = closing_hour;
    }

    public String getStartdealdate() {
        return startdealdate;
    }

    public void setStartdealdate(String startdealdate) {
        this.startdealdate = startdealdate;
    }

    public String getEnddealdate() {
        return enddealdate;
    }

    public void setEnddealdate(String enddealdate) {
        this.enddealdate = enddealdate;
    }

    String fixed = "Fixed";

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    ArrayList<String> days;
    String opening_hour = "Opening_Hour";
    String closing_hour = "Closing_Hour";
    String startdealdate = "Start_Deal_Date";
    String enddealdate = "End_Deal_date";




    public String address = "Address";
    public String description = "Description";


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    String email="email";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //BusinessDetails
    public String timing_slot_1_start = "Timing_Slot_1_Start";
    public String timing_slot_2_start = "Timing_Slot_2_Start";
    public String timing_slot_1_end = "Timing_Slot_1_End";
    public String timing_slot_2_end = "Timing_Slot_2_End";
    public String max_seating = "Maximum_Seating";

    public String getTiming_slot_1_start() {
        return timing_slot_1_start;
    }

    public void setTiming_slot_1_start(String timing_slot_1_start) {
        this.timing_slot_1_start = timing_slot_1_start;
    }

    public String getTiming_slot_2_start() {
        return timing_slot_2_start;
    }

    public void setTiming_slot_2_start(String timing_slot_2_start) {
        this.timing_slot_2_start = timing_slot_2_start;
    }

    public String getTiming_slot_1_end() {
        return timing_slot_1_end;
    }

    public void setTiming_slot_1_end(String timing_slot_1_end) {
        this.timing_slot_1_end = timing_slot_1_end;
    }

    public String getTiming_slot_2_end() {
        return timing_slot_2_end;
    }

    public void setTiming_slot_2_end(String timing_slot_2_end) {
        this.timing_slot_2_end = timing_slot_2_end;
    }

    public String getMax_seating() {
        return max_seating;
    }

    public void setMax_seating(String max_seating) {
        this.max_seating = max_seating;
    }





    //BuisnessIncomingDeal
    public String deal_offering = "Deal_Offering";
    public String alcohol = "Alcohol";
    public String cost_per_person = "Cost_Per_Person";
    public String max_guest = "Maximum_Guest";

    public String getDeal_offering() {
        return deal_offering;
    }

    public void setDeal_offering(String deal_offering) {
        this.deal_offering = deal_offering;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getCost_per_person() {
        return cost_per_person;
    }

    public void setCost_per_person(String cost_per_person) {
        this.cost_per_person = cost_per_person;
    }

    public String getMax_guest() {
        return max_guest;
    }

    public void setMax_guest(String max_guest) {
        this.max_guest = max_guest;
    }





    //DealStepOne
    public String Deal_Name = "Deal_Name";
    public String Full_Description = "Full_Description";
    public String Quick_Description = "Quick_Description";

    public String getDeal_Name() {
        return Deal_Name;
    }

    public void setDeal_Name(String deal_Name) {
        Deal_Name = deal_Name;
    }

    public String getFull_Description() {
        return Full_Description;
    }

    public void setFull_Description(String full_Description) {
        Full_Description = full_Description;
    }

    public String getQuick_Description() {
        return Quick_Description;
    }

    public void setQuick_Description(String quick_Description) {
        Quick_Description = quick_Description;
    }



    //DealStepSecond
    public String Maximum_Booking = "Maximum_Booking";
    public String Minimum_Guest = "Minimum_Guest";
    public String Cost_person = "Cost_person";

    public String Additional = "Additional";
    public String Terms_Conditions = "Terms_Conditions";
    public String Discount_Person = "Discount_Person";
    public String Minimum_Billing = "Minimum_Billing";
    public String Checking;

    public String getChecking() {
        return Checking;
    }

    public void setChecking(String checking) {
        Checking = checking;
    }

    public String getMaximum_Booking() {
        return Maximum_Booking;
    }

    public void setMaximum_Booking(String maximum_Booking) {
        Maximum_Booking = maximum_Booking;
    }

    public String getMinimum_Guest() {
        return Minimum_Guest;
    }

    public void setMinimum_Guest(String minimum_Guest) {
        Minimum_Guest = minimum_Guest;
    }

    public String getCost_person() {
        return Cost_person;
    }

    public void setCost_person(String cost_person) {
        Cost_person = cost_person;
    }



    public String getAdditional() {
        return Additional;
    }

    public void setAdditional(String additional) {
        Additional = additional;
    }

    public String getTerms_Conditions() {
        return Terms_Conditions;
    }

    public void setTerms_Conditions(String terms_Conditions) {
        Terms_Conditions = terms_Conditions;
    }

    public String getDiscount_Person() {
        return Discount_Person;
    }

    public void setDiscount_Person(String discount_Person) {
        Discount_Person = discount_Person;
    }

    public String getMinimum_Billing() {
        return Minimum_Billing;
    }

    public void setMinimum_Billing(String minimum_Billing) {
        Minimum_Billing = minimum_Billing;
    }

    //DealStepSecond


    public String getFix() {
        return Fix;
    }

    public void setFix(String fix) {
        Fix = fix;
    }

    public String Fix = "Fixed";


    public String Recurring = "Recurring";

    public String getStart_Deal_Date() {
        return Start_Deal_Date;
    }

    public void setStart_Deal_Date(String start_Deal_Date) {
        Start_Deal_Date = start_Deal_Date;
    }

    public String getClosing_Hour() {
        return Closing_Hour;
    }

    public void setClosing_Hour(String closing_Hour) {
        Closing_Hour = closing_Hour;
    }

    public String getOpening_Hour() {
        return Opening_Hour;
    }

    public void setOpening_Hour(String opening_Hour) {
        Opening_Hour = opening_Hour;
    }

    public String getEnd_Deal_date() {
        return End_Deal_date;
    }

    public void setEnd_Deal_date(String end_Deal_date) {
        End_Deal_date = end_Deal_date;
    }

    public String getRecurring() {
        return Recurring;
    }

    public String getRepeat() {
        return Repeat;
    }

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }

    public String Repeat = "Repeat";

    public String End_Deal_date = "End_Deal_date";
    public String Opening_Hour = "Opening_Hour";
    public String Closing_Hour = "Closing_Hour";
    public String Start_Deal_Date = "Start_Deal_Date";



}
