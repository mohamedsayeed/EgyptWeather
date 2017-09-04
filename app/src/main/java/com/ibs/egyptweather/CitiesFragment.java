package com.ibs.egyptweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ibs.egyptweather.api.ConnectionManager;
import com.ibs.egyptweather.model.City;
import com.ibs.egyptweather.model.List;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ibs.egyptweather.MainActivity.cityIds;

public class CitiesFragment extends Fragment {

    java.util.List<List> Cities = new ArrayList<>();
    RecyclerView CitiesList;
    CitiesAdapter Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cities_list, container, false);

        CitiesList = (RecyclerView) rootView.findViewById(R.id.Rv_cities);

        getAPIData(cityIds);

        return rootView;
    }


    private void getAPIData(String cityIds) {
        ConnectionManager.getInstance().getCityWeather(cityIds).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                Cities = response.body().getList();
                System.out.println("Cities Size === " + Cities.size());
                initViews();
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
        CitiesList.setLayoutManager(LayoutManager);
        System.out.println("city size ======== " + Cities.size());
        Adapter = new CitiesAdapter(getActivity().getApplicationContext(), Cities);
        CitiesList.setAdapter(Adapter);
    }


}
