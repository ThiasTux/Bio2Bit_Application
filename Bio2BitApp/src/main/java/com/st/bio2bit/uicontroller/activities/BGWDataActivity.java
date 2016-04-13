package com.st.bio2bit.uicontroller.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.st.bio2bit.R;
import com.st.bio2bit.controller.BGWBluetoothDriver;
import com.st.bio2bit.model.BluetoothState;
import com.st.bio2bit.uicontroller.adapters.PagerAdapter;
import com.st.bio2bit.uicontroller.fragments.BGWChartsFragment;
import com.st.bio2bit.uicontroller.fragments.BGWValuesFragment;
import com.st.bio2bit.utilities.BGWConst;
import com.st.bio2bit.utilities.Const;
import com.st.bio2bit.utilities.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BGWDataActivity extends AppCompatActivity {

    private Context mContext;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private BGWValuesFragment valuesFragment;
    private BGWChartsFragment chartsFragment;
    private boolean isStreaming = false;
    private Handler mHandler;
    private ProgressDialog dialog;
    private BGWBluetoothDriver bgwBluetoothDriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgw_data);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initializeHandler();

        Intent intent = getIntent();
        BluetoothDevice device = intent.getParcelableExtra(Const.BLUETOOTH_DEVICE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(device.getName());

        if (Utils.isTablet(this))
            setupFragments();
        else
            setupTabs();

        mContext = getApplicationContext();
        dialog = new ProgressDialog(this);
        dialog.setTitle(R.string.connecting_title);
        dialog.setMessage(getResources().getString(R.string.connecting_message));
        dialog.show();

        bgwBluetoothDriver = new BGWBluetoothDriver(device, this, mHandler);
        bgwBluetoothDriver.connect();

    }

    private void initializeHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case BGWConst.DEVICE_CONNECTED:
                        BluetoothDevice deviceConnected = (BluetoothDevice) msg.obj;
                        if (dialog != null)
                            dialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                deviceConnected.getName() + " connected.",
                                Toast.LENGTH_LONG)
                                .show();
                        break;
                    case BGWConst.DEVICE_DISCONNECTED:
                        dialog.dismiss();
                        BluetoothDevice deviceDisconnected = (BluetoothDevice) msg.obj;
                        Toast.makeText(getApplicationContext(),
                                deviceDisconnected.getName() + " disconnected.",
                                Toast.LENGTH_LONG)
                                .show();
                        BGWDataActivity.this.finish();
                        break;
                    case BGWConst.DEVICE_CONNECTION_FAILED:
                        dialog.dismiss();
                        BluetoothDevice deviceConnFailed = (BluetoothDevice) msg.obj;
                        Toast.makeText(getApplicationContext(),
                                "Connection to " + deviceConnFailed.getName() + " failed.",
                                Toast.LENGTH_LONG)
                                .show();
                        BGWDataActivity.this.finish();
                        break;
                }
            }
        };
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_start_stream).setVisible(!isStreaming);
        menu.findItem(R.id.action_stop_stream).setVisible(isStreaming);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(mContext, BGWSetParametersActivity.class);
                this.startActivity(intent);
                break;
            case R.id.action_start_stream:
                isStreaming = true;
                invalidateOptionsMenu();
                break;
            case R.id.action_stop_stream:
                isStreaming = false;
                invalidateOptionsMenu();
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgwBluetoothDriver.getServiceState() == BluetoothState.STATE_CONNECTED)
            bgwBluetoothDriver.disconnect();

    }

    private void setupFragments() {

        valuesFragment = (BGWValuesFragment) getSupportFragmentManager().findFragmentById(R.id.values_fragment);
        chartsFragment = (BGWChartsFragment) getSupportFragmentManager().findFragmentById(R.id.charts_fragment);

    }

    private void setupTabs() {

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), BGWDataActivity.this, true));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.fragment_tabs);
        tabLayout.setupWithViewPager(viewPager);

        valuesFragment = (BGWValuesFragment) ((PagerAdapter) viewPager.getAdapter()).getItem(0);
        chartsFragment = (BGWChartsFragment) ((PagerAdapter) viewPager.getAdapter()).getItem(1);

    }
}
