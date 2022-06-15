package com.example.CuandoCorrer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.CuandoCorrer.adapters.WeatherAdapter;
import com.example.CuandoCorrer.helpers.current.WeatherResult;
import com.example.CuandoCorrer.helpers.forecast.ForecastResult;
import com.example.CuandoCorrer.helpers.remote.ApiUtils;
import com.example.CuandoCorrer.helpers.remote.WeatherResponse;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String ApiKey = null;
    private String units = "metric";
    private double nlat = 41.670081;
    private double nlon = -3.689978;
    private String lat = String.valueOf(nlat);
    private String lon = String.valueOf(nlon);
    private WeatherAdapter adapter;
    private WeatherResponse response = ApiUtils.getWeatherResponse();
    private ForecastResult forecastResult;
    //private ForecastResult forecastResult2;
    private RecyclerView forecastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiKey = getString(R.string.open_weather_api);

        findViewById(R.id.botActualizar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentData();
                getForecast();
            }
        });
        forecastList = (RecyclerView) findViewById(R.id.forecastRecyclerView);
        getForecast();
        forecastList.setLayoutManager(new LinearLayoutManager(this));
        //getForecast();
        //Log.d("forecast almacenado",forecastResult.toString());

            adapter = new WeatherAdapter(forecastResult);
            forecastList.setAdapter(adapter);



    }

    void getCurrentData() {
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
                    // TODO aparezcan datos en pantalla
                    findViewById(R.id.txt_time)
                   /* current.clear();
                    current.put("temperature",weatherResult.getMain().getTemp().toString());
                    current.put("wind",weatherResult.getWind().getSpeed().toString());*/
                    Log.i("Resultado: ", stringCurrent);
                }
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });
    }

    //public ForecastResult getForecast(){
    public void getForecast(){
        Call<ForecastResult> callForecast = response.getForecast(lat, lon, ApiKey, units);
        callForecast.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {
                if (response.code() == 200) {

                    forecastResult = response.body();
                    Log.d("Tamano forecastResult", forecastResult.getCnt().toString());
                    assert forecastResult != null;
                    //adapter.setForecastResult(forecastResult);
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
                    adapter.setForecastResult(forecastResult);
                    /*try {
                        adapter = new WeatherAdapter(forecastResult);
                        forecastList.setAdapter(adapter);
                        forecastList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    } catch (Exception e ){
                        Log.e("Fallo en try", e.getMessage());
                    }*/

                }
            }

            @Override
            public void onFailure(Call<ForecastResult> call, Throwable t) {
                Log.e("Fallo", t.getMessage() + "");

            }
        });
        //if (forecastResult2 == null)
            //Log.d("Count forecast","Es nulo2222");
        //return forecastResult2;
    }
    /*public void setForecastResult (ForecastResult forecastResult2){
        forecastResult = forecastResult2;
        adapter.notifyDataSetChanged();
    }*/
}