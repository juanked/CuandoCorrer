package com.juanked.CuandoCorrer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.juanked.CuandoCorrer.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.juanked.CuandoCorrer.adapters.CurrentWeatherAdapter;
import com.juanked.CuandoCorrer.adapters.ForecastAdapter;
import com.juanked.CuandoCorrer.helpers.current.WeatherResult;
import com.juanked.CuandoCorrer.helpers.forecast.ForecastResult;
import com.juanked.CuandoCorrer.helpers.remote.ApiUtils;
import com.juanked.CuandoCorrer.helpers.remote.WeatherResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String ApiKey = null;
    private final String units = "metric";
    private double latitude;
    private double longitude;
    private String lat = null;
    private String lon = null;
    private ForecastAdapter adapterForecast;
    private CurrentWeatherAdapter adapterCurrent;
    private final WeatherResponse response = ApiUtils.getWeatherResponse();
    private ForecastResult forecastResult;
    private WeatherResult currentResult;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiKey = getString(R.string.open_weather_api);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        CharSequence text = getString(R.string.updated);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        findViewById(R.id.but_update).setOnClickListener(v -> {
            getLocation();
            toast.show();
        });

        RecyclerView currentList = findViewById(R.id.currentRecyclerView);

        RecyclerView forecastList = findViewById(R.id.forecastRecyclerView);
        getLocation();
        forecastList.setLayoutManager(new LinearLayoutManager(this));
        currentList.setLayoutManager(new LinearLayoutManager(this));
        adapterCurrent = new CurrentWeatherAdapter(currentResult, this);
        currentList.setAdapter(adapterCurrent);
        adapterForecast = new ForecastAdapter(forecastResult, this);
        forecastList.setAdapter(adapterForecast);


    }

    @Override
    protected void onPostResume() {
        getLocation();
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        getLocation();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.about) {
            Intent j = new Intent(getApplicationContext(), About.class);
            startActivity(j);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    void getCurrentData() {
        Call<WeatherResult> callCurrent = response.getCurrentWeather(lat, lon, ApiKey, units);
        callCurrent.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResult> call, @NonNull Response<WeatherResult> response) {
                if (response.code() == 200) {
                    currentResult = response.body();
                    assert currentResult != null;
                    adapterCurrent.setCurrentResult(currentResult);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResult> call, @NonNull Throwable t) {
                Log.e("GetCurrentData failure", t.getMessage() + "");
            }
        });
    }

    public void getForecast() {
        Call<ForecastResult> callForecast = response.getForecast(lat, lon, ApiKey, units);
        callForecast.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResult> call, @NonNull Response<ForecastResult> response) {
                if (response.code() == 200) {
                    forecastResult = response.body();
                    assert forecastResult != null;
                    adapterForecast.setForecastResult(forecastResult);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForecastResult> call, @NonNull Throwable t) {
                Log.e("GetForecast failure", t.getMessage() + "");
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                lat = String.valueOf(latitude);
                lon = String.valueOf(longitude);
                getCurrentData();
                getForecast();
            }
        });
    }


}