package org.sochidrive.weather;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String isDarkTheme = "IS_DARK_THEME";
    public static final String CITYNAME = "CITY_NAME";
    public static final String DEGREE = "DEGREE_WEATHER";
    public static final String WICON = "WEATHER_ICON";
    public static final String WINDSPEED = "IS_WIND_SPEED";
    public static final String PRESSURE = "IS_PRESSURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    public String getCityPref() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return defaultPref.getString(CITYNAME, "");
    }

    public void setCityPref(String city) {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPref.edit();
        editor.putString(BaseActivity.CITYNAME, city);
        editor.apply();
    }

    public String getDegreePref() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return defaultPref.getString(DEGREE, "0");
    }

    public void setDegreePref(String degree) {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPref.edit();
        editor.putString(BaseActivity.DEGREE, degree);
        editor.apply();
    }

    public String getWeatherIconPref() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return defaultPref.getString(WICON, "01n");
    }

    public void setWeatherIconPref(String icon) {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPref.edit();
        editor.putString(BaseActivity.WICON, icon);
        editor.apply();
    }

    public boolean getWindSpeed() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return defaultPref.getBoolean(WINDSPEED, false);
    }

    public boolean getPressure() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return defaultPref.getBoolean(PRESSURE, false);
    }

    public void setWindSpeed(boolean windSpeed) {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPref.edit();
        editor.putBoolean(BaseActivity.WINDSPEED, windSpeed);
        editor.apply();
    }

    public void setPressure(boolean pressure) {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPref.edit();
        editor.putBoolean(BaseActivity.PRESSURE, pressure);
        editor.apply();
    }

    public boolean isDarkTheme() {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return defaultPref.getBoolean(isDarkTheme,true);
    }

    public void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPref.edit();
        editor.putBoolean(BaseActivity.isDarkTheme, isDarkTheme);
        editor.apply();
    }

}
