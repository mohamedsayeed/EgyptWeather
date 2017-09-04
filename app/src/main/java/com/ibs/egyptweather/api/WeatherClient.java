package com.ibs.egyptweather.api;

import com.ibs.egyptweather.model.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohamed Sayed on 9/2/2017.
 */

public interface WeatherClient {

    @GET("group?")
    Call<City> weatherForCities(@Query("id") String cityIds, @Query("units") String Units, @Query("APPID") String Key);
//    Call<List> weatherForCities(@Query(QUERY) String cityName, @Query(KEY) String Key);
}
