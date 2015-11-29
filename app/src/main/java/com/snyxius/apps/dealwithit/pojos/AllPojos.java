package com.snyxius.apps.dealwithit.pojos;

import java.util.ArrayList;

/**
 * Created by snyxius on 11/6/2015.
 */
public class AllPojos {
    String name;
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
}
