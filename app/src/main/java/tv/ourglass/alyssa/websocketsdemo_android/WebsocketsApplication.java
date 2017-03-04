package tv.ourglass.alyssa.websocketsdemo_android;

import android.app.Application;

/**
 * Created by atorres on 3/3/17.
 */

public class WebsocketsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SocketIOManager.getInstance().connect();
    }
}
