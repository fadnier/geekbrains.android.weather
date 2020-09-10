package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("name") public String name;
    @SerializedName("coord") public Coord coord;
}
