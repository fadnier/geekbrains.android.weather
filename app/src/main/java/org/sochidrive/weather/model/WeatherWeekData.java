package org.sochidrive.weather.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherWeekData {
    private List<WeekDay> week = new ArrayList<>();

    public WeatherWeekData(List<List<String>> week) {
        for (int i = 0; i < week.size(); i++) {
            this.addDayWeek(week.get(i).get(0), week.get(i).get(1), week.get(i).get(2));
        }
    }

    public List<WeekDay> getWeek() {
        return week;
    }

    public boolean addDayWeek (String date, String degree, String weatherDesc) {
        return week.add(new WeekDay(date,degree,weatherDesc));
    }

    public class WeekDay {
        private String date;
        private String degree;
        private String weatherDesc;

        public WeekDay(String date, String degree, String weatherDesc) {
            this.date = date;
            this.degree = degree;
            this.weatherDesc = weatherDesc;
        }

        public String getDate() {
            return date;
        }

        public String getDegree() {
            return degree;
        }

        public String getWeatherDesc() {
            return weatherDesc;
        }
    }
}
