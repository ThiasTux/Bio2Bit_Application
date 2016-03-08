package com.st.bio2bit.uicontroller.adapters;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.st.BlueSTSDK.Feature;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.uicontroller.views.ChartView;
import com.st.bio2bit.uicontroller.views.chartsview.AccelerationChartView;
import com.st.bio2bit.uicontroller.views.chartsview.GyroscopeChartView;
import com.st.bio2bit.uicontroller.views.chartsview.MagnetometerChartView;
import com.st.bio2bit.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mathias on 19/01/16.
 */
public class ChartsAdapter extends android.support.v4.view.PagerAdapter {

    private DataActivity mContext;
    private List<Feature> features;
    private Feature.FeatureListener[] listeners;
    private Handler handler;
    private ChartsAdapter chartsAdapter;

    @Bind(R.id.chart_label)
    TextView chartLabel;
    @Bind(R.id.start_plot_button)
    Button startPlotButton;
    @Bind(R.id.stop_plot_button)
    Button stopPlotButton;
    @Bind(R.id.chart_plot)
    LineChart lineChart;

    public ChartsAdapter(DataActivity context) {
        this.mContext = context;
        features = new ArrayList<>();
        handler = DataActivity.mHandler;
        chartsAdapter = this;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view;
        Constants.FeatureClass featureClass = Constants.FeatureClass.valueOf(
                features.get(position).getClass().getSimpleName());
        switch (featureClass){
            case FeatureAcceleration:
                view = new AccelerationChartView(mContext, features.get(position)).getView(container);
                break;
            case FeatureGyroscope:
                view = new GyroscopeChartView(mContext, features.get(position)).getView(container);
                break;
            case FeatureMagnetometer:
                view = new MagnetometerChartView(mContext, features.get(position)).getView(container);
                break;

            default:
                view = new ChartView(mContext, features.get(position)).getView(container);
                break;
        }
        final int page = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Page " + page + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;
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

        }
    }
}