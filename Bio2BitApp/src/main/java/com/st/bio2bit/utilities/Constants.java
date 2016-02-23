package com.st.bio2bit.utilities;

import com.st.BlueSTSDK.Features.FeatureAcceleration;
import com.st.BlueSTSDK.Features.FeatureActivity;
import com.st.BlueSTSDK.Features.FeatureBattery;
import com.st.BlueSTSDK.Features.FeatureBioimpedance;
import com.st.BlueSTSDK.Features.FeatureBioimpedanceCompact;
import com.st.BlueSTSDK.Features.FeatureElectrocardiogram;
import com.st.BlueSTSDK.Features.FeatureElectrocardiogramCompact;
import com.st.BlueSTSDK.Features.FeatureGalvanicSkinResponse;
import com.st.BlueSTSDK.Features.FeatureGalvanicSkinResponseCompact;
import com.st.BlueSTSDK.Features.FeatureGyroscope;
import com.st.BlueSTSDK.Features.FeatureHumidity;
import com.st.BlueSTSDK.Features.FeatureMagnetometer;
import com.st.BlueSTSDK.Features.FeatureMemsSensorFusion;
import com.st.BlueSTSDK.Features.FeaturePressure;
import com.st.BlueSTSDK.Features.FeatureTemperature;

/**
 * Created by mathias on 11/01/16.
 */
public class Constants {

    public static final String ST_TAG = "STBlue_Hippo";

    public static final String NODE = "com.st.stblue_hippo.NODE";

    public static final int START_STREAM_VF = 1001;
    public static final int STOP_STREAM_VF = 2001;
    public static final int START_STREAM_CF = 1002;
    public static final int STOP_STREAM_CF = 2002;

    public static final String START_RECORDING = "com.st.stblue_hippo.start_recording";
    public static final String PAUSE_RECORDING = "com.st.stblue_hippo.pause_recording";
    public static final String STOP_RECORDING = "com.st.stblue_hippo.stop_recording";
    public static final String NEW_RECORDING = "com.st.stblue_hippo.new_recording";
    public static final String START_SERVICE = "com.st.stblue_hippo.start_service";
    public static final Class[] valuesFeatures = new Class[]{
            FeaturePressure.class,
            FeatureHumidity.class,
            FeatureTemperature.class,
            FeatureBattery.class,
            FeatureActivity.class,
    };

    public static final Class[] chartsFeatures = new Class[]{
            FeatureAcceleration.class,
            FeatureMagnetometer.class,
            FeatureGyroscope.class,
            FeaturePressure.class,
            FeatureHumidity.class,
            FeatureTemperature.class,
            FeatureMemsSensorFusion.class,
            FeatureActivity.class,
            FeatureElectrocardiogram.class,
            FeatureElectrocardiogramCompact.class,
            FeatureBioimpedance.class,
            FeatureBioimpedanceCompact.class,
            FeatureGalvanicSkinResponse.class,
            FeatureGalvanicSkinResponseCompact.class,
    };
    public static boolean IS_STREAMING_VF=false;

    public enum FeatureClass {
        FeatureAcceleration,
        FeatureMagnetometer,
        FeatureGyroscope,
        FeaturePressure,
        FeatureHumidity,
        FeatureTemperature,
        FeatureMemsSensorFusion,
        FeatureActivity,
        FeatureHeartRate,
        FeatureBreathingRate,
        FeatureElectrocardiogram,
        FeatureElectrocardiogramCompact,
        FeatureBioimpedance,
        FeatureBioimpedanceCompact,
        FeatureGalvanicSkinResponse,
        FeatureGalvanicSkinResponseCompact,
        FeatureBattery
    }

    public enum ConfigurableFeatures {
        FeatureElectrocardiogram,
        FeatureGalvanicSkinResponse,
        FeatureBioimpedanceAC,
        FeatureBioimpedanceDC,
        FeatureHeartRate,
        FeatureBreathingRate
    }
}
