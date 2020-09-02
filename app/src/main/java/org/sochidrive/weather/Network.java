package org.sochidrive.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.sochidrive.weather.fragment.FragmentWeather;
import org.sochidrive.weather.fragment.FragmentWeek;
import org.sochidrive.weather.model.WeatherData;
import org.sochidrive.weather.model.WeatherFiveDayRequest;
import org.sochidrive.weather.model.WeatherRequest;
import org.sochidrive.weather.model.WeatherWeekData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Network {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String WEATHER_FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String WEATHER_API_KEY = "6b844402e46cd60a2bfa8a12ab5204b6";
    private FragmentWeek fragmentWeek;
    private FragmentWeather fragmentWeather;
    private Activity activitySel;


    public Network(String city, FragmentWeek fragmentWeek) {
        SingletonSave.setCity(city);
        String urlForecast = WEATHER_FORECAST_URL+city+"&appid="+WEATHER_API_KEY;
        this.fragmentWeek = fragmentWeek;
        this.activitySel = fragmentWeek.getActivity();
        getWeather(urlForecast,"forecast");
    }

    public Network(String city, FragmentWeather fragmentWeather) {
        SingletonSave.setCity(city);
        String url = WEATHER_URL+city+"&appid="+WEATHER_API_KEY;
        this.fragmentWeather = fragmentWeather;
        this.activitySel = fragmentWeather.getActivity();
        getWeather(url,"weather");
    }

    public void getWeather(String url, final String type) {
        try {
            final URL uri = new URL(url);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        final String result = getLines(in);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayWeather(result,type);
                            }
                        });
                    } catch (Exception e) {
                        activitySel.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(activitySel)
                                        .setTitle(R.string.error_answer)
                                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .setMessage(R.string.error_answer_text).show();
                            }
                        });
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            activitySel.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(activitySel)
                            .setTitle(R.string.error_url)
                            .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setMessage(R.string.error_url_text).show();
                }
            });
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        StringBuilder rawData = new StringBuilder(1024);
        String tempVariable;

        while (true) {
            try {
                tempVariable = in.readLine();
                if(tempVariable == null) break;
                rawData.append(tempVariable).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawData.toString();
    }

    private void displayWeather(String result, String type){
        if(type.equals("weather")) {
            Gson gson = new Gson();
            final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

            fragmentWeather.getData(new WeatherData(weatherRequest));
        }
        if(type.equals("forecast")) {
            Gson gson = new Gson();
            final WeatherFiveDayRequest weatherFiveDayRequest = gson.fromJson(result, WeatherFiveDayRequest.class);

            fragmentWeek.getData(new WeatherWeekData(weatherFiveDayRequest.getWeek()));
        }
    }

}
