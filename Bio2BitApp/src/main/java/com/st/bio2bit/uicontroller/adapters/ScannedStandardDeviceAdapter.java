package com.st.bio2bit.uicontroller.adapters;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.st.bio2bit.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mathias on 02/03/16.
 */
public class ScannedStandardDeviceAdapter extends RecyclerView.Adapter<ScannedStandardDeviceAdapter.ViewHolder> {

    private List<BluetoothDevice> devicesList;
    private ClickListener clickListener;

    public ScannedStandardDeviceAdapter(List<BluetoothDevice> devicesList) {
        this.devicesList = devicesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scanned_device_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BluetoothDevice device = devicesList.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceAddress.setText(device.getAddress());
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    public BluetoothDevice getItem(int position) {
        return devicesList.get(position);
    }

    public void removeAll() {
        devicesList = new ArrayList<>();
    }

    public void setOnItemClickListener(ClickListener listener) {
        this.clickListener = listener;
    }

    public void addDevice(BluetoothDevice device) {
        devicesList.add(device);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.device_name)
        TextView deviceName;
        @Bind(R.id.device_address)
        TextView deviceAddress;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
