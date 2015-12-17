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
    /*
    Request
    {
        "email":"aa@aa.com"
    }*/

    public static final String category = baseUrl  + "establishmenttype/category";

    public static final String typeDetails = baseUrl  + "establishmenttype/typeDetails";

    /*
     Request
    {
     "type":
                [
                      "Restaurant",
                       "Halls"
                ]
}
*/

    public static final String cuisine = baseUrl  + "establishmenttype/cuisine";

    public static final String ambiance = baseUrl  + "establishmenttype/ambiance";

    public static final String allBuisnessProf = baseUrl  + "merchants/allBuisnessProf";
   /*
   Request
   {
        "userId":"563bcf4c1d76b3ac42d36fd9",
            "offset":"0",
            "limit":"2"
    }
*/

 /*
  Response
  {
        "notice":{
        "allProfiles":[{
            "Business_Name":"Sample",
                    "Cover_Image":"/FFFABRRRQAUUUUAFFFFAB\nRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH/9k=\n",
                    "Location_Name":"Domlur Bangalore",
                    "category":"Restaurant",
                    "profileId":"SampleDomlur BangaloreRestaurant"
        }
        ]
    }
    }*/


    public static final String tempBuisnessProf = baseUrl  + "merchants/tempBuisnessProf";

    public static final String saveDeal = baseUrl  + "merchants/saveDeal";

    public static final String saveBuisnessProf = baseUrl  + "merchants/saveBuisnessProf";

    public static final String getDeals = baseUrl  + "merchants/getDeals";

    public static final String deleteBuisnessProf = baseUrl  + "merchants/deleteBuisnessProf";

    public static final String deleteDeal = baseUrl  + "merchants/deleteDeal";

    public static final String getOneBuisnessProf = baseUrl  + "merchants/getOneBuisnessProf";

    public static final String getOneDeal = baseUrl  + "merchants/getOneDeal";

    public static final String getUserProfile = baseUrl  + "merchants/getUserProfile";

    /*{  "notice":
        {    "profile":
            {      "firstName": "aaa",
                    "lastName": "aaa",
                    "establishmentName": "aa",
                    "email": "a@b.com",
                    "mobile": "1234231232",
                    "userImage": null
            }
        },
        "status": "Success"
    }*/

    public static final String updateUserProfile = baseUrl  + "merchants/updateUserProfile";

    /*{
            "userId":"563bcf4c1d76b3ac42d36fd9",
            "firstName": "aaa",
            "lastName": "aaa",
            "establishmentName": "Airtel",
            "email": "a@b.com",
            "mobile": "1234231232"
    }*/

    public static final String updateBuisnessProf = baseUrl  + "merchants/updateBuisnessProf";

    public static final String updateDeal = baseUrl  + "merchants/updateDeal";


    /*
    Request
    {
        "userId":"563bcf4c1d76b3ac42d36fd9",
            "offset":"0",
            "limit":"2"
    }
     */


    //For Socket

}

