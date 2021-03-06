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
import com.juanked.CuandoCorrer.helpers.forecast.ForecastResult;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Integer.parseInt;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private ForecastResult forecastResult;
    private final Context context;

    public ForecastAdapter(ForecastResult forecastResult, Context context) {
        if (this.forecastResult == null)
            this.forecastResult = forecastResult;
        this.context = context;
    }

    public void setForecastResult(ForecastResult forecastResult) {
        this.forecastResult = forecastResult;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Double temp = forecastResult.getList().get(position).getMain().getTemp();
        Double wind = forecastResult.getList().get(position).getWind().getSpeed();
        String weather = forecastResult.getList().get(position).getWeather().get(0).getMain();
        int time = forecastResult.getList().get(position).getDt();

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

        String tempP = temp + "??C";
        holder.temperature.
                setText(tempP);
        String windP = wind.toString();
        holder.wind.
                setText(windP);
        holder.weather.
                setText(Translator.translate(weather));
        holder.time.setText(toTime(time, holder.card.getContext()));
        holder.date.setText(toDate(time, holder.card.getContext()));
        String image = forecastResult.getList().get(position).getWeather().get(0).getIcon();
        Picasso.get().load("https://openweathermap.org/img/wn/" + image + "@2x.png").
                resize(100, 100).into(holder.imWeather);
    }

    @Override
    public int getItemCount() {
        return forecastResult == null ? 0 : forecastResult.getCnt();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView temperature;
        private final TextView wind;
        private final TextView weather;
        private final TextView time;
        private final TextView date;
        private final ImageView imWeather;
        private final View card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.txt_temperature);
            wind = itemView.findViewById(R.id.txt_wind);
            weather = itemView.findViewById(R.id.txt_weather);
            time = itemView.findViewById(R.id.txt_time);
            date = itemView.findViewById(R.id.txt_date);
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
