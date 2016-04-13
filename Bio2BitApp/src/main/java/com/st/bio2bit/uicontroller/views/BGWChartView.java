package com.st.bio2bit.uicontroller.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.st.bio2bit.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mathias on 12/04/16.
 */
public class BGWChartView extends View {

    @Bind(R.id.chart_label)
    protected TextView chartLabel;
    @Bind(R.id.chart_plot)
    protected LineChart lineChart;

    private final String datatype;
    private final Context mContext;

    public BGWChartView(Context context, String datatype) {
        super(context);
        mContext = context;
        this.datatype = datatype;

    }

    public View getView(ViewGroup container) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bgw_chart_view, container, false);
        ButterKnife.bind(this, view);
        chartLabel.setText(datatype);
        container.addView(view);
        setupChart();
        return view;
    }

    protected void setupChart() {

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
        xl.setEnabled(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setTextColor(Color.BLACK);
        axisLeft.setStartAtZero(false);
        axisLeft.setDrawGridLines(true);

        lineChart.getLegend().setEnabled(false);

    }

    protected LineData setupSingleComponentData() {
        LineData lineData = new LineData();
        lineData.addDataSet(setupDataset(R.color.colorX, datatype, YAxis.AxisDependency.LEFT));
        return lineData;
    }

    protected LineData setupTheeComponentData(String first, String second, String third) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setupDataset(R.color.colorX, first, YAxis.AxisDependency.LEFT));
        dataSets.add(setupDataset(R.color.colorY, second, YAxis.AxisDependency.LEFT));
        dataSets.add(setupDataset(R.color.colorZ, third, YAxis.AxisDependency.LEFT));
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
