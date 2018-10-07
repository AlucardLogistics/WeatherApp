package com.logistics.alucard.weatherapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    //http://api.openweathermap.org/data/2.5/weather?q=Chicago&appid=bb72d2c2b6850337b36813263b0d37ee
    private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
