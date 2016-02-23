package com.st.bio2bit.uicontroller.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Features.FeatureAcceleration;
import com.st.BlueSTSDK.Features.FeatureBattery;
import com.st.BlueSTSDK.Features.FeatureBioimpedanceCompact;
import com.st.BlueSTSDK.Features.FeatureElectrocardiogramCompact;
import com.st.BlueSTSDK.Features.FeatureGalvanicSkinResponseCompact;
import com.st.BlueSTSDK.Features.FeatureGyroscope;
import com.st.BlueSTSDK.Features.FeatureHumidity;
import com.st.BlueSTSDK.Features.FeatureMagnetometer;
import com.st.BlueSTSDK.Features.FeaturePressure;
import com.st.BlueSTSDK.Features.FeatureTemperature;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mathias on 11/02/16.
 */
public class ChartView extends View {

    @Bind(R.id.chart_label)
    TextView chartLabel;
    @Bind(R.id.start_plot_button)
    Button startPlotButton;
    @Bind(R.id.stop_plot_button)
    Button stopPlotButton;
    @Bind(R.id.chart_plot)
    LineChart lineChart;

    Feature feature;
    DataActivity mContext;
    Feature.FeatureListener listener;
    Handler handler;
    public final int visibleX=50;

    public ChartView(DataActivity context, Feature feature) {
        super(context);
        this.mContext = context;
        this.feature = feature;
        handler = DataActivity.mHandler;
        listener = new Feature.FeatureListener() {
            @Override
            public void onUpdate(final Feature f, final Feature.Sample sample) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LineData data = lineChart.getLineData();
                        List<ILineDataSet> dataSet = data.getDataSets();
                        Constants.FeatureClass featureClass = Constants.FeatureClass.valueOf(f.getClass().getSimpleName());
                        switch (featureClass) {
                            case FeatureAcceleration:
                                data.addXValue(String.valueOf(sample.timestamp));
                                data.addEntry(new Entry(FeatureAcceleration.getAccX(sample), dataSet.get(0).getEntryCount()), 0);
                                data.addEntry(new Entry(FeatureAcceleration.getAccY(sample), dataSet.get(1).getEntryCount()), 1);
                                data.addEntry(new Entry(FeatureAcceleration.getAccZ(sample), dataSet.get(2).getEntryCount()), 2);
                                lineChart.notifyDataSetChanged();
                                data.notifyDataChanged();
                                lineChart.setVisibleXRangeMaximum(visibleX);
                                lineChart.moveViewToX(data.getXValCount() - (visibleX + 1));
                                break;
                            case FeatureActivity:
                                break;
                            case FeatureBioimpedance:
                                break;
                            case FeatureBioimpedanceCompact:
                                break;
                            case FeatureBreathingRate:
                                break;
                            case FeatureGalvanicSkinResponse:
                                break;
                            case FeatureElectrocardiogram:
                                break;
                            case FeatureMemsSensorFusion:
                                break;
                            case FeatureTemperature:
                                dataSet.get(0).addEntry(new Entry(FeatureTemperature.getTemperature(sample), dataSet.get(0).getEntryCount()));
                                lineChart.notifyDataSetChanged();
                                lineChart.setMaxVisibleValueCount(120);
                                lineChart.moveViewToX(121);
                                break;
                            case FeatureMagnetometer:
                                data.addXValue(String.valueOf(sample.timestamp));
                                dataSet.get(0).addEntry(new Entry(FeatureMagnetometer.getMagX(sample), dataSet.get(0).getEntryCount()));
                                dataSet.get(1).addEntry(new Entry(FeatureMagnetometer.getMagY(sample), dataSet.get(0).getEntryCount()));
                                dataSet.get(2).addEntry(new Entry(FeatureMagnetometer.getMagZ(sample), dataSet.get(0).getEntryCount()));
                                lineChart.notifyDataSetChanged();
                                lineChart.setMaxVisibleValueCount(120);
                                lineChart.moveViewToX(121);
                                break;
                            case FeatureGyroscope:
                                data.addXValue(String.valueOf(sample.timestamp));
                                dataSet.get(0).addEntry(new Entry(FeatureGyroscope.getGyroX(sample), dataSet.get(0).getEntryCount()));
                                dataSet.get(1).addEntry(new Entry(FeatureGyroscope.getGyroY(sample), dataSet.get(0).getEntryCount()));
                                dataSet.get(2).addEntry(new Entry(FeatureGyroscope.getGyroZ(sample), dataSet.get(0).getEntryCount()));
                                lineChart.notifyDataSetChanged();
                                lineChart.setMaxVisibleValueCount(120);
                                lineChart.moveViewToX(121);
                                break;
                            case FeaturePressure:
                                break;
                            case FeatureElectrocardiogramCompact:
                                data.addXValue(String.valueOf(sample.timestamp));
                                data.addEntry(new Entry(FeatureElectrocardiogramCompact.getEcgValue(sample), dataSet.get(0).getEntryCount()), 0);
                                lineChart.notifyDataSetChanged();
                                lineChart.setVisibleXRangeMaximum(1000);
                                lineChart.moveViewToX(data.getXValCount() - (1001));
                        }
                    }
                });
            }
        };
    }

    public View getView(ViewGroup container) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chart_view, container, false);
        ButterKnife.bind(this, view);
        setupChart();
        chartLabel.setText(feature.getName());
        startPlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlotButton.setVisibility(GONE);
                stopPlotButton.setVisibility(VISIBLE);
                feature.removeFeatureListener(listener);
                feature.addFeatureListener(listener);
                Message message = handler.obtainMessage(Constants.START_STREAM_CF, feature);
                handler.sendMessage(message);
            }
        });
        stopPlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlotButton.setVisibility(View.GONE);
                startPlotButton.setVisibility(View.VISIBLE);
                feature.removeFeatureListener(listener);
                Message message = handler.obtainMessage(Constants.STOP_STREAM_CF, feature);
                handler.sendMessage(message);
            }
        });
        container.addView(view);
        setupChart();
        return view;
    }

    private void setupChart() {

        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to start logging in order to provide data for the chart.");

        lineChart.setTouchEnabled(false);

        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);

        lineChart.setPinchZoom(false);

        XAxis xl = lineChart.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setAvoidFirstLastClipping(true);
        xl.setSpaceBetweenLabels(5);
        xl.setEnabled(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setTextColor(Color.BLACK);
        axisLeft.setStartAtZero(false);
        axisLeft.setDrawGridLines(true);

        lineChart.getLegend().setEnabled(false);
        String featureClassName = feature.getClass().getSimpleName();
        Constants.FeatureClass featureClass = Constants.FeatureClass.valueOf(featureClassName);
        switch (featureClass) {
            case FeaturePressure:
                lineChart.setData(setupSingleComponentData());
                axisLeft.setAxisMaxValue(FeaturePressure.DATA_MAX);
                axisLeft.setAxisMinValue(FeaturePressure.DATA_MIN);
                break;
            case FeatureHumidity:
                lineChart.setData(setupSingleComponentData());
                axisLeft.setAxisMaxValue(FeatureHumidity.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureHumidity.DATA_MIN);
                break;
            case FeatureTemperature:
                lineChart.setData(setupSingleComponentData());
                axisLeft.setAxisMaxValue(FeatureTemperature.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureTemperature.DATA_MIN);
                break;
            case FeatureBattery:
                lineChart.setData(setupSingleComponentData());
                axisLeft.setAxisMaxValue(FeatureBattery.DATA_MAX[0]);
                axisLeft.setAxisMinValue(FeatureBattery.DATA_MIN[0]);
                break;
            case FeatureHeartRate:
                lineChart.setData(setupSingleComponentData());
                axisLeft.setAxisMaxValue(FeaturePressure.DATA_MAX);
                axisLeft.setAxisMinValue(FeaturePressure.DATA_MIN);
                break;
            case FeatureBreathingRate:
                lineChart.setData(setupSingleComponentData());
                axisLeft.setAxisMaxValue(FeaturePressure.DATA_MAX);
                axisLeft.setAxisMinValue(FeaturePressure.DATA_MIN);
                break;
            case FeatureAcceleration:
                lineChart.setData(setupTheeComponentData());
                axisLeft.setTextColor(Color.BLACK);
                axisLeft.setStartAtZero(false);
                axisLeft.setDrawGridLines(true);
                axisLeft.setAxisMaxValue(FeatureAcceleration.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureAcceleration.DATA_MIN);
                break;
            case FeatureMagnetometer:
                lineChart.setData(setupTheeComponentData());
                axisLeft.setAxisMaxValue(FeatureMagnetometer.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureMagnetometer.DATA_MIN);
                break;
            case FeatureGyroscope:
                lineChart.setData(setupTheeComponentData());
                axisLeft.setAxisMaxValue(FeatureGyroscope.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureGyroscope.DATA_MIN);
                break;
            case FeatureBioimpedanceCompact:
                lineChart.setData(setupTheeComponentData());
                axisLeft.setAxisMaxValue(FeatureBioimpedanceCompact.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureBioimpedanceCompact.DATA_MIN);
                break;
            case FeatureGalvanicSkinResponseCompact:
                lineChart.setData(setupTheeComponentData());
                axisLeft.setAxisMaxValue(FeatureGalvanicSkinResponseCompact.DATA_MAX);
                axisLeft.setAxisMinValue(FeatureGalvanicSkinResponseCompact.DATA_MIN);
                break;
            case FeatureElectrocardiogramCompact:
                lineChart.setData(setupTheeComponentData());
                //axisLeft.setAxisMaxValue(FeatureElectrocardiogramCompact.DATA_MAX);
                //axisLeft.setAxisMinValue(FeatureElectrocardiogramCompact.DATA_MIN);
                break;
            default:
                break;
        }

        lineChart.setAutoScaleMinMaxEnabled(true);
    }

    private LineData setupSingleComponentData() {
        LineData lineData = new LineData();
        lineData.addDataSet(setupDataset(R.color.colorX, feature.getName(), YAxis.AxisDependency.LEFT));
        return lineData;
    }

    private LineData setupTheeComponentData() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setupDataset(R.color.colorX, "X", YAxis.AxisDependency.LEFT));
        dataSets.add(setupDataset(R.color.colorY, "Y", YAxis.AxisDependency.LEFT));
        dataSets.add(setupDataset(R.color.colorZ, "Z", YAxis.AxisDependency.LEFT));
        ArrayList<String> xVals = new ArrayList<>();
        return new LineData(xVals, dataSets);
    }

    public LineDataSet setupDataset(int color, String label, YAxis.AxisDependency dependency) {
        LineDataSet lineDataSet = new LineDataSet(new ArrayList<Entry>(), label);
        lineDataSet.setColor(ContextCompat.getColor(mContext, color));
        lineDataSet.setAxisDependency(dependency);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        return lineDataSet;
    }
}
