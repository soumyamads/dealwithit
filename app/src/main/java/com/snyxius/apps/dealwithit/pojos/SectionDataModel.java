package com.snyxius.apps.dealwithit.pojos;

import java.util.ArrayList;

public class SectionDataModel {



    private String headerTitle;
    private ArrayList<VenueModel> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, ArrayList<VenueModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<VenueModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<VenueModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
