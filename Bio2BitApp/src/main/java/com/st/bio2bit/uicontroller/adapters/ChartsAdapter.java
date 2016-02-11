package com.st.bio2bit.uicontroller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.st.BlueSTSDK.Feature;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mathias on 19/01/16.
 */
public class ChartsAdapter extends android.support.v4.view.PagerAdapter {

    private Context mContext;
    private List<Feature> features;

    @Bind(R.id.chart_label)
    TextView chartLabel;
    @Bind(R.id.start_plot_button)
    Button startPlotButton;
    @Bind(R.id.stop_plot_button)
    Button stopPlotButton;
    @Bind(R.id.chart_plot)
    LineChart lineChart;

    public ChartsAdapter(Context context) {
        this.mContext = context;
        features = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chart_view, container, false);
        ButterKnife.bind(this, view);
        if (features.size() != 0)
            chartLabel.setText(features.get(position).getName());
        final int page = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Page " + page + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
        startPlotButton.setOnClickListener(new StartPlotListener(position));
        container.addView(view);
        setupChart(position);
        return view;
    }

    private void setupChart(int position) {
        lineChart.getLegend().setEnabled(false);
        String featureClassName = features.get(position).getClass().getSimpleName();
        Constants.FeatureClass featureClass = Constants.FeatureClass.valueOf(featureClassName);
        switch (featureClass) {
            case FeaturePressure:
                LineDataSet dataSetPressure = new LineDataSet(new ArrayList<Entry>(), null);
                dataSetPressure.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                dataSetPressure.setDrawCubic(true);
                LineData dataPressure = new LineData(new String[]{}, dataSetPressure);
                lineChart.setData(dataPressure);

                break;
            case FeatureHumidity:
                LineDataSet dataSetHumidity = new LineDataSet(new ArrayList<Entry>(), null);
                dataSetHumidity.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                dataSetHumidity.setDrawCubic(true);
                LineData dataHumidity = new LineData(new String[]{}, dataSetHumidity);
                lineChart.setData(dataHumidity);
                break;
            case FeatureTemperature:
                LineDataSet dataSetTemperature = new LineDataSet(new ArrayList<Entry>(), null);
                dataSetTemperature.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                dataSetTemperature.setDrawCubic(true);
                LineData dataTemperature = new LineData(new String[]{}, dataSetTemperature);
                lineChart.setData(dataTemperature);
                break;
            case FeatureBattery:
                LineDataSet dataSetBattery = new LineDataSet(new ArrayList<Entry>(), null);
                dataSetBattery.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                dataSetBattery.setDrawCubic(true);
                LineData dataBattery = new LineData(new String[]{}, dataSetBattery);
                lineChart.setData(dataBattery);
                break;
            case FeatureHeartRate:
                LineDataSet dataSetHeartRate = new LineDataSet(new ArrayList<Entry>(), null);
                dataSetHeartRate.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                dataSetHeartRate.setDrawCubic(true);
                LineData dataHeartRate = new LineData(new String[]{}, dataSetHeartRate);
                lineChart.setData(dataHeartRate);
                break;
            case FeatureBreathingRate:
                LineDataSet dataSetBreathingRate = new LineDataSet(new ArrayList<Entry>(), null);
                dataSetBreathingRate.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                dataSetBreathingRate.setDrawCubic(true);
                LineData dataBreathingRate = new LineData(new String[]{}, dataSetBreathingRate);
                lineChart.setData(dataBreathingRate);
                break;
            default:

                break;
        }
    }

    @Override
    public int getCount() {
        return features.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addItems(List<Feature> chartsFeature) {
        features = new ArrayList<>();
        features.addAll(chartsFeature);
    }

    public void blindFeatureList() {
        features = DataActivity.chartFeatures;
    }

    public class StartPlotListener implements View.OnClickListener {

        int position;

        public StartPlotListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Constants.FeatureClass featureClass = Constants.FeatureClass
                    .valueOf(features.get(position).getClass().getSimpleName());
            switch (featureClass){
                case FeatureAcceleration:
                    break;
                case FeatureActivity:
                    break;
                case FeatureBioimpedanceAC:
                    break;
                case FeatureBioimpedanceDC:
                    break;
                case FeatureBreathingRate:
                    break;
                case FeatureGalvanicSkinResponse:
                    break;
                case FeatureECG:
                    break;
                case FeatureMemsSensorFusion:
                    break;
                case FeatureTemperature:
                    break;
                case FeatureMagnetometer:
                    break;
                case FeatureGyroscope:
                    break;
                case FeaturePressure:
                    break;

            }
            features.get(position).addFeatureListener(new Feature.FeatureListener() {
                @Override
                public void onUpdate(final Feature f, Feature.Sample sample) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LineData data = lineChart.getLineData();
                            List<LineDataSet> dataSet = data.getDataSets();
                            Constants.FeatureClass featureClass = Constants.FeatureClass.valueOf(f.getClass().getSimpleName());
                            switch (featureClass){
                                
                            }
                        }
                    });
                }
            });
        }
    }
}