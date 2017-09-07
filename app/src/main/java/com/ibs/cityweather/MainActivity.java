package com.ibs.cityweather;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ibs.cityweather.api.ConnectionManager;
import com.ibs.cityweather.model.Demo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamed Sayed on 9/3/2017.
 */

public class MainActivity extends AppCompatActivity {

    public static String cityIds = "360630,360995,361058,361055,359792,360502,358619,359796,352733,347497,359280,359173,360829,361320,359783,360761";

    public static ArrayList<Demo> Cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.main);
        getAPIData("Cairo");

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        TabSelector adapter = new TabSelector(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

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

