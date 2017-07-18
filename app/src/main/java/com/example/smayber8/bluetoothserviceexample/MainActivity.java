package com.example.smayber8.bluetoothserviceexample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import static com.example.smayber8.bluetoothserviceexample.BluetoothService.REQUEST_ENABLE_BT;

public class MainActivity extends AppCompatActivity {

    private boolean reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCallbackText = (TextView) findViewById(R.id.mCallbackTextView);
        Util.bC.setmContext(getApplicationContext());
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("intentKey"));



    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("key");
            mCallbackText.setText(message);

            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(!Util.isBound) {
            startService(new Intent(this, BluetoothDataService.class));
            Util.isBound = true;
            //doBindService();
        }
        else
        {
            //doBindService();
        }
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        super.onDestroy();

    }

    TextView mCallbackText;

    public void onGoToNextView (View v)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }


    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        Util.isBound = this.bindService(new Intent(MainActivity.this,
                BluetoothService.class), Util.bC.getmConnection(), Context.BIND_AUTO_CREATE);
        mCallbackText.setText("Binding.");
    }

    void doUnbindService() {
        if (Util.isBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (Util.bC.getmConnection() != null) {
                try {
                    Message msg = Message.obtain(null,
                            BluetoothService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = Util.bC.getmMessenger();
                    Util.bC.mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

            // Detach our existing connection.
            unbindService(Util.bC.getmConnection());
            Util.isBound = false;
            mCallbackText.setText("Unbinding.");
        }
    }
}
