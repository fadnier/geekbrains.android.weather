package org.sochidrive.weather.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherWeekData {
    private List<WeekDay> week = new ArrayList<>();

    public WeatherWeekData(List<List<String>> week) {
        for (int i = 0; i < week.size(); i++) {
            this.addDayWeek(week.get(i).get(0), week.get(i).get(1), week.get(i).get(2), week.get(i).get(3));
        }
    }

    public List<WeekDay> getWeek() {
        return week;
    }

    public boolean addDayWeek (String date, String degree, String weatherDesc, String icon) {
        return week.add(new WeekDay(date,degree,weatherDesc,icon));
    }

    public class WeekDay extends WeatherData {
        private String date;

        public WeekDay(String date, String degree, String weatherDesc, String icon) {
            super(null,degree,weatherDesc,icon,null,null);
            this.date = date;
        }

        public String getDate() {
            return date;
        }
    }
}
