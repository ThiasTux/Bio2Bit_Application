package com.st.bio2bit.uicontroller.views.chartsview;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Features.FeatureAcceleration;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.uicontroller.views.ChartView;
import com.st.bio2bit.utilities.Const;

import butterknife.ButterKnife;

/**
 * Created by mathias on 11/02/16.
 */
public class AccelerationChartView extends ChartView {

    public final int visibleX=50;

    public AccelerationChartView(DataActivity context, Feature feature) {
        super(context, feature);
        this.listener = new Feature.FeatureListener() {
            @Override
            public void onUpdate(Feature f, final Feature.Sample sample) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LineData data = lineChart.getLineData();
                        int entryCount = data.getDataSetByIndex(0).getEntryCount();
                        if(entryCount > visibleX){
                            data.removeEntry(0, 0);
                            data.removeEntry(0, 1);
                            data.removeEntry(0, 2);
                        }
                        data.addXValue(String.valueOf(sample.timestamp));
                        data.addEntry(new Entry(FeatureAcceleration.getAccX(sample), data.getDataSetByIndex(0).getEntryCount()), 0);
                        data.addEntry(new Entry(FeatureAcceleration.getAccY(sample), data.getDataSetByIndex(1).getEntryCount()), 1);
                        data.addEntry(new Entry(FeatureAcceleration.getAccZ(sample), data.getDataSetByIndex(2).getEntryCount()), 2);
                        lineChart.notifyDataSetChanged();
                        data.notifyDataChanged();
                        lineChart.setVisibleXRangeMaximum(visibleX);
                        lineChart.moveViewToX(data.getXValCount() - (visibleX + 1));
                    }
                });
            }
        };
    }

    @Override
    public View getView(ViewGroup container){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chart_view, container, false);
        ButterKnife.bind(this, view);

        chartLabel.setText(feature.getName());
        startPlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlotButton.setVisibility(GONE);
                stopPlotButton.setVisibility(VISIBLE);
                feature.removeFeatureListener(listener);
                feature.addFeatureListener(listener);
                Message message = handler.obtainMessage(Const.START_STREAM_CF, feature);
                handler.sendMessage(message);
            }
        });
        stopPlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlotButton.setVisibility(View.GONE);
                startPlotButton.setVisibility(View.VISIBLE);
                feature.removeFeatureListener(listener);
                Message message = handler.obtainMessage(Const.STOP_STREAM_CF, feature);
                handler.sendMessage(message);
            }
        });
        container.addView(view);
        setupChart();
        return view;
    }

    @Override
    protected void setupChart(){
        super.setupChart();
        lineChart.getAxisLeft().setAxisMaxValue(FeatureAcceleration.DATA_MAX);
        lineChart.getAxisLeft().setAxisMinValue(FeatureAcceleration.DATA_MIN);
        lineChart.setData(setupTheeComponentData("X", "Y", "Z"));
        lineChart.getLegend().setEnabled(true);
        lineChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        LimitLine line = new LimitLine(FeatureAcceleration.DATA_MIN, "timestamp (ms)");
        line.setLineColor(Color.BLACK);
        line.setLineWidth(1f);
        line.setTextColor(Color.BLACK);
        line.setTextSize(12f);
        line.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        lineChart.getAxisLeft().addLimitLine(line);
    }
}
