package com.ibs.egyptweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ibs.egyptweather.api.ConnectionManager;
import com.ibs.egyptweather.model.City;
import com.ibs.egyptweather.model.Demo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    public static List<Demo> Cities = new ArrayList<>();
    String cityIds = "360630,360995,361058,359792,358619,352733,359280,359783";
    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getAPIData("360630,360995,361058,359792,358619,352733,359280,359783");
        getAPIData(cityIds);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getAPIData(String cityIds) {
        ConnectionManager.getInstance().getCityWeather(cityIds).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                System.out.println("Response");
                City city = response.body();
                List<Demo> list = city.getList();
                Cities = list;
                System.out.println("response list size" + city.getCnt());
                System.out.println("Cities size ==== " + Cities.size());

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }
//    private void getAPIData(ArrayList<String> names) {
//        for (String name : names) {
//            ConnectionManager.getInstance().getCityWeather(name).enqueue(new Callback<Demo>() {
//                @Override
//                public void onResponse(Call<Demo> call, Response<Demo> response) {
//                    System.out.println("Response");
//                    Demo city = response.body();
//                    Cities.add(city);
//                    System.out.println("Cities size ==== " + Cities.size());
//
//                }
//
//                @Override
//                public void onFailure(Call<Demo> call, Throwable t) {
//                    System.out.println("Failed");
//                }
//            });
//        }
//    }
}
