package com.st.bio2bit.uicontroller.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Features.FeatureActivity;
import com.st.BlueSTSDK.Features.FeatureBattery;
import com.st.BlueSTSDK.Features.FeatureHumidity;
import com.st.BlueSTSDK.Features.FeaturePressure;
import com.st.BlueSTSDK.Features.FeatureTemperature;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.utilities.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mathias on 19/01/16.
 */
public class ValuesAdapter extends RecyclerView.Adapter<ValuesAdapter.ViewHolder> {

    private final Activity mActivity;
    private List<Feature> features;
    private boolean resetValue=true;

    public ValuesAdapter(Activity context) {
        features = new ArrayList<>();
        this.mActivity = context;
    }

    @Override
    public ValuesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.value_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.valueLabel.setText(features.get(position).getName());
        final String featureClassName = features.get(position).getClass().getSimpleName();
        holder.value.setText("----");
        if(Const.DEBUG) Log.d(Const.TAG, "Listener added");
        features.get(position).removeAllFeatureListener();
        features.get(position).addFeatureListener(new Feature.FeatureListener() {
            @Override
            public void onUpdate(Feature f, Feature.Sample sample) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.value.setText(getFeatureValue(featureClassName, position));
                    }
                });
            }
        });
        holder.unit.setText(getFeatureUnit(featureClassName, position));
        try{
            Const.ConfigurableFeatures configurableFeatures = Const.ConfigurableFeatures.valueOf(featureClassName);
            holder.frequencies.setEnabled(true);
        } catch (IllegalArgumentException e){
            holder.frequencies.setEnabled(false);
        }
    }

    private String getFeatureUnit(String featureClassName, int position) {
        String featureUnit;
        Const.FeatureClass featureClass = Const.FeatureClass.valueOf(featureClassName);
        try {
            switch (featureClass) {
                case FeaturePressure:
                    featureUnit = String.valueOf(FeaturePressure.FEATURE_UNIT);
                    break;
                case FeatureHumidity:
                    featureUnit = String.valueOf(FeatureHumidity.FEATURE_UNIT);
                    break;
                case FeatureTemperature:
                    featureUnit = String.valueOf(FeatureTemperature.FEATURE_UNIT);
                    break;
                case FeatureBattery:
                    featureUnit = String.valueOf(FeatureBattery.FEATURE_UNIT[0]);
                    break;
                case FeatureActivity:
                    featureUnit = String.valueOf(FeatureActivity.FEATURE_UNIT[0]);
                    break;
                case FeatureHeartRate:
                    featureUnit = "bpm";
                    break;
                case FeatureBreathingRate:
                    featureUnit = "bpm";
                    break;
                default:
                    featureUnit = "";
                    break;
            }
        } catch (NullPointerException e) {
            featureUnit = "";
        }
        return featureUnit;
    }

    private String getFeatureValue(String featureClassName, int position) {
        String featureValue;
        Const.FeatureClass featureClass = Const.FeatureClass.valueOf(featureClassName);
        try {
            switch (featureClass) {
                case FeaturePressure:
                    featureValue = String.valueOf(FeaturePressure.getPressure(features.get(position).getSample()));
                    break;
                case FeatureHumidity:
                    featureValue = String.valueOf(FeatureHumidity.getHumidity(features.get(position).getSample()));
                    break;
                case FeatureTemperature:
                    featureValue = String.valueOf(FeatureTemperature.getTemperature(features.get(position).getSample()));
                    break;
                case FeatureBattery:
                    featureValue = String.valueOf(FeatureBattery.getBatteryLevel(features.get(position).getSample()));
                    break;
                case FeatureActivity:
                    featureValue = String.valueOf(FeatureActivity.getActivityStatus(features.get(position).getSample()));
                    break;
                case FeatureHeartRate:
                    featureValue = "";
                    break;
                case FeatureBreathingRate:
                    featureValue = "";
                    break;
                default:
                    featureValue = "";
                    break;
            }
        } catch (NullPointerException e) {
            featureValue = "";
        }
        return featureValue;
    }

    @Override
    public int getItemCount() {
        return features.size();
    }

    public void addItem(Feature feature) {
        features.add(feature);
    }

    public List<Feature> getItems() {
        return features;
    }

    public void addItems(List<Feature> valuesFeatures) {
        features = new ArrayList<>();
        features.addAll(valuesFeatures);
    }

    public void startLog() {
        for (Feature feature : features) {

        }
    }

    public void blindFeatureList() {
        features = DataActivity.valueFeatures;
    }

    public void resetValue() {
        resetValue=true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.value_label)
        TextView valueLabel;
        @Bind(R.id.value)
        TextView value;
        @Bind(R.id.frequency_spinner)
        Spinner frequencies;
        @Bind(R.id.unit)
        TextView unit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            frequencies.setEnabled(false);
        }
    }
}
