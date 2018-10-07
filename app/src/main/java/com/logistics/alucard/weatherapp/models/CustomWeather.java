package com.logistics.alucard.weatherapp.models;

public class CustomWeather {

    String desc, icon;
    double temp, humidity;

    public CustomWeather(String desc, String icon, double temp, double humidity) {
        this.desc = desc;
        this.icon = icon;
        this.temp = temp;
        this.humidity = humidity;
    }

    public CustomWeather() {
    }

    @Override
    public String toString() {
        return "CustomWeather{" +
                "desc='" + desc + '\'' +
                ", icon='" + icon + '\'' +
                ", temp=" + temp +
                ", humidity=" + humidity +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
