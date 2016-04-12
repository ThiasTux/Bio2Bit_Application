package com.st.bio2bit.utilities;

import android.content.Context;
import android.os.Environment;

import com.st.BlueSTSDK.Feature;
import com.st.bio2bit.R;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by mathias on 16/02/16.
 */
public class Utils {

    public static boolean isValueFeature(Feature feature) {
        return Arrays.asList(Const.valuesFeatures).contains(feature.getClass());
    }

    public static boolean isChartFeature(Feature feature) {
        return Arrays.asList(Const.chartsFeatures).contains(feature.getClass());
    }

    public static boolean isDeviceSupported(String deviceName) {
        return Arrays.asList(Const.supportedDevices).contains(deviceName);
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

    public static int bytesToInt(int littleEndian, byte[] byteArray) throws ByteLenghtException {
        if(byteArray.length<=4) {
            int data = 0;
            if (littleEndian == Const.LITTLE_ENDIAN) {
                data = new BigInteger(byteArray).intValue();
            } else {
                ArrayUtils.reverse(byteArray);
                data = new BigInteger(byteArray).intValue();
            }
            return data;
        } else {
            throw new ByteLenghtException("Wrong byteArray length, it should be <=4, instead of: " + byteArray.length);
        }
    }

    public static class ByteLenghtException extends Throwable {
        public ByteLenghtException(String message) {
            super(message);
        }
    }
}
