package com.example.smayber8.bluetoothserviceexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

/**
 * Created by SMAYBER8 on 7/18/2017.
 */

public class Util {
    public static boolean isBound = false;

    public static BluetoothContainer bC = new BluetoothContainer();

}
