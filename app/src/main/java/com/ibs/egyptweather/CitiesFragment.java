package com.ibs.egyptweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibs.egyptweather.api.ConnectionManager;
import com.ibs.egyptweather.model.Demo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ibs.egyptweather.MainActivity.names;

public class CitiesFragment extends Fragment {

    static final String API_KEY = "44ee1a8f1bfa0d60fadfd3ad61a6f781";
    ArrayList<Demo> Cities = new ArrayList<>();
    RecyclerView CitiesList;
    CitiesAdapter Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cities_list, container, false);

        CitiesList = (RecyclerView) rootView.findViewById(R.id.Rv_cities);

        getAPIData(names, API_KEY);

        return rootView;
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
                }
            });
        }
    }

    private void initViews() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
        CitiesList.setLayoutManager(LayoutManager);
        System.out.println("city size ======== " + Cities.size());
        Adapter = new CitiesAdapter(getActivity().getApplicationContext(), Cities);
        CitiesList.setAdapter(Adapter);
    }


}
