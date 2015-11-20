package com.snyxius.apps.dealwithit.extras;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by amanjham on 20/11/15 AD.
 */
public interface IncomingDeals {
    void sendDealsCategoryData(JSONObject jsonObject,JSONArray incomingDealArray);
}
