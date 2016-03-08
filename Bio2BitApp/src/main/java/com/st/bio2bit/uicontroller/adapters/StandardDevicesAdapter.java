package com.st.bio2bit.uicontroller.adapters;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.st.bio2bit.R;

import java.util.ArrayList;

/**
 * Created by mathias on 25/02/16.
 */
public class StandardDevicesAdapter extends ArrayAdapter<BluetoothDevice> {

    private Activity mContext;
    private ArrayList<BluetoothDevice> deviceList;
    private AdapterView.OnItemClickListener listener;

    public StandardDevicesAdapter(Activity context) {
        super(context, R.layout.node_view_item);
        mContext = context;
        deviceList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){

        ViewHolderItem viewHolder;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.node_view_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.deviceName = (TextView) v.findViewById(R.id.device_name);
            viewHolder.deviceAddress = (TextView) v.findViewById(R.id.device_address);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) v.getTag();
        }

        BluetoothDevice device = getItem(position);

        viewHolder.deviceName.setText(device.getName());
        viewHolder.deviceAddress.setText(device.getAddress());

        return v;
    }

    public void removeAll() {
        deviceList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addDevice(BluetoothDevice device){
        if(!deviceList.contains(device)){
            this.add(device);
        }
    }

    private static class ViewHolderItem {
        TextView deviceName;
        TextView deviceAddress;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        this.listener = listener;
    }
}
