package com.st.bio2bit.uicontroller.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.st.bio2bit.R;
import com.st.bio2bit.controller.BluetoothSPP;
import com.st.bio2bit.model.BluetoothState;
import com.st.bio2bit.utilities.Const;

public class BGWDataActivity extends AppCompatActivity {

    private BluetoothDevice device;
    private BluetoothSPP btSPP;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_data);

        Intent intent = getIntent();
        device = intent.getParcelableExtra(Const.BLUETOOTH_DEVICE);

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
                ActionBar actionBar = getSupportActionBar();
                if(actionBar!=null)
                    actionBar.setTitle(name);
            }

            @Override
            public void onDeviceDisconnected() {
                dialog.dismiss();
                Toast.makeText(mContext, R.string.device_disconnected, Toast.LENGTH_LONG).show();
                BGWDataActivity.this.finish();
            }

            @Override
            public void onDeviceConnectionFailed() {
                dialog.dismiss();
                Toast.makeText(mContext, R.string.device_conn_failed, Toast.LENGTH_LONG).show();
                BGWDataActivity.this.finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bgw_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_settings) {
            Intent intent = new Intent(mContext, BGWSetParametersActivity.class);
            this.startActivity(intent);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btSPP.getServiceState() == BluetoothState.STATE_CONNECTED)
            btSPP.disconnect();

    }
}
