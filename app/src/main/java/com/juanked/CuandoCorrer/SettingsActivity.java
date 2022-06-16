package com.juanked.CuandoCorrer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.CuandoCorrer.R;

import java.util.prefs.Preferences;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_RAIN_SWITCH = "int_rain";
    private EditTextPreference userMaxTemp;
    private EditTextPreference userMinTemp;
    private EditTextPreference userWind;
    private SwitchPreference userRain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        findViewById(R.id.but_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreDefaultSettings();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void restoreDefaultSettings() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.restoreDefaults();
        //TODO reinciar edittext view y switch
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {

        private EditTextPreference userMaxTemp;
        private EditTextPreference userMinTemp;
        private EditTextPreference userWind;
        private SwitchPreference userRain;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
        public void restoreDefaults(){
            userMaxTemp = findPreference("num_maxTemp");
            userMinTemp = findPreference("num_minTemp");
            userWind = findPreference("num_maxWind");
            userRain = findPreference("int_rain");
            userMaxTemp.setText("30");
            userMinTemp.setText("10");
            userWind.setText("20");
            userRain.setChecked(false);
        }
    }
}