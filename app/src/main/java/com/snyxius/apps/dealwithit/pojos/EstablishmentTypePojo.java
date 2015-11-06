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
}
