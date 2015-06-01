package com.example.weatherserviceapp;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by Адрей on 31.05.2015.
 */
public class WeatherServiceSync extends Service {

    /**
     * An implementation of the AIDL Interface DownloadCall.  We
     * extend the Stub class, which implements DownloadCall, so that
     * Android can properly handle calls across process boundaries.
     *
     * This implementation plays the role of Invoker in the Broker
     * Pattern
     */
    WeatherCall.Stub mDownloadCallImpl = new WeatherCall.Stub() {
        /**
         * Download the image at the given Uri and return a
         * pathname to the file on the Android file system.
         *
         * Use the methods defined in DownloadUtils for code
         * brevity.
         */
        @Override
        public List<WeatherData> getCurrentWeather(String Weather){
            return null;
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