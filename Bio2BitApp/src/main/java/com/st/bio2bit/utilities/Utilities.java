package com.st.bio2bit.utilities;

import com.st.BlueSTSDK.Feature;

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
}
