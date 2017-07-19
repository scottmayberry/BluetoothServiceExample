package com.example.smayber8.bluetoothserviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCallbackText = (TextView) findViewById(R.id.mCallbackTextView);
        Util.bC.setmContext(getApplicationContext());
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("intentKey"));
        if(!Util.isBound) {
            startService(new Intent(this, BluetoothDataService.class));
            Util.isBound = true;
        }//Util isBound


    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("key");
            mCallbackText.setText(message);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //doUnbindService();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }//on destroy

    TextView mCallbackText;

    public void onGoToNextView (View v)
    {
        Intent writeIntent = new Intent(getApplicationContext(), BluetoothDataService.class);
        writeIntent.putExtra("WRITE", "I have made it here" + ((int)(Math.random()*100)));
        startService(writeIntent);

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }//on go to next view
}