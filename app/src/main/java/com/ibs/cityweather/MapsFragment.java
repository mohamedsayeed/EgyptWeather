package com.ibs.cityweather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibs.cityweather.model.Demo;

import java.util.Hashtable;
import java.util.Locale;

import static com.ibs.cityweather.R.id.map;
import static com.ibs.cityweather.SplashScreen.Cities;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    static final String API_KEY = "44ee1a8f1bfa0d60fadfd3ad61a6f781";
    View rootView;
    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    private Marker marker;
    private Hashtable<String, String> markers;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_maps, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        return rootView;
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
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Projection projection = mMap.getProjection();
                LatLng markerPosition = marker.getPosition();
                Point markerPoint = projection.toScreenLocation(markerPosition);
                Point targetPoint = new Point(markerPoint.x, markerPoint.y - rootView.getHeight() / 2);
                LatLng targetPosition = projection.fromScreenLocation(targetPoint);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(targetPosition), 1000, null);
                marker.showInfoWindow();
                return true;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()), 1000, null);
            }
        });

        addLatLngs();
    }

    private void addLatLngs() {
        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        Demo cityWeather = Cities.get(0);
        LatLng point = new LatLng(cityWeather.getCoord().getLat(), cityWeather.getCoord().getLon());
        options.position(point);
        options.title(cityWeather.getName());
        options.snippet(String.format(Locale.ENGLISH, "%.0f", cityWeather.getMain().getTemp() - 273.15));
        numTxt.setText(String.format(Locale.ENGLISH, "%.0f", cityWeather.getMain().getTemp() - 273.15));
        options.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker)));
        mMap.addMarker(options);
        LatLng camera = new LatLng(Cities.get(0).getCoord().getLat(), Cities.get(0).getCoord().getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 5.5f));

    }

    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker innerMarker) {

            if (marker != null && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker windowMarker) {
            marker = windowMarker;
            Demo currentCity = Cities.get(0);

            final TextView cityTitle, cityTemp, weatherDescription, maxTemp, minTemp, cityHum, Pressure, windSpeed, windDeg;
            ImageView closePopUp = (ImageView) view.findViewById(R.id.close_popup);
            cityTitle = (TextView) view.findViewById(R.id.cityTitle);
            cityTemp = (TextView) view.findViewById(R.id.cityTemp);
            weatherDescription = (TextView) view.findViewById(R.id.description);
            maxTemp = (TextView) view.findViewById(R.id.maxTemp);
            minTemp = (TextView) view.findViewById(R.id.minTemp);
            cityHum = (TextView) view.findViewById(R.id.cityHum);
            Pressure = (TextView) view.findViewById(R.id.pressure);
            windSpeed = (TextView) view.findViewById(R.id.wind_speed);
            windDeg = (TextView) view.findViewById(R.id.wind_deg);

            cityTitle.setText(currentCity.getName());
            cityTemp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTemp() - 273.15));
            cityHum.setText(String.valueOf(currentCity.getMain().getHumidity()));
            windSpeed.setText(String.valueOf(currentCity.getWind().getSpeed()));
            windDeg.setText(String.valueOf(currentCity.getWind().getDeg()));
            weatherDescription.setText(currentCity.getWeather().get(0).getDescription());
            maxTemp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTempMax() - 273.15));
            minTemp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTempMin() - 273.15));
            Pressure.setText((String.valueOf(currentCity.getMain().getPressure())));

            closePopUp.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    marker.hideInfoWindow();
                    return true;
                }
            });
//            closePopUp.setVisibility(View.GONE);
            return view;
        }
    }

}
