package com.st.bio2bit.controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.st.bio2bit.model.BluetoothState;


/**
 * Created by mathias on 15/03/16.
 */
public class BGWBluetoothDriver implements BluetoothSPP.OnDataReceivedListener {

    private BluetoothDevice device;
    private BluetoothSPP bluetoothSPP;

    public BGWBluetoothDriver(BluetoothDevice device, Context context) {
        this.device = device;
        bluetoothSPP = new BluetoothSPP(context);
    }

    private void initializeService() {
        bluetoothSPP.setupService();
        bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
        bluetoothSPP.setOnDataReceivedListener(this);
    }

    private void connect(){
        bluetoothSPP.connect(device.getAddress());
    }

    private void setBluetoothConnectionListener(BluetoothSPP.BluetoothConnectionListener listener){
        bluetoothSPP.setBluetoothConnectionListener(listener);

    }

    private void setBluetoothStateListener(BluetoothSPP.BluetoothStateListener listener){
        bluetoothSPP.setBluetoothStateListener(listener);
    }


    @Override
    public void onDataReceived(byte[] data, String message) {

    }
}
