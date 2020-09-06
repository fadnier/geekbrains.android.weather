package org.sochidrive.weather.model;

public class WeatherData {
    private String cityName;
    private String degree;
    private String weatherDesc;
    private String icon;
    private String pressure;
    private String windSpeed;

    public WeatherData(WeatherDataInterface weatherDataInterface) {
        this.cityName = weatherDataInterface.getCityName();
        this.degree = weatherDataInterface.getDegree();
        this.weatherDesc = weatherDataInterface.getWeatherDesc();
        this.icon = weatherDataInterface.getIcon();
        this.pressure = weatherDataInterface.getPressure();
        this.windSpeed = weatherDataInterface.getWindSpeed();
    }

    public WeatherData(String cityName, String degree, String weatherDesc, String icon, String pressure, String windSpeed) {
        this.cityName = cityName;
        this.degree = degree;
        this.weatherDesc = weatherDesc;
        this.icon = icon;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
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

    public String getIcon() { return icon; }

}
