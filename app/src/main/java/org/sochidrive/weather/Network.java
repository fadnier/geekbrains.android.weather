package org.sochidrive.weather;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.sochidrive.weather.model.WeatherFiveDayRequest;
import org.sochidrive.weather.model.WeatherRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Network {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String WEATHER_FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String WEATHER_API_KEY = "42d8133e838d012eeede7fba7d32bfde";
    public FragmentWeather fragmentWeather;
    public FragmentWeek fragmentWeek;


    public Network(String city, FragmentWeather fragmentWeather) {
        String url = WEATHER_URL+city+"&appid="+WEATHER_API_KEY;
        String urlForecast = WEATHER_FORECAST_URL+city+"&appid="+WEATHER_API_KEY;
        this.fragmentWeather = fragmentWeather;
        getWeather(url,"weather");
    }

    public Network(String city, FragmentWeek fragmentWeek) {
        String url = WEATHER_URL+city+"&appid="+WEATHER_API_KEY;
        String urlForecast = WEATHER_FORECAST_URL+city+"&appid="+WEATHER_API_KEY;
        this.fragmentWeek = fragmentWeek;
        getWeather(urlForecast,"forecast");
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
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private void displayWeather(String result, String type){
        if(type.equals("weather")) {
            Gson gson = new Gson();
            final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
            fragmentWeather.getData(weatherRequest);
        }
        if(type.equals("forecast")) {
            Gson gson = new Gson();
            final WeatherFiveDayRequest weatherFiveDayRequest = gson.fromJson(result, WeatherFiveDayRequest.class);
            fragmentWeek.getData(weatherFiveDayRequest);
        }
    }

}
