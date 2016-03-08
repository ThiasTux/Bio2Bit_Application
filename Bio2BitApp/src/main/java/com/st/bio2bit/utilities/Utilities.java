package com.st.bio2bit.utilities;

import android.content.Context;

import com.st.BlueSTSDK.Feature;
import com.st.bio2bit.R;

import java.util.Arrays;

/**
 * Created by mathias on 16/02/16.
 */
public class Utilities {

    public static boolean isValueFeature(Feature feature){
        return Arrays.asList(Constants.valuesFeatures).contains(feature.getClass());
    }

    public static boolean isChartFeature(Feature feature){
        return Arrays.asList(Constants.chartsFeatures).contains(feature.getClass());
    }

    public static boolean isDeviceSupported(String deviceName) {
        return Arrays.asList(Constants.supportedDevices).contains(deviceName);
    }

    public static boolean isTablet(Context context){
        return context.getResources().getBoolean(R.bool.isTablet);
    }
}
