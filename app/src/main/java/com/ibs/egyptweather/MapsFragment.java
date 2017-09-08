package com.ibs.egyptweather;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibs.egyptweather.model.Demo;

import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.ibs.egyptweather.R.id.map;
import static com.ibs.egyptweather.SplashScreen.Cities;


public class MapsFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    View rootView;
    LatLng camera = new LatLng(30.06263, 31.24967);
    private GoogleMap mMap;
    private Marker mMarker;

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

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        mMap = googleMap;
        addLatLngs();
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(camera), 1000, null);
            }
        });

    }

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

    private void addLatLngs() {
        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        for (Demo cityWeather : Cities) {
            numTxt.setText(String.format(Locale.ENGLISH, "%.0f", cityWeather.getMain().getTemp()));
            LatLng point = new LatLng(cityWeather.getCoord().getLat(), cityWeather.getCoord().getLon());
            Marker cityMarker = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker))));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 5f));

    }


    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker innerMarker) {
            return null;
//            return prepareInfoView(innerMarker);
        }

        @Override
        public View getInfoWindow(final Marker windowMarker) {
//            return null;
            return prepareInfoView(windowMarker);
        }

        private View prepareInfoView(Marker marker) {

            for (Demo currentCity : Cities) {
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
                cityTemp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTemp()));
                cityHum.setText(String.valueOf(currentCity.getMain().getHumidity()));
                windSpeed.setText(String.valueOf(currentCity.getWind().getSpeed()));
                windDeg.setText(String.valueOf(currentCity.getWind().getDeg()));
                weatherDescription.setText(currentCity.getWeather().get(0).getDescription());
                maxTemp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTempMax()));
                minTemp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTempMin()));
                Pressure.setText((String.valueOf(currentCity.getMain().getPressure())));

                closePopUp.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mMarker.hideInfoWindow();
                        return true;
                    }
                });

            }
            return view;
        }
    }
}

