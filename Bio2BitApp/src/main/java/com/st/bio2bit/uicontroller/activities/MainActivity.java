package com.st.bio2bit.uicontroller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.st.bio2bit.R;
import com.st.bio2bit.utilities.Const;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.scan_ble_button)
    Button scanBleButton;
    @Bind(R.id.scan_standard_button)
    Button scanStandardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Const.DEBUG) Log.e(Const.TAG, "++ ON PAUSE ++");
    }

    @OnClick(R.id.scan_ble_button)
    protected void startScanBle(){
        Intent intent = new Intent(this, ScanBleDeviceActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.scan_standard_button)
    protected void startScanStandard(){
        Intent intent = new Intent(this, StandardPairedDeviceActivity.class);
        this.startActivity(intent);
    }
}
