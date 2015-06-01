package com.example.weatherserviceapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.weatherserviceapp.LifecycleLoggingActivity;
import com.example.weatherserviceapp.R;

import java.util.List;

import jsonweather.Weather;


public class MainActivity extends LifecycleLoggingActivity {

    /**
     * Text box for destination
     */
    private EditText mPointText;


    /**
     * The AIDL Interface that's used to make twoway calls to the
     * DownloadServiceSync Service.  This object plays the role of
     * Requestor in the Broker Pattern.  If it's null then there's no
     * connection to the Service.
     */
    WeatherCall mWeatherCall;

    /**
     * The AIDL Interface that we will use to make oneway calls to the
     * DownloadServiceAsync Service.  This plays the role of Requestor
     * in the Broker Pattern.  If it's null then there's no connection
     * to the Service.
     */
    WeatherRequest mWeatherRequest;


    /**
     * The implementation of the DownloadResults AIDL
     * Interface. Should be passed to the DownloadBoundServiceAsync
     * Service using the DownloadRequest.downloadImage() method.
     *
     * This implementation of DownloadResults.Stub plays the role of
     * Invoker in the Broker Pattern.
     */
    WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {
        /**
         * Called when the DownloadServiceAsync finishes obtaining
         * the results from the GeoNames Web service.  Use the
         * provided String to display the results in a TextView.
         */
        @Override
        public void sendResults(final List<WeatherData> results) throws RemoteException {
            // Create a new Runnable whose run() method displays
            // the bitmap image whose pathname is passed as a
            // parameter to sendPath().  Please use
            // displayBitmap() defined in DownloadBase.
            final Runnable displayRunnable = new Runnable() {
                public void run() {

                   //@@TODO add code for return results here
                }
            };

            runOnUiThread(displayRunnable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPointText = (EditText) findViewById(R.id.editText);
    }

    /**
     * Hide the keyboard after a user has finished typing the url.
     */
    protected void hideKeyboard() {
        InputMethodManager mgr =
                (InputMethodManager) getSystemService
                        (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(mPointText.getWindowToken(),
                0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when a user presses a button(see
     * res/layout/activity_download.xml)
     */
    public void runService(View view) {

        hideKeyboard();

        switch(view.getId()) {
            case R.id.button_get_sync:
                getWeatherSync();
                break;

            case R.id.button_get_async:
                getWeatherAsync();
                break;
        }
    }

    private void getWeatherAsync() {
        if(mWeatherRequest != null) {
            try {
                Log.d(TAG,
                        "Calling oneway DownloadServiceAsync.downloadImage()");

                // Call downloadImage() on mDownloadRequest, passing in
                // the appropriate Uri and Results.
                //@@TODO download weather
                mWeatherRequest.getCurrentWeather("",
                        mWeatherResults);
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void getWeatherSync() {
        if (mWeatherCall != null) {
            Log.d(TAG,
                    "Calling twoway DownloadServiceSync.downloadImage()");
            /**
             * Define an AsyncTask instance to avoid blocking the UI Thread.
             * */
            new AsyncTask<String, Void, List<WeatherData> >() {
                /**
                 * Runs in a background thread.
                 */
                @Override
                protected List<WeatherData> doInBackground(String... params) {
                    try {
                        return mWeatherCall.getCurrentWeather(params[0]);
                    } catch(RemoteException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                /**
                 * Runs in the UI Thread.
                 */
                @Override
                protected void onPostExecute(List<WeatherData> result) {
                    //@@TODO start new activity to show weather
                    //if (result != null)
                    //    displayBitmap(result);
                }
                //@@TODO past real location
            }.execute("");
        }
    }
}
