package com.example.weatherserviceapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import jsonweather.Weather;

/**
 * Created by Адрей on 31.05.2015.
 */
public class WeatherServiceAsync  extends Service {

    protected final String TAG = getClass().getSimpleName();

    Cache ch = new Cache();

    /**
     * The concrete implementation of the AIDL Interface
     * DownloadRequest.  We extend the Stub class, which implements
     * DownloadRequest, so that Android can properly handle calls
     * across process boundaries.
     *
     * This implementation plays the role of Invoker in the Broker
     * Pattern.
     */
    WeatherRequest.Stub mDownloadRequestImpl = new WeatherRequest.Stub() {
        /**
         * Download the image at the given Uri and return a
         * pathname to the file on the Android file system by
         * calling the sendPath() method on the provided Results
         *
         * Use the methods defined in DownloadUtils for code brevity.
         */
        @Override
        public void getCurrentWeather(String location,
                                  WeatherResults results)
                throws RemoteException {
            Log.d(TAG, "get results async");
            List<WeatherData> data = (List<WeatherData>)ch.ReadFromCache(location);
            if(data == null) {
                data = HttpUtils.getWeather(location);
                ch.WriteToCache(location, data);
            }

            results.sendResults(data);
        }
    };

    /**
     * Called when a component calls bindService() with the proper
     * intent.  Return the concrete implementation of DownloadRequest
     * cast as an IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadRequestImpl;
    }

    /**
     * Make an explicit Intent that will start this service when
     * passed to bindService().
     *
     * @param context		The context of the calling component.
     */
    public static Intent makeIntent(Context context) {
        // Create an explicit Intent and return it to the caller.
        return new Intent(context,
                WeatherServiceAsync.class);
    }
}
