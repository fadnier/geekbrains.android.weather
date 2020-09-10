package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

public class List {
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("wind") public Wind wind;
    @SerializedName("clouds") public Clouds clouds;
    @SerializedName("dt") public Long dt;
}
