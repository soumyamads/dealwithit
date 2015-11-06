package com.snyxius.apps.dealwithit.pojos;

/**
 * Created by snyxius on 11/6/2015.
 */
public class EstablishmentTypePojo {
    String name;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    EstablishmentTypePojo(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }

}
