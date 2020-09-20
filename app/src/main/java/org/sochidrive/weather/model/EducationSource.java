package org.sochidrive.weather.model;

import org.sochidrive.weather.model.dao.EducationDao;
import org.sochidrive.weather.model.sql.Weather;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EducationSource {
    private final EducationDao educationDao;

    private List<Weather> weaters;

    public EducationSource(EducationDao educationDao){
        this.educationDao = educationDao;
    }

    public List<Weather> getWeaters(){
        if (weaters == null){
            loadWeathers();
        }
        return weaters;
    }

    public void loadWeathers(){
        weaters = educationDao.getAllWeather();
    }

    public long getCountWeather(){
        return educationDao.getCountWeather();
    }

    public void addWeather(Weather weather){
        Weather weatherSql = educationDao.getLastWeather();

        if(weatherSql != null && weatherSql.dt != null) {
            Timestamp timestamp = new Timestamp(weatherSql.dt);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
            String date = dateFormat.format(new Date(timestamp.getTime()));

            Timestamp timestamp2 = new Timestamp(weather.dt);
            String date2 = dateFormat.format(new Date(timestamp2.getTime()));

            if(!weatherSql.city.equals(weather.city) || !date.equals(date2)) {
                educationDao.insertWeather(weather);
            }
        } else {
            educationDao.insertWeather(weather);
        }

        loadWeathers();
    }

    public void updateWeather(Weather weather){
        educationDao.updateWeather(weather);
        loadWeathers();
    }

    public void removeWeather(long id){
        educationDao.deteleWeatherById(id);
        loadWeathers();
    }

}
