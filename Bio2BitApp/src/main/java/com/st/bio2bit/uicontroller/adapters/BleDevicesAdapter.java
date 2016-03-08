package com.st.bio2bit.uicontroller.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.st.BlueSTSDK.Node;
import com.st.bio2bit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathias on 09/12/15.
 */
public class BleDevicesAdapter extends ArrayAdapter<Node> {

    private Activity mContext;
    private List<Node> nodeList;

    public BleDevicesAdapter(Activity context) {
        super(context, R.layout.node_view_item);
        mContext = context;
        nodeList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){

        ViewHolderItem viewHolder;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.node_view_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.sensorName = (TextView) v.findViewById(R.id.device_name);
            viewHolder.sensorAddress = (TextView) v.findViewById(R.id.device_address);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) v.getTag();
        }

        Node node = getItem(position);

        viewHolder.sensorName.setText(node.getName());
        viewHolder.sensorAddress.setText(node.getTag());

        return v;
    }

    private static class ViewHolderItem {
        TextView sensorName;
        TextView sensorAddress;
    }

}
