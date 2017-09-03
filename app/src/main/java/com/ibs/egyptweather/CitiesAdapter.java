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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Sayed on 9/2/2017.
 */

class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Demo> mCitiesWeather;


    public CitiesAdapter(Context context, ArrayList<Demo> citiesWeather) {
        mContext = context;
        mCitiesWeather = citiesWeather;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_row, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Demo currentCity = mCitiesWeather.get(position);
        holder.cityName.setText(currentCity.getName());
        holder.temp.setText(String.format(Locale.ENGLISH, "%.0f", currentCity.getMain().getTemp() - 273.15));
        holder.hum.setText(String.valueOf(currentCity.getMain().getHumidity()));
        holder.windSpeed.setText(String.valueOf(currentCity.getWind().getSpeed()));
        holder.windDeg.setText(String.valueOf(currentCity.getWind().getDeg()));
    }

    @Override
    public int getItemCount() {
        return mCitiesWeather.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.city)
        public TextView cityName;
        @BindView(R.id.temp)
        TextView temp;
        @BindView(R.id.hum)
        TextView hum;
        @BindView(R.id.windSpeed)
        TextView windSpeed;
        @BindView(R.id.windDeg)
        TextView windDeg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
