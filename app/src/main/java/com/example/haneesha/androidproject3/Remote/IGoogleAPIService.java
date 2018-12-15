package com.example.haneesha.androidproject3.Remote;

import com.example.haneesha.androidproject3.Model.MyPlaces;
import com.example.haneesha.androidproject3.Model.PlaceDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);

    @GET
    Call<PlaceDetail> getDetailsPlace(@Url String url);

}