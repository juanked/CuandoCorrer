package com.example.CuandoCorrer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CuandoCorrer.R;
import com.example.CuandoCorrer.helpers.current.WeatherResult;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentWeatherAdapter extends RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder> {

    private WeatherResult currentResult;
    private Context context;
    private String image;
    private int userTemp = 20;
    private int userWind = 20;
    private boolean userRain = false;
    public CurrentWeatherAdapter(WeatherResult currentResult){
        if (this.currentResult == null)
            Log.d("Count forecast","Es nulo");
        this.currentResult = currentResult;
    }

    public void setCurrentResult(WeatherResult currentResult){
        this.currentResult = currentResult;
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
        Log.d("Ciudad",currentResult.getName());
        Double temp = currentResult.getMain().getTemp();
        Log.i("currentResult",temp.toString());
        Double wind = currentResult.getWind().getSpeed();
        Log.i("currentResult",wind.toString());
        String weather = currentResult.getWeather().get(0).getMain();
        Log.i("currentResult",weather);
        int time = currentResult.getDt();
        if (temp > userTemp && wind < userWind && (weather.equals("Rain") == userRain)){
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.card.getContext(),R.color.good_temp));
        } else {
            holder.card.setBackgroundColor(ContextCompat.getColor( holder.card.getContext(),R.color.bad_temp));
        }
        String tempP = temp.toString()+"ºC";
        holder.temperature.
                setText(tempP);
        String windP = wind.toString();
        holder.wind.
                setText(windP);
        holder.weather.
                setText(weather);
        //String timeP = String.valueOf(time);
        holder.time.setText(toTime(time,holder.card.getContext()));
        holder.date.setText(toDate(time,holder.card.getContext()));
        holder.city.setText(currentResult.getName());
        image = currentResult.getWeather().get(0).getIcon();
        Picasso.get().load("https://openweathermap.org/img/wn/"+image+"@2x.png").
                resize(100,100).into(holder.imWeather);
    }

    @Override
    public int getItemCount() {
        return  currentResult == null ? 0 : currentResult.getWeather().size();
    }

    /*public View getView (int position, View convertView, ViewGroup parent){
        ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list,null);
            vh = new ViewHolder();
            vh.temperature = convertView.findViewById(R.id.txt_temperature);
            vh.wind = convertView.findViewById(R.id.txt_wind);
            vh.weather = convertView.findViewById(R.id.txt_weather);
            vh.imWeather = convertView.findViewById(R.id.img_imWeather);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        ForecastResult forecastResult = forecastResultList.get(position);

        return convertView;
    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView temperature;
        private TextView wind;
        private TextView weather;
        private TextView time;
        private TextView date;
        private TextView city;
        private ImageView imWeather;
        private View card;
        public ViewHolder (@NonNull View itemView){
            super(itemView);
            temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
            wind = (TextView) itemView.findViewById(R.id.txt_wind);
            weather = (TextView) itemView.findViewById(R.id.txt_weather);
            time = (TextView) itemView.findViewById(R.id.txt_time);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            city = (TextView) itemView.findViewById(R.id.txt_city);
            imWeather = (ImageView) itemView.findViewById(R.id.img_imWeather);
            card = itemView.findViewById(R.id.card_view_item);
        }
    }

    private String toTime(int unix_time, Context context){
        Date date = new Date(unix_time*1000L);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(context.getApplicationContext());
        Log.d("time",dateFormat.format(date));
        return dateFormat.format(date);
    }
    private String toDate(int unix_time, Context context){
        Date date = new Date(unix_time*1000L);
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context.getApplicationContext());

        return dateFormat.format(date);
    }
}
