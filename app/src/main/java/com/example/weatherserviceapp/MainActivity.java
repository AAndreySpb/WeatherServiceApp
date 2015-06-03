package com.example.weatherserviceapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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
    private WeatherCall mWeatherCall;

    /**
     * The AIDL Interface that we will use to make oneway calls to the
     * DownloadServiceAsync Service.  This plays the role of Requestor
     * in the Broker Pattern.  If it's null then there's no connection
     * to the Service.
     */
    private WeatherRequest mWeatherRequest;

    /**
     * Used to retain the ImageOps state between runtime configuration
     * changes.
     */
    protected final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(),
                    TAG);


    /**
     * This ServiceConnection is used to receive results after binding
     * to the DownloadServiceAsync Service using bindService().
     */
    ServiceConnection mServiceConnectionAsync = new ServiceConnection() {
        /**
         * Cast the returned IBinder object to the DownloadRequest
         * AIDL Interface and store it for later use in
         * mDownloadRequest.
         */
        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            // Call the generated stub method to convert the
            // service parameter into an interface that can be
            // used to make RPC calls to the Service.
            mWeatherRequest =
                    WeatherRequest.Stub.asInterface(service);
        }

        /**
         * Called if the remote service crashes and is no longer
         * available.  The ServiceConnection will remain bound,
         * but the service will not respond to any requests.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mWeatherRequest = null;
        }
    };


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

        handleConfigurationChanges();
    }

    /**
     * Handle hardware reconfigurations, such as rotating the display.
     */
    protected void handleConfigurationChanges() {
        // If this method returns true then this is the first time the
        // Activity has been created.
        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG,
                    "First time onCreate() call");

            // Bind this activity to the DownloadBoundService* Services if
            // they aren't already bound Use mBoundSync/mBoundAsync
            if(mWeatherCall == null)
                bindService(WeatherServiceSync.makeIntent(this),
                        mServiceConnectionSync,
                        BIND_AUTO_CREATE);
            if(mWeatherRequest == null)
                bindService(WeatherServiceAsync.makeIntent(this),
                        mServiceConnectionAsync,
                        BIND_AUTO_CREATE);

        } else {
            // The RetainedFragmentManager was previously initialized,
            // which means that a runtime configuration change
            // occured.

            Log.d(TAG,
                    "Second or subsequent onCreate() call");


            //@@TODO get saved data


        }
    }

    /**
     * This ServiceConnection is used to receive results after binding
     * to the DownloadServiceSync Service using bindService().
     */
    ServiceConnection mServiceConnectionSync = new ServiceConnection() {
        /**
         * Cast the returned IBinder object to the DownloadCall
         * AIDL Interface and store it for later use in
         * mDownloadCall.
         */
        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            Log.d(TAG, "ComponentName: " + name);
            // Call the generated stub method to convert the
            // service parameter into an interface that can be
            // used to make RPC calls to the Service.
            mWeatherCall =
                    WeatherCall.Stub.asInterface(service);
        }

        /**
         * Called if the remote service crashes and is no longer
         * available.  The ServiceConnection will remain bound,
         * but the service will not respond to any requests.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mWeatherCall = null;
        }
    };

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

    /**
     * Show a toast message.
     */
    public static void showToast(Context context,
                                 String message) {
        Toast.makeText(context,
                message,
                Toast.LENGTH_SHORT).show();
    }

    private void getWeatherAsync() {
        if(mWeatherRequest != null) {
            try {
                Log.d(TAG,
                        "Calling oneway mWeatherRequest.getCurrentWeather()");

                mWeatherRequest.getCurrentWeather(mPointText.getText().toString(),
                        mWeatherResults);
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void getWeatherSync() {
        if (mWeatherCall != null) {
            Log.d(TAG,
                    "Calling twoway mWeatherCall.getCurrentWeather()");
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
                        Log.d(TAG, "in AsyncTask");
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
                    if (result != null)
                    {
                        Log.d(TAG, "in Post execute, start new activity");
                        Intent intent = new Intent(MainActivity.this, WeatherResultsActivity.class);
                        intent.putExtra("Name", result.get(0).getName());
                        intent.putExtra("Icon", result.get(0).getIcon());
                        intent.putExtra("Humidity", result.get(0).getHumidity());
                        intent.putExtra("Temp", result.get(0).getTemp());
                        intent.putExtra("Speed", result.get(0).getSpeed());
                        intent.putExtra("Deg", result.get(0).getDeg());
                        startActivity(intent);
                    }else
                        showToast(MainActivity.this, "There is no weather data for this city!");

                    //    displayBitmap(result);
                }
            }.execute(mPointText.getText().toString());
        }
    }
}
