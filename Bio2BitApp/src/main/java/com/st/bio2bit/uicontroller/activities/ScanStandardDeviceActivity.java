package com.st.bio2bit.uicontroller.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.adapters.ScannedStandardDeviceAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScanStandardDeviceActivity extends Activity implements ScannedStandardDeviceAdapter.ClickListener {

    private static final long DISCOVERY_INTERVAL = 30000;
    private BluetoothAdapter mBluetoothAdapter;

    @Bind(R.id.scanned_devices_list)
    RecyclerView scannedView;
    @Bind(R.id.swipe_scan_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    ScannedStandardDeviceAdapter adapter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_standard_device);

        ButterKnife.bind(this);

        setTitle("Scanned devices:");
        setResult(Activity.RESULT_CANCELED);


        adapter = new ScannedStandardDeviceAdapter(new ArrayList<BluetoothDevice>());
        scannedView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        scannedView.setLayoutManager(llm);
        adapter.setOnItemClickListener(this);
        scannedView.setAdapter(adapter);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startDiscovery();
            }
        });

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        this.registerReceiver(mReceiver, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        startDiscovery();

    }

    private void startDiscovery() {
        adapter.removeAll();
        adapter.notifyDataSetChanged();
        mBluetoothAdapter.startDiscovery();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopDiscovery();
            }
        }, DISCOVERY_INTERVAL);
    }

    private void stopDiscovery() {
        mBluetoothAdapter.cancelDiscovery();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            stopDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mSwipeRefreshLayout.setRefreshing(false);

                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    adapter.addDevice(device);
                    adapter.notifyDataSetChanged();
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR) == BluetoothDevice.BOND_BONDED)
                    returnToMainActivity(device);
            }
        }
    };

    private void returnToMainActivity(BluetoothDevice device) {
        Intent intent = new Intent();
        intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(View v, int position) {
        BluetoothDevice device = adapter.getItem(position);
        device.createBond();
    }

}
