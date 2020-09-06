package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

import org.sochidrive.weather.model.WeatherWeekDataInterface;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherFiveDayRequest implements WeatherWeekDataInterface {
    @SerializedName("city") private City city;
    @SerializedName("list") private List[] list;

    @Override
    public java.util.List<java.util.List<String>> getWeek() {
        
        java.util.List<java.util.List<String>> week = new ArrayList<>();

        for (List value : list) {
            java.util.List<String> day = new ArrayList<>();

            Timestamp timestamp = new Timestamp(value.dt * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());
            String date = dateFormat.format(new Date(timestamp.getTime()));

            day.add(date);
            day.add(String.format(Locale.getDefault(), "%+d", (int) value.main.temp));
            day.add(value.weather[0].description);
            day.add(value.weather[0].icon);
            week.add(day);
        }
        return week;
    }

    public void setList(List[] list) {
        this.list = list;
    }
}
