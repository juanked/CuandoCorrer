package com.example.CuandoCorrer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
/*    // inside of an Activity, `getString` is called directly
    String secretValue = getString(R.string.parse_application_id);
    // inside of another class (requires a context object to exist)
    String secretValue = context.getString(R.string.parse_application_id);*/

    private String weatherAPI = getString(R.string.open_weather_api);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}