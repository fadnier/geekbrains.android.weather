package org.sochidrive.weather.model;

import org.sochidrive.weather.model.openweathermap.WeatherFiveDayRequest;
import org.sochidrive.weather.model.openweathermap.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String city,
                                     @Query("appid") String keyApi,
                                     @Query("units") String units,
                                    @Query("lang") String country);

    @GET("data/2.5/forecast")
    Call<WeatherFiveDayRequest> loadForecast(@Query("q") String city,
                                            @Query("appid") String keyApi,
                                            @Query("units") String units,
                                            @Query("lang") String country);
}
