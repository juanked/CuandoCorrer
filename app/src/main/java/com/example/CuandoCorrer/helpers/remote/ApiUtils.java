package com.example.CuandoCorrer.helpers.remote;

public class ApiUtils {
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    public static WeatherResponse getWeatherResponse(){
        return RetrofitClient.getClient(BASE_URL).create(WeatherResponse.class);
    }
}
