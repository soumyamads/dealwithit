package com.snyxius.apps.dealwithit.pojos;

/**
 * Created by snyxius on 11/6/2015.
 */
public class EstablishmentTypePojo {
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
}
