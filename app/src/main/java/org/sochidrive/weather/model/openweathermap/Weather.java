package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main") public String main;
    @SerializedName("description")  public String description;
    @SerializedName("icon")  public String icon;
}
