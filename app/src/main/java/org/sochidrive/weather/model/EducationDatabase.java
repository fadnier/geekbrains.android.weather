package org.sochidrive.weather.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.sochidrive.weather.model.dao.EducationDao;
import org.sochidrive.weather.model.sql.Weather;

@Database(entities = {Weather.class}, version = 1, exportSchema = false)
public abstract class EducationDatabase extends RoomDatabase {
    public abstract EducationDao getEducationDao();
}
