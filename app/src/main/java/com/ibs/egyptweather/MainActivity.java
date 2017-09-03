package com.ibs.egyptweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ibs.egyptweather.api.ConnectionManager;
import com.ibs.egyptweather.model.Demo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<Demo> Cities = new ArrayList<>();
    RecyclerView CitiesList;
    citiesAdapter Adapter;


    static final String API_KEY = "44ee1a8f1bfa0d60fadfd3ad61a6f781";
    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_list);

        CitiesList = (RecyclerView) findViewById(R.id.Rv_cities);

        addCities();
        getAPIData(names, API_KEY);


    }


    private void getAPIData(ArrayList<String> names, String APPId) {
        for (String CityName : names) {
            ConnectionManager.getInstance().getCityWeather(CityName, APPId).enqueue(new Callback<Demo>() {
                @Override
                public void onResponse(Call<Demo> call, Response<Demo> response) {
                    Demo CityWeather = response.body();
                    Cities.add(CityWeather);
                    System.out.println("Cities size ======== " + Cities.size());
                    initViews();
                }

                @Override
                public void onFailure(Call<Demo> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Oppppsss", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void initViews() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        CitiesList.setLayoutManager(LayoutManager);
        System.out.println("city size ======== " + Cities.size());
        Adapter = new citiesAdapter(getApplicationContext(), Cities);
        CitiesList.setAdapter(Adapter);
    }

    private void addCities() {
        names.add("Cairo,EG");
        names.add("Giza,EG");
        names.add("Alexandria,EG");
        names.add("Ismailia,EG");
        names.add("Aswan,EG");
        names.add("Luxor,EG");
        names.add("PortSaid,EG");
        names.add("Suez,EG");
        names.add("Suhaj,EG");
        names.add("Asyut,EG");
    }
}
