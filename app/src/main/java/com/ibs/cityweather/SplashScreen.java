package com.ibs.cityweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ibs.cityweather.api.ConnectionManager;
import com.ibs.cityweather.model.Demo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    public static ArrayList<Demo> Cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAPIData("Cairo");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getAPIData(String cityName) {
        ConnectionManager.getInstance().getCityWeather(cityName).enqueue(new Callback<Demo>() {
            @Override
            public void onResponse(Call<Demo> call, Response<Demo> response) {
                Demo cityW = response.body();
                Cities.add(cityW);
                System.out.println("Cities Size = " + Cities.size());
            }

            @Override
            public void onFailure(Call<Demo> call, Throwable t) {
                System.out.println("Failed on map");

            }
        });
    }
}
