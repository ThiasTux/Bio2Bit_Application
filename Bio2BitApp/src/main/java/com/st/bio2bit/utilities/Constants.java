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

    public static final boolean DEBUG = true;
    public static final String TAG = "com.st.bio2bit";
    public static final String NODE = "com.st.bio2bit.NODE";

    public static final int START_STREAM_VF = 1001;
    public static final int STOP_STREAM_VF = 2001;
    public static final int START_STREAM_CF = 1002;
    public static final int STOP_STREAM_CF = 2002;

    public static final int REQUEST_DEVICE_PARING = 3001;

    public static final String START_RECORDING = "com.st.bio2bit.start_recording";
    public static final String PAUSE_RECORDING = "com.st.bio2bit.pause_recording";
    public static final String STOP_RECORDING = "com.st.bio2bit.stop_recording";
    public static final String NEW_RECORDING = "com.st.bio2bit.new_recording";
    public static final String START_SERVICE = "com.st.bio2bit.start_service";

    public static final String BLUETOOTH_DEVICE = "com.st.bio2bit.BLUETOOTH_DEVICE";

    /**
     * request id for the activity that will ask to the user to enable the bt
     */
    public static final int REQUEST_ENABLE_BT = 1;
    /**
     * request id for grant the location permission
     */
    public static final int REQUEST_LOCATION_ACCESS = 2;

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

    public static boolean IS_STREAMING_VF = false;

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

    public static String[] supportedDevices = new String[]{
            "Pulse Sensor"
    };

    public enum BGWCommandID {
        cfgReq((byte) 2),
        cfgRsp((byte) 3),
        setOpModeReq((byte) 4),
        setOpModeRsp((byte) 5),
        shutdownReq((byte) 6),
        shutdownRsp((byte) 7),
        identifyReq((byte) 8),
        identifyRsp((byte) 9),
        getInfoReq((byte) 10),
        getInfoRsp((byte) 11),
        notifInd((byte) 12),
        notifRsp((byte) 13),
        startUploadReq((byte) 128),
        startUploadRsp((byte) 129),
        continueUploadReq((byte) 130),
        continueUploadRsp((byte) 131),
        pushDataInd((byte) 132),
        pushDataRsp((byte) 133),
        startTestReq((byte) 200),
        startTestRsp((byte) 201),
        stopTestReq((byte) 202),
        stopTestRsp((byte) 203),
        fotaWriteReq((byte) 210),
        fotaWriteRsp((byte) 211),
        genericErrorRsp((byte) 251);

        private final byte id;

        BGWCommandID(byte id) {
            this.id = id;
        }
    }

    public enum BGWClusterID {
        syncSettings((byte) 1),
        dataElementSettings((byte) 2),
        notifSettings((byte) 3),
        medProtSettings((byte) 4),
        algoSettings((byte) 5),
        miscSettings((byte) 6),
        infoType((byte) 7),
        about((byte) 8),
        opMode((byte) 9),
        vitalsTypeInt((byte) 10),
        uploadComplete((byte) 11),
        eventNotif((byte) 12),
        negAck((byte) 13),
        currLogId((byte) 14),
        shutdownType((byte) 15),
        vitalsData((byte) 241),
        fwChunk((byte) 242);

        private final byte id;

        BGWClusterID(byte id) {
            this.id = id;
        }
    }
}
