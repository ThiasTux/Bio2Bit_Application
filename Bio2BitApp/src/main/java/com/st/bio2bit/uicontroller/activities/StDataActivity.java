package com.st.bio2bit.uicontroller.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.st.bio2bit.R;
import com.st.bio2bit.controller.BluetoothSPP;
import com.st.bio2bit.model.BluetoothState;
import com.st.bio2bit.utilities.Constants;

public class StDataActivity extends AppCompatActivity {

    private BluetoothDevice device;
    private BluetoothSPP btSPP;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_data);

        Intent intent = getIntent();
        device = intent.getParcelableExtra(Constants.BLUETOOTH_DEVICE);

        mContext = getApplicationContext();

        btSPP = new BluetoothSPP(this);

        btSPP.setupService();
        btSPP.startService(BluetoothState.DEVICE_OTHER);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(R.string.connecting_title);
        dialog.setMessage(getResources().getString(R.string.connecting_message));
        dialog.show();
        btSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                dialog.dismiss();
                Toast.makeText(mContext, R.string.device_connected, Toast.LENGTH_LONG).show();
                loadConfiguration();
            }

            @Override
            public void onDeviceDisconnected() {
                dialog.dismiss();
                Toast.makeText(mContext, R.string.device_disconnected, Toast.LENGTH_LONG).show();
                StDataActivity.this.finish();
            }

            @Override
            public void onDeviceConnectionFailed() {
                dialog.dismiss();
                Toast.makeText(mContext, R.string.device_conn_failed, Toast.LENGTH_LONG).show();
                StDataActivity.this.finish();
            }
        });
        btSPP.connect(device.getAddress());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btSPP.getServiceState() == BluetoothState.STATE_CONNECTED)
            btSPP.disconnect();

    }

    private void loadConfiguration() {

    }
}
