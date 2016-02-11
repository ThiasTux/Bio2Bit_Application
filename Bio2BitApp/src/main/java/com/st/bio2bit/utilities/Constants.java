package com.st.bio2bit.utilities;

import com.st.BlueSTSDK.Features.FeatureAcceleration;
import com.st.BlueSTSDK.Features.FeatureActivity;
import com.st.BlueSTSDK.Features.FeatureBattery;
import com.st.BlueSTSDK.Features.FeatureGyroscope;
import com.st.BlueSTSDK.Features.FeatureHumidity;
import com.st.BlueSTSDK.Features.FeatureMagnetometer;
import com.st.BlueSTSDK.Features.FeatureMemsSensorFusion;
import com.st.BlueSTSDK.Features.FeaturePressure;
import com.st.BlueSTSDK.Features.FeatureTemperature;
import com.st.bio2bit.model.FeatureBioimpedanceAC;
import com.st.bio2bit.model.FeatureBioimpedanceDC;
import com.st.bio2bit.model.FeatureBreathingRate;
import com.st.bio2bit.model.FeatureECG;
import com.st.bio2bit.model.FeatureGalvanicSkinResponse;
import com.st.bio2bit.model.FeatureHeartRate;

/**
 * Created by mathias on 11/01/16.
 */
public class Constants {

    public static final String ST_TAG = "STBlue_Hippo";

    public static final String NODE = "com.st.stblue_hippo.NODE";

    public static final int START_STREAM_VF = 1001;
    public static final int STOP_STREAM_VF = 2001;
    public static final int START_STREAM_CF = 1002;

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
            FeatureHeartRate.class,
            FeatureBreathingRate.class
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
            FeatureHeartRate.class,
            FeatureBreathingRate.class,
            FeatureECG.class,
            FeatureBioimpedanceAC.class,
            FeatureBioimpedanceDC.class,
            FeatureGalvanicSkinResponse.class
    };

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
        FeatureECG,
        FeatureBioimpedanceAC,
        FeatureBioimpedanceDC,
        FeatureGalvanicSkinResponse,
        FeatureBattery
    }

    public enum ConfigurableFeatures {
        FeatureECG,
        FeatureGalvanicSkinResponse,
        FeatureBioimpedanceAC,
        FeatureBioimpedanceDC,
        FeatureHeartRate,
        FeatureBreathingRate
    }
}
