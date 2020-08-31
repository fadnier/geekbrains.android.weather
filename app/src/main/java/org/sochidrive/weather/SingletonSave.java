package org.sochidrive.weather;

import org.sochidrive.weather.model.WeatherFiveDayRequest;
import org.sochidrive.weather.model.WeatherRequest;

import java.io.Serializable;

public class SingletonSave implements Serializable {
    private static Boolean checkBoxWindSpeed = false;
    private static Boolean checkBoxPressure = false;
    private static String city = "Москва";
    private static WeatherRequest weatherRequestCurrent;
    private static WeatherFiveDayRequest weatherFiveDayRequestCurrent;
    private static SingletonSave instance;

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

    public static WeatherRequest getWeatherRequestCurrent() {
        return weatherRequestCurrent;
    }

    public static void setWeatherRequestCurrent(WeatherRequest weatherRequestCurrent) {
        SingletonSave.weatherRequestCurrent = weatherRequestCurrent;
    }

    public static WeatherFiveDayRequest getWeatherFiveDayRequestCurrent() {
        return weatherFiveDayRequestCurrent;
    }

    public static void setWeatherFiveDayRequestCurrent(WeatherFiveDayRequest weatherFiveDayRequestCurrent) {
        SingletonSave.weatherFiveDayRequestCurrent = weatherFiveDayRequestCurrent;
    }

    private SingletonSave() {}

    public static SingletonSave getInstance() {
        if(instance == null) {
            instance = new SingletonSave();
        }

        return instance;
    }
}
