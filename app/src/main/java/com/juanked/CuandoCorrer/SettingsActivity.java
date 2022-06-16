package com.juanked.CuandoCorrer;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.juanked.CuandoCorrer.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        findViewById(R.id.but_reset).setOnClickListener(v -> restoreDefaultSettings());

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
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }

        public void restoreDefaults() {
            EditTextPreference userMaxTemp = findPreference("num_maxTemp");
            EditTextPreference userMinTemp = findPreference("num_minTemp");
            EditTextPreference userWind = findPreference("num_maxWind");
            SwitchPreference userRain = findPreference("int_rain");
            assert userMaxTemp != null;
            userMaxTemp.setText("30");
            assert userMinTemp != null;
            userMinTemp.setText("10");
            assert userWind != null;
            userWind.setText("20");
            assert userRain != null;
            userRain.setChecked(false);
        }
    }
}