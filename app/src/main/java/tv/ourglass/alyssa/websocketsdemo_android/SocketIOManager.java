package tv.ourglass.alyssa.websocketsdemo_android;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by atorres on 3/3/17.
 */

public class SocketIOManager {

    String TAG = "SocketIOManager";

    private static SocketIOManager instance = new SocketIOManager();

    private static Socket mSocket;

    private SocketIOManager() {
        try {
            mSocket = IO.socket("http://129.65.48.86:3000");
        } catch (URISyntaxException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public static SocketIOManager getInstance() {
        return instance;
    }

    public void connect() {
        mSocket.connect();
    }

    public void disconnect() {
        mSocket.disconnect();
    }

    public void connectToServerWithNickname(String nickname) {
        mSocket.emit("connectUser", nickname);
    }

    public void getUserList(Emitter.Listener listener) {
        mSocket.on("userList", listener);
    }

    public void exitChatWithNickname(String nickname) {
        mSocket.emit("exitUser", nickname);
    }
}
