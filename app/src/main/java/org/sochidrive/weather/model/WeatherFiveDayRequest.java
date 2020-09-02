package org.sochidrive.weather.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherFiveDayRequest implements WeatherWeekDataInterface {
    private City city;
    private List[] list;

    @Override
    public java.util.List<java.util.List<String>> getWeek() {
        
        java.util.List<java.util.List<String>> week = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            java.util.List<String> day = new ArrayList<>();

            Timestamp timestamp = new Timestamp(list[i].getDt()*1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());
            String date = dateFormat.format(new Date(timestamp.getTime()));

            day.add(date);
            day.add(String.format(Locale.getDefault(),"%+d", (int)(list[i].getMain().getTemp()-273.15f)));
            day.add(list[i].getWeather()[0].getDescription());
            week.add(day);
        }
        return week;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List[] getList() {
        return list;
    }

    public void setList(List[] list) {
        this.list = list;
    }
}
