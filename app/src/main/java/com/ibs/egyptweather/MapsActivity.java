package com.ibs.egyptweather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibs.egyptweather.api.ConnectionManager;
import com.ibs.egyptweather.model.Demo;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();

    static final String API_KEY = "44ee1a8f1bfa0d60fadfd3ad61a6f781";
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Demo> Cities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addCities();
        getAPIData(names, API_KEY);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        for (LatLng point : latlngs) {
//            options.position(point);
//            options.title("someTitle");
//            options.snippet("someDesc");
//            mMap.addMarker(options);
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngs.get(0)));
//        }
//        LatLng sydney = new LatLng(30.050556, 31.23683);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void addLatLngs() {
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        for (Demo city : Cities) {
            LatLng point = new LatLng(city.getCoord().getLat(), city.getCoord().getLon());
            options.position(point);
            options.title(city.getName());
            options.snippet(String.format(Locale.ENGLISH, "%.0f", city.getMain().getTemp() - 273.15));
            numTxt.setText(String.format(Locale.ENGLISH, "%.0f", city.getMain().getTemp() - 273.15));
            options.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker)));
            mMap.addMarker(options);
            latlngs.add(point);
        }
        LatLng camera = new LatLng(Cities.get(0).getCoord().getLat(), Cities.get(0).getCoord().getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 5.5f));

    }

    private void getAPIData(ArrayList<String> names, String APPId) {
        for (String CityName : names) {
            ConnectionManager.getInstance().getCityWeather(CityName, APPId).enqueue(new Callback<Demo>() {
                @Override
                public void onResponse(Call<Demo> call, Response<Demo> response) {
                    Demo CityWeather = response.body();
                    Cities.add(CityWeather);
                    System.out.println("Cities size ======== " + Cities.size());
                    addLatLngs();
                }

                @Override
                public void onFailure(Call<Demo> call, Throwable t) {
//                    Toast.makeText(MainActivity.this, "Oppppsss", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private void addCities() {
        names.add("Cairo,EG");
//        names.add("Giza,EG");
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
