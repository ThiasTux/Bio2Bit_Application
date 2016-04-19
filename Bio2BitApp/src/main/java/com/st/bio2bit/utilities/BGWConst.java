package com.st.bio2bit.utilities;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mathias on 01/04/16.
 */
public class BGWConst {

    public static final String PARAMS = "bgw_shared_prefs";

    public static boolean WAIT_FOR_BYTE;
    public static boolean WAIT_FOR_FRAME_CONTROL;
    public static boolean WAIT_FOR_START;
    public static boolean WAIT_FOR_COMMAND;
    public static boolean WAIT_FOR_CLUSTER;
    public static boolean WAIT_FOR_END;

    public static int START_OF_PACKET = 0x53;
    public static int FRAME_CONTROL = 0x81;
    public static int END_OF_PACKET = 0x81;

    public static final int DEVICE_CONNECTED = 1001;
    public static final int DEVICE_DISCONNECTED = 1002;
    public static final int DEVICE_CONNECTION_FAILED = 1003;
    public static final int NEW_ACCELERATION_DATA = 2001;
    public static final int NEW_HEARTRATE_DATA = 2002;
    public static final int NEW_BIOIMPEDANCE_DX_DATA = 2003;
    public static final int NEW_BIOIMPEDANCE_Z0_DATA = 2004;
    public static final int NEW_ACTIVITY_LEVEL_DATA = 2005;
    public static final int NEW_BATTERY_LEVEL_DATA = 2006;
    public static final int NEW_BREATHINGRATE_DATA = 2007;
    public static final int NEW_ELECTROCARDIOGRAM_DATA = 2008;
    public static final int NEW_RRI_DATA = 2009;


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
        private static final Map<Byte, BGWCommandID> lookup =
                new HashMap<>();

        BGWCommandID(byte id) {
            this.id = id;
        }

        static {
            for (BGWCommandID commandID : EnumSet.allOf(BGWCommandID.class)) {
                lookup.put(commandID.getId(), commandID);
            }
        }

        public byte getId() {
            return id;
        }

        public static boolean isBGWCommandId(byte id) {
            return Arrays.asList(new byte[]{
                    2, 3, 4, 5, 6, 7, 8, 9,
                    10, 11, 12, 13,
                    (byte) 128, (byte) 129, (byte) 131, (byte) 132, (byte) 133,
                    (byte) 200, (byte) 201, (byte) 202, (byte) 203,
                    (byte) 210, (byte) 211, (byte) 251
            }).contains(id);
        }

        public static BGWCommandID getCommandID(byte commandID) {
            return lookup.get(commandID);
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
        private static final Map<Byte, BGWClusterID> lookup =
                new HashMap<>();

        BGWClusterID(byte id) {
            this.id = id;
        }

        static {
            for (BGWClusterID clusterID : EnumSet.allOf(BGWClusterID.class)) {
                lookup.put(clusterID.getId(), clusterID);
            }
        }

        public byte getId() {
            return id;
        }

        public static boolean isBGWCluster(byte data) {
            return Arrays.asList(new byte[]{
                    1, 2, 3, 4, 5, 6, 7,
                    8, 9, 10, 11, 12, 13,
                    14, 15, (byte) 241, (byte) 242
            }).contains(data);
        }

        public static BGWClusterID getClusterID(byte id) {
            return lookup.get(id);
        }
    }

    public enum BGWVitalDataID {
        ecg((byte) 10),
        bioz((byte) 11),
        acc((byte) 12),
        hrr((byte) 13),
        br((byte) 14),
        albp((byte) 15),
        bat((byte) 16),
        trace_log((byte) 17),
        trace_log_rec((byte) 18),
        rri((byte) 19);

        private final byte id;
        private static final Map<Byte, BGWVitalDataID> lookup =
                new HashMap<>();

        BGWVitalDataID(byte id) {
            this.id = id;
        }

        static {
            for (BGWVitalDataID vitalDataID : EnumSet.allOf(BGWVitalDataID.class)) {
                lookup.put(vitalDataID.getId(), vitalDataID);
            }
        }

        private byte getId() {
            return id;
        }

        public static BGWVitalDataID getVitalDataID(byte id) {
            return lookup.get(id);
        }
    }

    public enum BGWStatus {
        start((byte) 1),
        stop((byte) 2),
        na((byte) 3);


        private final byte id;
        private static final Map<Byte, BGWStatus> lookup =
                new HashMap<>();

        BGWStatus(byte id) {
            this.id = id;
        }

        static {
            for (BGWStatus status : EnumSet.allOf(BGWStatus.class)) {
                lookup.put(status.getId(), status);
            }
        }

        private byte getId() {
            return id;
        }

        public static BGWStatus getStatus(byte id) {
            return lookup.get(id);
        }
    }

    public enum BGWNotificationID {
        buttonPush((byte) 50),
        lowBattery((byte) 51),
        powerOn((byte) 52),
        powerOff((byte) 53),
        onCharge((byte) 54),
        electrodesDetached((byte) 55);

        private final byte id;
        private static final Map<Byte, BGWNotificationID> lookup =
                new HashMap<>();

        BGWNotificationID(byte id) {
            this.id = id;
        }

        static {
            for (BGWNotificationID notificationID : EnumSet.allOf(BGWNotificationID.class)) {
                lookup.put(notificationID.getID(), notificationID);
            }
        }

        private byte getID() {
            return id;
        }

        public static BGWNotificationID getNotificationID(byte id) {
            return lookup.get(id);
        }
    }

    public enum BGWMedicalProtocolID {
        lhr((byte) 30),
        hhrAl((byte) 31),
        lbr((byte) 32),
        hbrAl((byte) 33),
        pause((byte) 34),
        arrhythmiaOnset((byte) 35),
        lhrAl((byte) 36),
        hhr((byte) 37),
        lbrAl((byte) 38),
        hbr((byte) 39);


        private final byte id;
        private static final Map<Byte, BGWMedicalProtocolID> lookup =
                new HashMap<>();

        BGWMedicalProtocolID(byte id) {
            this.id = id;
        }

        static {
            for (BGWMedicalProtocolID medicalProtocolID : EnumSet.allOf(BGWMedicalProtocolID.class)) {
                lookup.put(medicalProtocolID.getID(), medicalProtocolID);
            }
        }

        private byte getID() {
            return id;
        }

        public static BGWMedicalProtocolID getID(byte id) {
            return lookup.get(id);
        }
    }
}
