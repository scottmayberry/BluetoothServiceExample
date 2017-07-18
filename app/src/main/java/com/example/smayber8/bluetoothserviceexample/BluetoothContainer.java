package com.example.smayber8.bluetoothserviceexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

/**
 * Created by SMAYBER8 on 7/18/2017.
 */

public class BluetoothContainer {


    public Messenger mService;
    private Context mContext;
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    public BluetoothContainer()
    {
    }

    public synchronized void setmContext(Context mContext)
    {
        this.mContext = mContext;
    }
    public synchronized Context getmContext()
    {
        return mContext;
    }


    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(mContext == null)
                return;
            switch (msg.what) {
                case BluetoothService.MSG_SET_VALUE:
                    Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    public synchronized ServiceConnection getmConnection()
    {
        return mConnection;
    }
    public synchronized Messenger getmMessenger()
    {
        return mMessenger;
    }
    private void sendMessageToActivity(String msg) {
        Intent intent = new Intent("intentKey");
        intent.putExtra("key", msg);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    public ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mService = new Messenger(service);

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        BluetoothService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);

                // Give it some value as an example.
                msg = Message.obtain(null,
                        BluetoothService.MSG_SET_VALUE, this.hashCode(), 0);
                mService.send(msg);
                sendMessageToActivity(msg.toString());
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even
                // do anything with it; we can count on soon being
                // disconnected (and then reconnected if it can be restarted)
                // so there is no need to do anything here.
            }

            // As part of the sample, tell the user what happened.
            Toast.makeText(mContext, R.string.remote_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;

            // As part of the sample, tell the user what happened.
            Toast.makeText(mContext, R.string.remote_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };
}
