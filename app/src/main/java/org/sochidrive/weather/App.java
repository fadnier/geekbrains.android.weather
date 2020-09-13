package org.sochidrive.weather;

import android.app.Application;

import androidx.room.Room;

import org.sochidrive.weather.model.dao.EducationDao;
import org.sochidrive.weather.model.EducationDatabase;

public class App extends Application {

    private static App instance;

    private EducationDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        db = Room.databaseBuilder(
                getApplicationContext(),
                EducationDatabase.class,
                "education_database")
                .allowMainThreadQueries()
                .build();
    }

    public EducationDao getEducationDao() {
        return db.getEducationDao();
    }
}
