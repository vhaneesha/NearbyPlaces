package com.example.haneesha.androidproject3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.haneesha.androidproject3.Model.PlaceDetail;
import com.example.haneesha.androidproject3.Remote.IGoogleAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlace extends AppCompatActivity {

    RatingBar ratingBar;
    TextView opening_hours, place_address, place_name;
    Button btnViewOnMap;
    IGoogleAPIService mService;

    PlaceDetail myPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);

        mService = Common.getGoogleAPIService();
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        place_address = (TextView)findViewById(R.id.place_address);
        place_name = (TextView)findViewById(R.id.place_name);
        opening_hours = (TextView)findViewById(R.id.place_open_hour);
        btnViewOnMap = (Button)findViewById(R.id.btn_show_map);

        //Empty all view
        place_name.setText("");
        place_address.setText("");
        opening_hours.setText("");

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });
        //rating
        if(Common.currentResult.getRating() != null && !TextUtils.isEmpty(Common.currentResult.getRating())){
            ratingBar.setRating(Float.parseFloat(Common.currentResult.getRating()));
        }
        else{
            ratingBar.setVisibility(View.GONE);
        }

        //opening hours

        if(Common.currentResult.getOpening_hours() != null ){

            if(Common.currentResult.getOpening_hours().getOpen_now().equals("true")) {
                //opening_hours.setText("Open now: " + Common.currentResult.getOpening_hours().getOpen_now());
                opening_hours.setText("Store Hours: Open");
            }
            else{
                opening_hours.setText("Store hours: Closed");
            }
        }
        else{
            opening_hours .setVisibility(View.GONE);
        }


        //Address and name
        mService.getDetailsPlace(getPlaceDetailUrl(Common.currentResult.getPlace_id()))
        .enqueue(new Callback<PlaceDetail>() {
            @Override
            public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {

                myPlace = response.body();
                place_address.setText(myPlace.getResult().getFormatted_address());
                place_name.setText(myPlace.getResult().getName());

            }

            @Override
            public void onFailure(Call<PlaceDetail> call, Throwable t) {

            }
        });


    }


    private String getPlaceDetailUrl(String place_id){
       StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
       url.append("?placeid="+place_id);
       url.append(("&key="+getResources().getString(R.string.browser_key)));
       return url.toString();
    }

}
