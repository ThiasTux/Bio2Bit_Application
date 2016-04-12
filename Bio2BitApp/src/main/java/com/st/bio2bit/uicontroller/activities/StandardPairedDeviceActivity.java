package com.st.bio2bit.uicontroller.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.adapters.ScannedStandardDeviceAdapter;
import com.st.bio2bit.utilities.Const;
import com.st.bio2bit.utilities.Utils;

import java.util.ArrayList;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StandardPairedDeviceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ScannedStandardDeviceAdapter.ClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.standard_devices)
    RecyclerView mDevices;

    ScannedStandardDeviceAdapter adapter;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_paired_device);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle(R.string.standard_paired_devices);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        adapter = new ScannedStandardDeviceAdapter(new ArrayList<BluetoothDevice>());
        if(Utils.isTablet(this)){
            mDevices.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mDevices.setLayoutManager(llm);
        }
        adapter.setOnItemClickListener(this);
        mDevices.setAdapter(adapter);

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Const.REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Const.DEBUG) Log.e(Const.TAG, "++ ON START ++");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Const.DEBUG) Log.e(Const.TAG, "++ ON RESUME ++");
        addBondedDevices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Const.DEBUG) Log.e(Const.TAG, "++ ON PAUSE ++");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Const.DEBUG) Log.e(Const.TAG, "++ ON STOP ++");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_standard_paired_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_add_devices:
                pairNewDevices();
                break;
            case R.id.action_settings:
                this.startActivity(new Intent(this, BGWSetParametersActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void pairNewDevices() {
        Intent intent = new Intent(this, ScanStandardDeviceActivity.class);
        this.startActivityForResult(intent, Const.REQUEST_DEVICE_PARING);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Const.REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "This application cannot work without Bluetooth turned on.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        if(requestCode == Const.REQUEST_DEVICE_PARING){
            if(resultCode == Activity.RESULT_OK) {
                BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Snackbar.make(this.getCurrentFocus(), device.getName()+" paired successfully", Snackbar.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void addBondedDevices(){
        Set<BluetoothDevice> bluetoothDevices = mBluetoothAdapter.getBondedDevices();
        adapter.removeAll();
        adapter.notifyDataSetChanged();

        if(bluetoothDevices.size()>0)
            for(BluetoothDevice device : bluetoothDevices){
                if(Utils.isDeviceSupported(device.getName()))
                    adapter.addDevice(device);
            }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View v, int position) {
        BluetoothDevice device = adapter.getItem(position);
        Intent intent = new Intent(this, BGWDataActivity.class);
        intent.putExtra(Const.BLUETOOTH_DEVICE, device);
        this.startActivity(intent);
    }
}
