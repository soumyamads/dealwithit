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

    public String getRecurring() {
        return recurring;
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
    public String menu_images = "Menu_Images";

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

    public String getMenu_images() {
        return menu_images;
    }

    public void setMenu_images(String menu_images) {
        this.menu_images = menu_images;
    }


    String firstName="firstName";
    String lastName="lastName";
    String establishmentName="establishmentName";
    String email="email";
    String mobile="mobile";
    String userImage="userImage";

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


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

    public String deal_offering = "Deal_Offering";
    public String alcohol = "Alcohol";
    public String cost_per_person = "Cost_Per_Person";
    public String max_guest = "Maximum_Guest";

}
