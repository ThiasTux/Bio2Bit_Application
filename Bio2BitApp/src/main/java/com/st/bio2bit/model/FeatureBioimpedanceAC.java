package com.st.bio2bit.model;

import android.support.annotation.NonNull;

import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Features.Field;
import com.st.BlueSTSDK.Node;

/**
 * Created by mathias on 25/01/16.
 */
public class FeatureBioimpedanceAC extends Feature {
    /**
     * build a new disabled feature, that doesn't need to be initialized in the node side
     *
     * @param name     name of the feature
     * @param n        node that will update this feature
     * @param dataDesc description of the data that belong to this feature
     */
    public FeatureBioimpedanceAC(String name, Node n, @NonNull Field[] dataDesc) {
        super(name, n, dataDesc);
    }

    @Override
    protected ExtractResult extractData(long timestamp, byte[] data, int dataOffset) {
        return null;
    }
}
