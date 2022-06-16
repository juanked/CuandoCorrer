package com.juanked.CuandoCorrer.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juanked.CuandoCorrer.R;
import com.juanked.CuandoCorrer.helpers.current.WeatherResult;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Integer.parseInt;

public class CurrentWeatherAdapter extends RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder> {

    private WeatherResult currentResult;
    private final Context context;

    public CurrentWeatherAdapter(WeatherResult currentResult, Context context) {
        this.currentResult = currentResult;
        this.context = context;
    }

    public void setCurrentResult(WeatherResult currentResult) {
        this.currentResult = currentResult;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).
                //inflate(R.layout.item_list, parent, false);
                inflate(R.layout.item_list, parent, false);
        return new ViewHolder(convertView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Double temp = currentResult.getMain().getTemp();
        Double wind = currentResult.getWind().getSpeed();
        String weather = currentResult.getWeather().get(0).getMain();
        int time = currentResult.getDt();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean userRain = prefs.getBoolean("int_rain", false);
        String userMaxTemp = prefs.getString("num_maxTemp", "30");
        int userMaxiTemp = parseInt(userMaxTemp);
        String userMinTemp = prefs.getString("num_minTemp", "10");
        int userMiniTemp = parseInt(userMinTemp);
        String userWind = prefs.getString("num_maxWind", "20");
        int userW = parseInt(userWind);

        boolean rainRun;
        if (!userRain && weather.equals("Rain"))
            rainRun = false;
        else if (!userRain)
            rainRun = true;
        else
            rainRun = true;

        if (userMiniTemp < temp && temp < userMaxiTemp && wind < userW && rainRun) {
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.card.getContext(), R.color.good_temp));
        } else {
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.card.getContext(), R.color.bad_temp));
        }

        String tempP = temp + "ºC";
        holder.temperature.
                setText(tempP);
        String windP = wind.toString();
        holder.wind.
                setText(windP);
        holder.weather.
                setText(Translator.translate(weather));
        holder.time.setText(toTime(time, holder.card.getContext()));
        holder.date.setText(toDate(time, holder.card.getContext()));
        holder.city.setText(currentResult.getName());
        String image = currentResult.getWeather().get(0).getIcon();
        Picasso.get().load("https://openweathermap.org/img/wn/" + image + "@2x.png").
                resize(100, 100).into(holder.imWeather);
    }

    @Override
    public int getItemCount() {
        return currentResult == null ? 0 : currentResult.getWeather().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView temperature;
        private final TextView wind;
        private final TextView weather;
        private final TextView time;
        private final TextView date;
        private final TextView city;
        private final ImageView imWeather;
        private final View card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.txt_temperature);
            wind = itemView.findViewById(R.id.txt_wind);
            weather = itemView.findViewById(R.id.txt_weather);
            time = itemView.findViewById(R.id.txt_time);
            date = itemView.findViewById(R.id.txt_date);
            city = itemView.findViewById(R.id.txt_city);
            imWeather = itemView.findViewById(R.id.img_imWeather);
            card = itemView.findViewById(R.id.card_view_item);
        }
    }

    private String toTime(int unix_time, Context context) {
        Date date = new Date(unix_time * 1000L);
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(context.getApplicationContext());
        return dateFormat.format(date);
    }

    private String toDate(int unix_time, Context context) {
        Date date = new Date(unix_time * 1000L);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context.getApplicationContext());

        return dateFormat.format(date);
    }
}
