package org.sochidrive.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import androidx.annotation.NonNull;

import org.sochidrive.weather.fragment.FragmentWeather;
import org.sochidrive.weather.fragment.FragmentWeek;
import org.sochidrive.weather.model.EducationSource;
import org.sochidrive.weather.model.dao.EducationDao;
import org.sochidrive.weather.model.OpenWeatherRepo;
import org.sochidrive.weather.model.WeatherData;
import org.sochidrive.weather.model.openweathermap.WeatherFiveDayRequest;
import org.sochidrive.weather.model.openweathermap.WeatherRequest;
import org.sochidrive.weather.model.WeatherWeekData;
import org.sochidrive.weather.model.sql.Weather;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Network {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_API_KEY = "6b844402e46cd60a2bfa8a12ab5204b6";
    private FragmentWeek fragmentWeek;
    private FragmentWeather fragmentWeather;
    private Activity activitySel;
    private EducationSource educationSource;



    public Network(String city, FragmentWeek fragmentWeek) {
        if(!city.equals("")) {
            SingletonSave.setCity(city);
            this.fragmentWeek = fragmentWeek;
            this.activitySel = fragmentWeek.getActivity();
            updateForecastData(city);
        }
    }

    public Network(String city, FragmentWeather fragmentWeather) {
        if(!city.equals("")) {
            SingletonSave.setCity(city);
            this.fragmentWeather = fragmentWeather;
            this.activitySel = fragmentWeather.getActivity();
            updateWeatherData(city);
        }
    }

    private void updateForecastData(final String city) {
        OpenWeatherRepo.getInstance().getAPI().loadForecast(city+",ru",WEATHER_API_KEY,"metric","ru").enqueue(new Callback<WeatherFiveDayRequest>() {
            @Override
            public void onResponse(@NonNull Call<WeatherFiveDayRequest> call, @NonNull Response<WeatherFiveDayRequest> response) {
                if (response.body() != null && response.isSuccessful()) {
                    fragmentWeek.getData(new WeatherWeekData(response.body().getWeek()));
                } else {
                    if(response.code() == 500) {
                        showErrorConn();
                    } else if(response.code() == 401) {
                        showErrorConn();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<WeatherFiveDayRequest> call, @NonNull Throwable t) {
                showErrorConn();
            }
        });
    }

    private void updateWeatherData(final String city) {
        OpenWeatherRepo.getInstance().getAPI().loadWeather(city+",ru",WEATHER_API_KEY,"metric","ru").enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(@NonNull Call<WeatherRequest> call, @NonNull Response<WeatherRequest> response) {
                if (response.body() != null && response.isSuccessful()) {
                    fragmentWeather.getData(new WeatherData(response.body()));
                    EducationDao educationDao = App.getInstance().getEducationDao();
                    educationSource = new EducationSource(educationDao);
                    educationSource.addWeather(getWeatherModel(response.body()));
                } else {
                    if(response.code() == 500) {
                        showErrorConn();
                    } else if(response.code() == 401) {
                        showErrorConn();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<WeatherRequest> call, @NonNull Throwable t) {
                showErrorConn();
            }
        });
    }

    private Weather getWeatherModel(WeatherRequest weatherRequest) {
        Weather weather = new Weather();
        weather.city = weatherRequest.getCityName();
        weather.icon = weatherRequest.getIcon();
        weather.temp = weatherRequest.getDegree();
        weather.dt = System.currentTimeMillis();
        return weather;
    }

    private void showErrorConn() {
        activitySel.runOnUiThread(() -> new AlertDialog.Builder(activitySel)
                .setTitle(R.string.error_answer)
                .setNeutralButton(R.string.ok, (dialogInterface, i) -> {

                })
                .setMessage(R.string.error_answer_text).show());
        Log.e(TAG, "Fail connection");
    }

}
