package org.sochidrive.weather;

import org.sochidrive.weather.model.WeatherData;
import org.sochidrive.weather.model.WeatherWeekData;

import java.io.Serializable;

public class SingletonSave implements Serializable {
    private static Boolean checkBoxWindSpeed = false;
    private static Boolean checkBoxPressure = false;
    private static String city = "Москва";
    private static WeatherData weatherData;
    private static WeatherWeekData weatherWeekData;
    private static SingletonSave instance;

    public static WeatherData getWeatherData() {
        return weatherData;
    }

    public static void setWeatherData(WeatherData weatherData) {
        SingletonSave.weatherData = weatherData;
    }

    public static WeatherWeekData getWeatherWeekData() {
        return weatherWeekData;
    }

    public static void setWeatherWeekData(WeatherWeekData weatherWeekData) {
        SingletonSave.weatherWeekData = weatherWeekData;
    }

    public static Boolean getCheckBoxWindSpeed() {
        return checkBoxWindSpeed;
    }

    public static void setCheckBoxWindSpeed(Boolean checkBoxWindSpeed) {
        SingletonSave.checkBoxWindSpeed = checkBoxWindSpeed;
    }

    public static Boolean getCheckBoxPressure() {
        return checkBoxPressure;
    }

    public static void setCheckBoxPressure(Boolean checkBoxPressure) {
        SingletonSave.checkBoxPressure = checkBoxPressure;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        SingletonSave.city = city;
    }


    private SingletonSave() {}

    public static SingletonSave getInstance() {
        if(instance == null) {
            instance = new SingletonSave();
        }

        return instance;
    }
}
