package com.juanked.CuandoCorrer.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CuandoCorrer.R;
import com.juanked.CuandoCorrer.helpers.current.WeatherResult;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Integer.parseInt;

public class CurrentWeatherAdapter extends RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder> {

    private WeatherResult currentResult;
    private Context context;
    private String image;
    private String userMaxTemp;
    private String userMinTemp;
    private String userWind;
    private boolean userRain;
    private SharedPreferences prefs;

    public CurrentWeatherAdapter(WeatherResult currentResult, Context context){
        if (this.currentResult == null)
            Log.d("Count forecast","Es nulo");
        this.currentResult = currentResult;
        this.context = context;
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

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        userRain = prefs.getBoolean("int_rain",false);
        userMaxTemp = prefs.getString("num_maxTemp","30");
        int userMTemp = parseInt(userMaxTemp);
        userMinTemp = prefs.getString("num_minTemp","10");
        int userminTemp = parseInt(userMinTemp);
        userWind = prefs.getString("num_maxWind","20");
        int userW = parseInt(userWind);

        if(!userRain && weather.equals("Rain"))
            userRain = false;
        else if (!userRain && !weather.equals("Rain"))
            userRain = true;

        if (userminTemp < temp && temp < userMTemp && wind < userW && userRain){
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
                setText(Translator.translate(weather));
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
