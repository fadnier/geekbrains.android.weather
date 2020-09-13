package org.sochidrive.weather.model;

import org.sochidrive.weather.model.dao.EducationDao;
import org.sochidrive.weather.model.sql.Weather;

import java.util.List;

public class EducationSource {
    private final EducationDao educationDao;

    private List<Weather> weaters;

    public EducationSource(EducationDao educationDao){
        this.educationDao = educationDao;
    }

    public List<Weather> getWeaters(){
        if (weaters == null){
            LoadWeathers();
        }
        return weaters;
    }

    public void LoadWeathers(){
        weaters = educationDao.getAllWeather();
    }

    public long getCountWeather(){
        return educationDao.getCountWeather();
    }

    public void addWeather(Weather weather){
        educationDao.insertWeather(weather);
        LoadWeathers();
    }

    public void updateStudent(Weather weather){

        educationDao.updateWeather(weather);
        LoadWeathers();
    }

    public void removeStudent(long id){
        educationDao.deteleWeatherById(id);
        LoadWeathers();
    }

}
