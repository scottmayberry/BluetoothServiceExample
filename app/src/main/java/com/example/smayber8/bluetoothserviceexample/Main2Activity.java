package com.example.smayber8.bluetoothserviceexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    TextView textView;
    Messenger mService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = (TextView) findViewById(R.id.visibleTextView);
        Util.bC.setmContext(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        //Util.isBound = this.bindService(new Intent(Main2Activity.this,
          //      BluetoothService.class), , Context.BIND_AUTO_CREATE);
        textView.setText("Binding.");
    }
}
