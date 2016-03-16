package com.st.bio2bit.utilities;

import android.content.Context;
import android.os.Environment;

import com.st.BlueSTSDK.Feature;
import com.st.bio2bit.R;

import java.util.Arrays;

/**
 * Created by mathias on 16/02/16.
 */
public class Utils {

    public static boolean isValueFeature(Feature feature) {
        return Arrays.asList(Constants.valuesFeatures).contains(feature.getClass());
    }

    public static boolean isChartFeature(Feature feature) {
        return Arrays.asList(Constants.chartsFeatures).contains(feature.getClass());
    }

    public static boolean isDeviceSupported(String deviceName) {
        return Arrays.asList(Constants.supportedDevices).contains(deviceName);
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
