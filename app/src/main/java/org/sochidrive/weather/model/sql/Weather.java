package org.sochidrive.weather.model.sql;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id", "dt", "city", "temp", "icon"})})
public class Weather {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "dt")
    public Long dt;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "temp")
    public String temp;

    @ColumnInfo(name = "icon")
    public String icon;
}
