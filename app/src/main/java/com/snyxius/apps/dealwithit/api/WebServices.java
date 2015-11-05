package com.snyxius.apps.dealwithit.api;

/**
 * Created by AMAN on 25-10-2015.
 */
public class WebServices {

    public static final String baseUrl = "http://45.55.245.79:1337/";

    public static final String signUp = baseUrl +  "merchants/signUp";

    /*
    Request
    {
        "firstName":"aa",
            "lastName" :"aa",
            "mobile":"1231234",
            "email":"aa@aa.com",
            "establishmentName":"test",
            "password":"aa"
    }*/
/*
Response
          {
            "notice": "SignUp Completed",
            "status": "Success",
            "id": "563bb5e21d76b3ac42d36fd8"
            }
  */

    public static final String signIn = baseUrl  + "merchants/signIn";
    /*
    Request
    {
            "Email":"aa@aa.com",
            "Password":"aa"
    }*/

    /*
    Response
    {
            "status": "Success",
            "id": "563bb5e21d76b3ac42d36fd8",
            "notice": "SignIn Completed"
    }*/

    public static final String forgotPass = baseUrl  + "merchants/forgotPass";
    /*{
        "email":"aa@aa.com"
    }*/

    public static final String saveDeal = baseUrl  + "merchants/saveDeal";

    public static final String saveBuisnessProf = baseUrl  + "merchants/saveBuisnessProf";

    public static final String type = baseUrl  + "establishmenttype/type";

}
