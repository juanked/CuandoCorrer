package com.example.CuandoCorrer;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.CuandoCorrer.helpers.current.WeatherResult;
import com.example.CuandoCorrer.helpers.remote.ApiUtils;
import com.example.CuandoCorrer.helpers.remote.WeatherResponse;

public class MainActivity extends AppCompatActivity {

    private String ApiKey = null;
    private String units = "metric";
    private double nlat = 41.670081;
    private double nlon = -3.689978;
    private String lat = String.valueOf(nlat);
    private String lon = String.valueOf(nlon);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiKey = getString(R.string.open_weather_api);

        findViewById(R.id.botActualizar).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getCurrentData();
            }
        });
    }

    void getCurrentData(){
        WeatherResponse response = ApiUtils.getWeatherResponse();
        Call<WeatherResult> call = response.getCurrentWeather(lat, lon, ApiKey , units);
        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if(response.code() == 200){
                    WeatherResult weatherResponse = response.body();
                    assert weatherResponse != null;

                    String stringBuilder =
                            "Temperature: " + weatherResponse.getMain().getTemp() + "\n" +
                            "Wind: " + weatherResponse.getWind().getSpeed() + "\n" +
                            "Weather: " + weatherResponse.getWeather().get(0).getMain();
                    Log.i("Resultado: ",stringBuilder);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });


    }

}