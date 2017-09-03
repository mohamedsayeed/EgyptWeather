package com.ibs.egyptweather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibs.egyptweather.model.Demo;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mohamed Sayed on 9/2/2017.
 */

public class citiesAdapter extends RecyclerView.Adapter<citiesAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Demo> mCitiesWeather;


    public citiesAdapter(Context context, ArrayList<Demo> citiesWeather) {
        mContext = context;
        mCitiesWeather = citiesWeather;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView cityName, country, temp, hum, press, windSpeed, windDeg;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city);
            country = (TextView) itemView.findViewById(R.id.country);
            temp = (TextView) itemView.findViewById(R.id.temp);
            hum = (TextView) itemView.findViewById(R.id.hum);
            press = (TextView) itemView.findViewById(R.id.press);
            windSpeed = (TextView) itemView.findViewById(R.id.windSpeed);
            windDeg = (TextView) itemView.findViewById(R.id.windDeg);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_row, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Demo currentCity = mCitiesWeather.get(position);
        holder.cityName.setText(currentCity.getName());
        holder.country.setText(currentCity.getSys().getCountry());
        holder.temp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTemp() - 273.15));
        holder.hum.setText(String.valueOf(currentCity.getMain().getHumidity()));
        holder.press.setText(String.valueOf(currentCity.getMain().getPressure()));
        holder.windSpeed.setText(String.valueOf(currentCity.getWind().getSpeed()));
        holder.windDeg.setText(String.valueOf(currentCity.getWind().getDeg()));
    }

    @Override
    public int getItemCount() {
        return mCitiesWeather.size();
    }


}
