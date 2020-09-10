package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lat") public float lat;
    @SerializedName("lon") public float lon;
}
