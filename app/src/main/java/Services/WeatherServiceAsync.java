package Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Адрей on 31.05.2015.
 */
public class WeatherServiceAsync  extends Service {

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
