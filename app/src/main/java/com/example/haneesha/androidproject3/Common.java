package com.example.haneesha.androidproject3;


import com.example.haneesha.androidproject3.Model.Results;
import com.example.haneesha.androidproject3.Remote.IGoogleAPIService;
import com.example.haneesha.androidproject3.Remote.RetrofitClient;

public class Common {

    public static Results currentResult;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleAPIService getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
