package com.snyxius.apps.dealwithit.Socket;

import android.content.Context;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.snyxius.apps.dealwithit.api.WebServices;

import java.net.URISyntaxException;

/**
 * Created by amanjham on 30/11/15 AD.
 */
public class SocketSingleton {
    private static SocketSingleton instance;
    private static final String SERVER_ADDRESS = WebServices.baseUrl;
    private Socket mSocket;
    private Context context;

    public SocketSingleton(Context context) {
        this.context = context;
        this.mSocket = getServerSocket();
    }

    public static SocketSingleton get(Context context){
        if(instance == null){
            instance = getSync(context);
        }
        instance.context = context;
        return instance;
    }

    private static synchronized SocketSingleton getSync(Context context) {
        if(instance == null){
            instance = new SocketSingleton(context);
        }
        return instance;
    }

    public Socket getSocket(){
        return this.mSocket;
    }

    public Socket getServerSocket() {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            mSocket = IO.socket(SERVER_ADDRESS, opts);
            return mSocket;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
