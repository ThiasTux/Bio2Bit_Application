package com.st.bio2bit.model;

import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Features.Field;
import com.st.BlueSTSDK.Node;
import com.st.BlueSTSDK.Utils.NumberConversion;

/**
 * Created by mathias on 25/01/16.
 */
public class FeatureHeartRate extends Feature {

    public static final String FEATURE_NAME = "Heartrate";
    public static final String FEATURE_UNIT = "bpm";
    public static final String FEATURE_DATA_NAME = "Rate";
    public static final float DATA_MAX = 200;
    public static final float DATA_MIN = 0;

    public FeatureHeartRate(Node n) {
        super(FEATURE_NAME, n, new Field[]{
                new Field(FEATURE_DATA_NAME, FEATURE_UNIT, Field.Type.Int16, DATA_MAX, DATA_MIN)
        });
    }

    public static int getHeartrate(Sample sample){
        if(sample.data.length>0)
            if(sample.data[0] != null)
                return sample.data[0].intValue();
        return -1;
    }

    @Override
    protected ExtractResult extractData(long timestamp, byte[] data, int dataOffset) {
        if(data.length - dataOffset < 2)
            throw new IllegalArgumentException("There are no 2 bytes available to read");
        Sample bpm = new Sample(timestamp, new Number[]{
                NumberConversion.LittleEndian.bytesToInt16(data, dataOffset)
        });
        return new ExtractResult(bpm,2);
    }
}
