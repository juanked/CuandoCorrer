package com.example.CuandoCorrer.helpers;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/weather")
    Observable<Resultado> listPopularTVShows(@Query("api_key") String api_key);

}
