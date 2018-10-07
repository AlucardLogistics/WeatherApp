package com.logistics.alucard.weatherapp;

import com.logistics.alucard.weatherapp.models.weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    //http://api.openweathermap.org/data/2.5/forecast?q=Chicago&units=imperial&cnt=40&appid=bb72d2c2b6850337b36813263b0d37ee
    @GET("forecast")
    Call<Weather> getWeather(@Query("q") String city,
                             @Query("units") String imperial,
                             @Query("cnt") String count,
                             @Query("appid") String apiKey);


}
