package com.example.weatherserviceapp;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import jsonweather.Weather;

/**
 * Created by Адрей on 01.06.2015.
 */
public class HttpUtils {

    public static String getWeatherJson(String city)
    {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city;

        try{
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            if (con.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : "+ con.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            con.disconnect();
            return br.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<WeatherData> getWeather(String city)
    {
        getWeatherJson(city);
        return null;
    }
}
