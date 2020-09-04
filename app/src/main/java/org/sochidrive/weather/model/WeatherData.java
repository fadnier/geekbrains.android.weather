package org.sochidrive.weather.model;

public class WeatherData {
    private String cityName;
    private String degree;
    private String weatherDesc;

    public WeatherData(WeatherDataInterface weatherDataInterface) {
        this.cityName = weatherDataInterface.getCityName();
        this.degree = weatherDataInterface.getDegree();
        this.weatherDesc = weatherDataInterface.getWeatherDesc();
    }

    public String getCityName() {
        return cityName;
    }

    public String getDegree() {
        return degree;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

}
