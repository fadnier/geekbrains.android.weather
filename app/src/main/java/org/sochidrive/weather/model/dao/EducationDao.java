package org.sochidrive.weather.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.sochidrive.weather.model.sql.Weather;

import java.util.List;

@Dao
public interface EducationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(Weather weather);

    @Update
    void updateWeather(Weather weather);

    @Delete
    void deleteWeather(Weather weather);

    @Query("DELETE FROM weather WHERE id = :id")
    void deteleWeatherById(long id);

    @Query("SELECT * FROM weather")
    List<Weather> getAllWeather();

    @Query("SELECT * FROM weather WHERE id = :id")
    Weather getWeatherById(long id);

    @Query("SELECT COUNT(*) FROM weather")
    long getCountWeather();
}
