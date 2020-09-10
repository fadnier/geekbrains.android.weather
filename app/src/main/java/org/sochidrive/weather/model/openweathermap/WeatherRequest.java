package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import org.sochidrive.weather.model.WeatherDataInterface;

import java.util.Locale;

public class WeatherRequest implements WeatherDataInterface {
    @SerializedName("coord") private Coord coord;
    @SerializedName("weather") private Weather[] weather;
    @SerializedName("main") private Main main;
    @SerializedName("wind") private Wind wind;
    @SerializedName("clouds") private Clouds clouds;
    @SerializedName("name") private String name;

    @Override
    public String getCityName() {
        return name;
    }

    @Override
    public String getDegree() {
        return String.format(Locale.getDefault(),"%+d", (int)main.temp);
    }

    @Override
    public String getWeatherDesc() {
        return weather[0].description;
    }

    @Override
    public String getIcon() {
        return weather[0].icon;
    }

    @Override
    public String getPressure() {
        return String.format(Locale.getDefault(),"%d hPa", main.pressure);
    }

    @Override
    public String getWindSpeed() {
        return String.format(Locale.getDefault(),"%.1f m/s", wind.speed);
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
