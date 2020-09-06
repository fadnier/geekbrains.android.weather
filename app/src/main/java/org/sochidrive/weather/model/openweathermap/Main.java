package org.sochidrive.weather.model.openweathermap;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp") public float temp;
    @SerializedName("pressure") public int pressure;
    @SerializedName("humidity") public int humidity;
}
