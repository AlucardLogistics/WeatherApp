package com.logistics.alucard.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.logistics.alucard.weatherapp.models.weather.ListItem;
import com.logistics.alucard.weatherapp.models.weather.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnDataPass {
    private static final String TAG = "MainActivity";

    private final String WEATHER_API_KEY = "bb72d2c2b6850337b36813263b0d37ee";

    TextView tvDate, tvTemp, tvDesc, tvHumidity;
    ImageView ivWeatherIcon;
    Button btCalendar, btPayPal;
    List<ListItem> myWeather = new ArrayList<>();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCalendar = findViewById(R.id.btCalendar);
        btPayPal = findViewById(R.id.btPayPal);
        tvDate = findViewById(R.id.tvDate);
        tvTemp = findViewById(R.id.tvTemperature);
        tvDesc = findViewById(R.id.tvDesc);
        tvHumidity = findViewById(R.id.tvHumidity);
        ivWeatherIcon = findViewById(R.id.ivIcon);

        tvTemp.setText("Temp: --");
        tvDesc.setText("Weather: --");
        tvHumidity.setText("Humidity: --");

        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.calendarFragment, new CalendarFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });



//        final int currentTime = (int) (new Date().getTime()/1000);
//        Calendar calendar = Calendar.getInstance();
//        Date today = calendar.getTime();
//        Log.d(TAG, "onCreate: today: " + today);
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
//        Date tomorrow = calendar.getTime();
//        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(tomorrow);
//        Log.d(TAG, "onCreate: tomorrow as string: " + date);

        btPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
        
    }


    @Override //format date for API search
    public void onDataPass(String data, String date) {
        Log.d(TAG, "onDataPass: started");
        tvDate.setText(data);
        String weatherDate = date + " 12:00:00";
        Log.d(TAG, "onCreate: weather date: " + weatherDate);
        //call retrofit functions
        getWeather(weatherDate);
    }

    //retrofit to get the desired weather day
    private void getWeather(final String date) {
        pd = new ProgressDialog(this);
        pd.setTitle("Weather");
        pd.setMessage("Getting weather data.");
        pd.show();

        ApiService apiService = RetrofitInstance.getRetrofitInstance()
                .create(ApiService.class);

        final Call<Weather> weatherCall =
                apiService.getWeather("Chicago", "imperial", "40", WEATHER_API_KEY);
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                //Log.d(TAG, "onResponse: weatherData: " + response.body().getList().toString());
                pd.dismiss();
                myWeather.clear();
                boolean isMatch = false;
                List<ListItem> weatherDetails = response.body().getList();

                for(int i = 0; i< weatherDetails.size(); i++) {
//                    Log.d(TAG, "onResponse: TestMatch: " + response.body().getList().get(i).getDtTxt().trim()
//                            + " equals " + date.trim());
                    isMatch = false;
                    if(weatherDetails.get(i).getDtTxt() != null && weatherDetails.get(i).getDtTxt().trim().contentEquals(date.trim())) {
                        Log.d(TAG, "onResponse: Match: " + response.body().getList().get(i).getDtTxt()
                                + " equals " + date);

                        myWeather.add(weatherDetails.get(i));
                        isMatch = true;
                        break;
//                        weatherDetails.add(itemNeeded.getMain().getTemp(), itemNeeded.getMain().getHumidity(),
//                                itemNeeded.getWeather().get(0).getDescription(),
//                                itemNeeded.getWeather().get(0).getIcon());
                    }

                }

                if(!isMatch) {
                    Log.d(TAG, "onResponse: Response is null");
                    myWeather.clear();
                    Toast.makeText(MainActivity.this, "Data to far ahead.", Toast.LENGTH_SHORT).show();
                    tvTemp.setText("Temp: --");
                    tvDesc.setText("Weather: --");
                    tvHumidity.setText("Humidity: --");
                    ivWeatherIcon.setImageResource(R.drawable.ic_launcher_background);
                }
                Log.d(TAG, "onResponse: itemNeeded: " + myWeather.toString());

                String temp = null, desc = null, hum = null, icon = null;
                if(!myWeather.isEmpty()) {
                    for(int j = 0; j < myWeather.size(); j++) {
                        int temperature = (int) myWeather.get(0).getMain().getTemp();
                        temp = String.valueOf(temperature);
                        desc = String.valueOf(myWeather.get(0).getWeather().get(0).getDescription());
                        hum = String.valueOf(myWeather.get(0).getMain().getHumidity());
                        icon = String.valueOf(myWeather.get(0).getWeather().get(0).getIcon());
                    }

                    tvTemp.setText("Temp:" + temp + "°");
                    tvDesc.setText("Weather: " + desc);
                    tvHumidity.setText("Humidity: " + hum);
                    Log.d(TAG, "onResponse: icon symbol: " + icon);

                    switch (icon) {
                        case "01d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "02d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "03d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "04d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "04n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "10d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "11d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "13d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "01n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "02n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "03n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "10n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "11n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                        case "13n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherIcon);
                            break;
                    }
                }


            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: weather: " + t.getMessage());

            }
        });
    }
}
