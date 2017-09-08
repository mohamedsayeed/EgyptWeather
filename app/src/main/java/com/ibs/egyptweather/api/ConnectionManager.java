package com.ibs.egyptweather.api;

/**
 * Created by Mohamed Sayed on 9/2/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibs.egyptweather.model.City;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionManager {

    static final String API_KEY = "44ee1a8f1bfa0d60fadfd3ad61a6f781";
    private static ConnectionManager connectionManager;
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private WeatherClient weatherClient;

    private ConnectionManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        weatherClient = retrofit.create(WeatherClient.class);

    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null)
            connectionManager = new ConnectionManager();

        return connectionManager;
    }

    //    public Call<Demo> getCityWeather(String cityIds) {
//        return weatherClient.weatherForCities(cityIds, API_KEY);
//    }
    public Call<City> getCityWeather(String cityIds) {
        return weatherClient.weatherForCities(cityIds, "metric", API_KEY);
    }


}
