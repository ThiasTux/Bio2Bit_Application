package com.st.bio2bit.uicontroller.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.st.BlueSTSDK.Manager;
import com.st.BlueSTSDK.Node;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.adapters.BleDevicesAdapter;
import com.st.bio2bit.utilities.Const;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScanBleDeviceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.ble_devices) AbsListView mDevices;
    @Bind(R.id.scan_swipe_layout) SwipeRefreshLayout mSwipeLayout;

    private BleDevicesAdapter mAdapter;

    private static final int SCAN_PERIOD = 10000;
    private Handler mHanlder;
    private boolean mScanning;

    private Context mContext;

    private Manager mManager;

    private Manager.ManagerListener mManagerListener = new Manager.ManagerListener() {
        @Override
        public void onDiscoveryChange(Manager m, boolean enabled) {
            if(!enabled)
                stopNodeDiscovery();
        }

        @Override
        public void onNodeDiscovered(Manager m, final Node node) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.add(node);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    /**
     * request id for the activity that will ask to the user to enable the bt
     */
    private static final int REQUEST_ENABLE_BT = 1;
    /**
     * request id for grant the location permission
     */
    private static final int REQUEST_LOCATION_ACCESS = 2;
    private Menu mMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ble_device);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle(R.string.ble_devices);

        mHanlder = new Handler();

        mManager = Manager.getSharedInstance();
        mManager.addListener(mManagerListener);
        mContext = this;

        mAdapter = new BleDevicesAdapter(this);
        mDevices.setAdapter(mAdapter);

        mSwipeLayout.setOnRefreshListener(this);

        mDevices.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_scan_ble_device, menu);
        if(mManager.isDiscovering())
            toggleToolbarActions(false);
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
            case R.id.action_scan:
                startNodeDiscovery();
                break;
            case R.id.action_stop:
                stopNodeDiscovery();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopNodeDiscovery();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startNodeDiscovery();
    }

    /**
     * return after ask to the user to enable the bluetooth
     *
     * @param requestCode request code id
     * @param resultCode  request result, if is {@code Activity.RESULT_CANCELED} the activity is
     *                    closed since we need the bluetooth
     * @param data        request data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth -> close all
        if (requestCode == REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, com.st.BlueSTSDK.R.string.bluetoothNotEnabled, Toast.LENGTH_SHORT).show();
                finish();
            }else {
                //bluetooth enable -> try to start scanning
                startNodeDiscovery();
            }//if result
        }//if request
        super.onActivityResult(requestCode, resultCode, data);
    }//onActivityResult

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //we have the permission try to start the scan again
                    startNodeDiscovery();
                } else {
                    Toast.makeText(this, com.st.BlueSTSDK.R.string.LocationNotGranted,Toast.LENGTH_SHORT).show();
                }//if-else
                break;
            }//REQUEST_LOCATION_ACCESS
        }//switch
    }//onRequestPermissionsResult

    private void startNodeDiscovery() {
        if(checkAdapterAndPermission()) {
            mAdapter.clear();
            mManager.resetDiscovery();
            mManager.startDiscovery(SCAN_PERIOD);
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSwipeLayout.setRefreshing(true);
                    toggleToolbarActions(false);
                }
            });
        }
    }

    private void stopNodeDiscovery() {
        mManager.stopDiscovery();
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
                toggleToolbarActions(true);
            }
        });
    }

    private void toggleToolbarActions(boolean actionDiscovery) {
        if(mMenu!=null) {
            mMenu.findItem(R.id.action_scan).setVisible(actionDiscovery);
            mMenu.findItem(R.id.action_stop).setVisible(!actionDiscovery);
        }
    }

    /**
     * check to have the permission and the service enabled needed for stat a bluetooth scanning
     * @return true if we have all the requirements, false if we ask for something
     */
    private boolean checkAdapterAndPermission(){
        if(enableBluetoothAdapter()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(enableLocationService())
                    if(checkBlePermission())
                        return true;
            }else
                return true;
        }//if
        return false;
    }//checkAdapterAndPermission

    /**
     * check to have the permission needed for start a bluetooth scanning
     * @return true if we have ti false if we ask for it
     */
    private boolean checkBlePermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                final View viewRoot = ((ViewGroup) this
                        .findViewById(android.R.id.content)).getChildAt(0);
                Snackbar.make(viewRoot, com.st.BlueSTSDK.R.string.LocationCoarseRationale,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions((Activity) mContext,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        REQUEST_LOCATION_ACCESS);
                            }//onClick
                        }).show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION_ACCESS);
            }//if-else
            return false;
        }else
            return  true;
    }//checkBlePermission

    /**
     * check that the bluetooth is enabled
     * @return true if the bluetooth is enable false if we ask to the user to enable it
     */
    private boolean enableBluetoothAdapter(){
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //the adapter is !=null since we request in the manifest to have the bt capability
        final BluetoothAdapter btAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return false;
        }else
            return true;
    }//enableBluetoothAdapter

    /**
     * check that the location service is enabled
     * @return true if the location service is enabled, false if we ask to the user to do it
     */
    private boolean enableLocationService(){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) |
                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!providerEnabled) {
            Resources res = getResources();
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(res.getString(com.st.BlueSTSDK.R.string.EnablePositionService));
            dialog.setPositiveButton(res.getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            paramDialogInterface.cancel();
                        }
                    });
            dialog.setNegativeButton(res.getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            paramDialogInterface.cancel();
                            Toast.makeText(mContext, com.st.BlueSTSDK.R.string.LocationNotEnabled, Toast
                                    .LENGTH_SHORT)
                                    .show();
                            finish();
                        }//onClick
                    });
            dialog.show();
        }//if
        return providerEnabled;
    }//enableLocationService

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Node connectingNode = mAdapter.getItem(position);
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(R.string.connecting_title);
        progressDialog.setMessage(getResources().getString(R.string.connecting_message));
        connectingNode.addNodeStateListener(new Node.NodeStateListener() {
            @Override
            public void onStateChange(Node node, Node.State newState, Node.State prevState) {
                if (newState == Node.State.Connected) {
                    progressDialog.dismiss();
                    Intent i = new Intent(mContext, DataActivity.class);
                    i.putExtra(Const.NODE, node.getTag());
                    mContext.startActivity(i);
                } else if (newState == Node.State.Connecting) {
                    progressDialog.show();
                } else if (newState == Node.State.Dead && prevState == Node.State.Connecting) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext,
                            "Error during connection to node: "+node.getName(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        connectingNode.connect(mContext);
    }

    @Override
    public void onRefresh() {
        startNodeDiscovery();
    }
}
