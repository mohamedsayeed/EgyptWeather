package com.ibs.egyptweather.api;

import com.ibs.egyptweather.model.Demo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohamed Sayed on 9/2/2017.
 */

public interface WeatherClient {

    @GET("weather?")
    Call<Demo> weatherForCities(@Query("q") String cityName,@Query("APPID") String Key);
//    Call<Demo> weatherForCities(@Query(QUERY) String cityName, @Query(KEY) String Key);
}
