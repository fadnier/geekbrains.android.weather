package org.sochidrive.weather;

import org.sochidrive.weather.model.WeatherData;
import org.sochidrive.weather.model.WeatherWeekData;

import java.io.Serializable;

public class SingletonSave implements Serializable {
    private static Boolean checkBoxWindSpeed = false;
    private static Boolean checkBoxPressure = false;
    private static String city;
    private static String degree;
    private static String icon;
    private static WeatherData weatherData;
    private static WeatherWeekData weatherWeekData;
    private static SingletonSave instance;
    private static MainActivity mainActivity;

    public static void setMainActivity(MainActivity mainActivity) {
        SingletonSave.mainActivity = mainActivity;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static String getDegree() {
        return degree;
    }

    public static void setDegree(String degree) {
        SingletonSave.degree = degree;
    }

    public static String getIcon() {
        return icon;
    }

    public static void setIcon(String icon) {
        SingletonSave.icon = icon;
    }

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


    public static SingletonSave getInstance() {
        if(instance == null) {
            instance = new SingletonSave();
        }

        return instance;
    }
}
