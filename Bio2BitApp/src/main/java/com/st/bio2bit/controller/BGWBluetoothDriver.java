package com.st.bio2bit.controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;

import com.st.bio2bit.model.BluetoothState;
import com.st.bio2bit.utilities.BGWConst;
import com.st.bio2bit.utilities.BGWConst.BGWCommandID;

import java.util.Arrays;


/**
 * Created by mathias on 15/03/16.
 */
public class BGWBluetoothDriver implements BluetoothSPP.OnDataReceivedListener {

    private final Handler mHandler;
    private BluetoothDevice device;
    private BluetoothSPP bluetoothSPP;

    public BGWBluetoothDriver(final BluetoothDevice device, Context context, Handler handler) {
        this.device = device;
        this.mHandler = handler;
        bluetoothSPP = new BluetoothSPP(context);
        bluetoothSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            @Override
            public void onDeviceConnected(String name, String address) {
                mHandler.obtainMessage(BGWConst.DEVICE_CONNECTED, device).sendToTarget();
            }

            @Override
            public void onDeviceDisconnected() {
                mHandler.obtainMessage(BGWConst.DEVICE_DISCONNECTED, device).sendToTarget();
            }

            @Override
            public void onDeviceConnectionFailed() {
                mHandler.obtainMessage(BGWConst.DEVICE_CONNECTION_FAILED, device).sendToTarget();
            }
        });
        bluetoothSPP.setOnDataReceivedListener(this);
        initializeService();
    }

    private void initializeService() {
        bluetoothSPP.setupService();
        bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
    }

    public void connect(){
        bluetoothSPP.connect(device.getAddress());
    }

    @Override
    public void onDataReceived(byte[] data, String message) {
        byte startOfPacket = data[0];
        byte frameControl = data[1];
        BGWCommandID commandID = BGWCommandID.getCommandID(data[3]);
        switch (commandID){
            case cfgRsp:
                parseCfgRsp(Arrays.copyOfRange(data, 4, data.length));
                break;


        }
    }

    private void parseCfgRsp(byte[] bytes) {

    }

    public int getServiceState() {
        return bluetoothSPP.getServiceState();
    }

    public void disconnect() {
        bluetoothSPP.disconnect();
    }
}
