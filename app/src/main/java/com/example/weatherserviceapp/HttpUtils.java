package com.example.weatherserviceapp;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import jsonweather.JsonWeather;
import jsonweather.Weather;
import jsonweather.WeatherJSONParser;

/**
 * Created by Адрей on 01.06.2015.
 */
public class HttpUtils {

    protected final static String TAG = "HttpUtils";
    public static String getWeatherJson(String city)
    {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city;

        try{
            Log.d(TAG, "create url");
            URL url = new URL(urlString);

            Log.d(TAG, "open http connection");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "get response code");
            if (con.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : "+ con.getResponseCode());
            }

            Log.d(TAG, "get buffer");
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            Log.d(TAG, "convert buffer to string:" );
            String results = br.readLine();

            Log.d(TAG, results );

            Log.d(TAG, "close http connection" );
            con.disconnect();

            return results;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<WeatherData> getWeather(String city)
    {
        try {
            String weatherJson = getWeatherJson(city);
            InputStream stream = new ByteArrayInputStream(weatherJson.getBytes(StandardCharsets.UTF_8));
            WeatherJSONParser parser = new WeatherJSONParser();
            List<JsonWeather> list =  parser.parseJsonStream(stream);
        }catch (IOException ex)
        {
            Log.d(TAG, "error during processing: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
