package com.example.CuandoCorrer;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.CuandoCorrer.helpers.current.WeatherResult;
import com.example.CuandoCorrer.helpers.forecast.ForecastResult;
import com.example.CuandoCorrer.helpers.remote.ApiUtils;
import com.example.CuandoCorrer.helpers.remote.WeatherResponse;

import java.util.List;

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

        findViewById(R.id.botActualizar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentData();
            }
        });
    }

    void getCurrentData() {
        WeatherResponse response = ApiUtils.getWeatherResponse();
        Call<WeatherResult> callCurrent = response.getCurrentWeather(lat, lon, ApiKey, units);
        callCurrent.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.code() == 200) {
                    WeatherResult weatherResult = response.body();
                    assert weatherResult != null;

                    String stringCurrent =
                            "Temperature: " + weatherResult.getMain().getTemp() + "\n" +
                                    "Wind: " + weatherResult.getWind().getSpeed() + "\n" +
                                    "Weather: " + weatherResult.getWeather().get(0).getMain();
                    Log.i("Resultado: ", stringCurrent);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });


        Call<ForecastResult> callForecast = response.getForecast(lat, lon, ApiKey, units);
        callForecast.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {
                if (response.code() == 200) {
                    ForecastResult forecastResult = response.body();
                    assert forecastResult != null;

                    String stringForecast = null;
                    for (int i = 0; i < forecastResult.getCnt(); i++) {
                        stringForecast = stringForecast +
                                "Time: " +
                                forecastResult.getList().get(i).getDtTxt() + "\n" +
                                "Temperature: " +
                                forecastResult.getList().get(i).getMain().getTemp() + "\n" +
                                "Wind: " +
                                forecastResult.getList().get(i).getWind().getSpeed() + "\n" +
                                "Weather: " +
                                forecastResult.getList().get(i).getWeather().get(0).getMain() +
                                "\n";
                    }
                    Log.i("Resultado predicción", stringForecast);
                }

            }

            @Override
            public void onFailure(Call<ForecastResult> call, Throwable t) {
                Log.e("Fallo2", t.getMessage() + "");

            }
        });


    }

}