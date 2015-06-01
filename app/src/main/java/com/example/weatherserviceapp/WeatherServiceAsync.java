package com.example.weatherserviceapp;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import jsonweather.Weather;

/**
 * Created by Адрей on 31.05.2015.
 */
public class WeatherServiceAsync  extends Service {

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
            // Download the file using the appropriate helper
            // method in DownloadUtils and then send the pathname
            // back to the client via the Results object.
            //@@TODO add here real results for weather
            results.sendResults(null);
        }
    };

    /**
     * Called when a component calls bindService() with the proper
     * intent.  Return the concrete implementation of DownloadRequest
     * cast as an IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        //@@ TODO here should be right implementation
        return null;
    }
}
