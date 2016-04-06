package com.snyxius.apps.dealwithit.pojos;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class VenueModel {


    private String name;
    private String url;
    private String description;


    public VenueModel() {
    }

    public VenueModel(String name, String url) {
        this.name = name;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
