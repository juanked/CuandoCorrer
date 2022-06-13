package com.example.CuandoCorrer.helpers.remote;

import com.example.CuandoCorrer.helpers.current.WeatherResult;
import com.example.CuandoCorrer.helpers.forecast.ForecastResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherResponse {

    // String Apikey = Resources.getSystem().getString(R.string.open_weather_api);

    @GET("weather?")
    Call<WeatherResult> getCurrentWeather(@Query("lat") String lat,
                                          @Query("lon") String lon,
                                          @Query("appid") String ApiKey,
                                          @Query("units") String units);

    @GET("forecast?")
    Call<ForecastResult> getForecast(@Query("lat") String lat,
                                           @Query("lon") String lon,
                                           @Query("appid") String ApiKey,
                                           @Query("units") String units);

}
