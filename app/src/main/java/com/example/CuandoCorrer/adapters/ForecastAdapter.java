package com.example.CuandoCorrer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CuandoCorrer.R;
import com.example.CuandoCorrer.helpers.forecast.ForecastResult;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private ForecastResult forecastResult;
    private Context context;
    private String image;
    private int userTemp = 20;
    private int userWind = 20;
    private boolean userRain = false;
    public ForecastAdapter(ForecastResult forecastResult){
        if (this.forecastResult == null)
            Log.d("Count forecast","Es nulo");
        this.forecastResult = forecastResult;
    }

    public void setForecastResult(ForecastResult forecastResult){
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
        Log.i("forecastResult",temp.toString());
        Double wind = forecastResult.getList().get(position).getWind().getSpeed();
        Log.i("forecastResult",wind.toString());
        String weather = forecastResult.getList().get(position).getWeather().get(0).getMain();
        Log.i("forecastResult",weather);
        int time = forecastResult.getList().get(position).getDt();
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
        image = forecastResult.getList().get(position).getWeather().get(0).getIcon();
        Picasso.get().load("https://openweathermap.org/img/wn/"+image+"@2x.png").
                resize(100,100).into(holder.imWeather);
    }

    @Override
    public int getItemCount() {
        //return forecastResult.getCnt();
        //return 0;

        return  forecastResult == null ? 0 : forecastResult.getCnt();
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
        private ImageView imWeather;
        private View card;
        public ViewHolder (@NonNull View itemView){
            super(itemView);
            temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
            wind = (TextView) itemView.findViewById(R.id.txt_wind);
            weather = (TextView) itemView.findViewById(R.id.txt_weather);
            time = (TextView) itemView.findViewById(R.id.txt_time);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            imWeather = (ImageView) itemView.findViewById(R.id.img_imWeather);
            card = itemView.findViewById(R.id.card_view_item);
        }
    }

    private String toTime(int unix_time, Context context){
        Date date = new Date(unix_time*1000L);
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(context.getApplicationContext());

        return dateFormat.format(date);
    }

    private String toDate(int unix_time, Context context){
        Date date = new Date(unix_time*1000L);
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context.getApplicationContext());

        return dateFormat.format(date);
    }
}
