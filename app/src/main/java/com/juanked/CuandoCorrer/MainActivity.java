package com.juanked.CuandoCorrer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CuandoCorrer.R;
import com.juanked.CuandoCorrer.adapters.CurrentWeatherAdapter;
import com.juanked.CuandoCorrer.adapters.ForecastAdapter;
import com.juanked.CuandoCorrer.helpers.current.WeatherResult;
import com.juanked.CuandoCorrer.helpers.forecast.ForecastResult;
import com.juanked.CuandoCorrer.helpers.remote.ApiUtils;
import com.juanked.CuandoCorrer.helpers.remote.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private String ApiKey = null;
    private String units = "metric";
    private double latitude;
    private double longitude;
    private String lat=null;
    private String lon=null;
    private ForecastAdapter adapterForecast;
    private CurrentWeatherAdapter adapterCurrent;
    private WeatherResponse response = ApiUtils.getWeatherResponse();
    private ForecastResult forecastResult;
    private WeatherResult currentResult;
    //private ForecastResult forecastResult2;
    private RecyclerView forecastList;
    private RecyclerView currentList;
    private TextView temperatureTV;
    private TextView windTV;
    private TextView weatherTV;
    private TextView timeTV;
    private ImageView imWeatherIM;
    private View currentCard;
    private Location currentLocation;
    //private LocationManagerService locationManager;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiKey = getString(R.string.open_weather_api);
        Log.d("inicio","inicio");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        CharSequence text = getString(R.string.updated);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        findViewById(R.id.but_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("boton", "se ha pulsado el boton");
                getLocation();

                toast.show();
            }
        });

        /*temperatureTV = findViewById(R.id.txt_temperature);
        windTV = findViewById(R.id.txt_wind);
        weatherTV = findViewById(R.id.txt_weather);
        timeTV = findViewById(R.id.txt_time);
        imWeatherIM = findViewById(R.id.img_imWeather);
        currentCard = findViewById(R.id.card_current);*/

        currentList = (RecyclerView) findViewById(R.id.currentRecyclerView);
        forecastList = (RecyclerView) findViewById(R.id.forecastRecyclerView);
        getLocation();
        Log.d("Entrando","Entro en los metodos");
        /*getCurrentData();
        getForecast();*/
        forecastList.setLayoutManager(new LinearLayoutManager(this));
        currentList.setLayoutManager(new LinearLayoutManager(this));

        //getForecast();
        //Log.d("forecast almacenado",forecastResult.toString());
        adapterCurrent = new CurrentWeatherAdapter(currentResult,this);
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
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.about:
                Intent j = new Intent(getApplicationContext(),About.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void getCurrentData() {
        Call<WeatherResult> callCurrent = response.getCurrentWeather(lat, lon, ApiKey, units);
        callCurrent.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.code() == 200) {
                    WeatherResult weatherResult = response.body();
                    assert weatherResult != null;
                    adapterCurrent.setCurrentResult(weatherResult);
/*                    CurrentWeatherAdapter adapterCW =
                            new CurrentWeatherAdapter(weatherResult,MainActivity.this, this);
                    adapterCW.print();*/
                    /*String stringCurrent =
                            "Temperature: " + weatherResult.getMain().getTemp() + "\n" +
                                    "Wind: " + weatherResult.getWind().getSpeed() + "\n" +
                                    "Weather: " + weatherResult.getWeather().get(0).getMain();*/
                    // TODO aparezcan datos en pantalla
/*                    String tempP = weatherResult.getMain().getTemp().toString()+"ºC";
                    temperatureTV.setText(tempP);
                    String windP = weatherResult.getWind().getSpeed().toString();
                    windTV.setText(windP);
                    weatherTV.setText(weatherResult.getWeather().get(0).getMain());
                    timeTV.setText(toDate(weatherResult.getDt(),getApplicationContext()));
                    String image = weatherResult.getWeather().get(0).getIcon();
                    Picasso.get().load("https://openweathermap.org/img/wn/"+image+"@2x.png").
                            resize(100,100).into(imWeatherIM);*/

                   /* current.clear();
                    current.put("temperature",weatherResult.getMain().getTemp().toString());
                    current.put("wind",weatherResult.getWind().getSpeed().toString());*/
                    //Log.i("Resultado: ", stringCurrent);
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
                    /*String stringForecast = null;
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
                    Log.i("Resultado predicción", stringForecast);*/
                    adapterForecast.setForecastResult(forecastResult);
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

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            // here to request the missing permissions, and then overriding
            //public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    lat= String.valueOf(latitude);
                    lon= String.valueOf(longitude);
                    Log.d("latitude",lat);
                    Log.d("longitude",lon);
                    getCurrentData();
                    getForecast();
                }
            }
        });



        /*SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("latitude",latitude);
        editor.putLong("longitude",longitude.longValue());
        editor.apply();*/
    }


}