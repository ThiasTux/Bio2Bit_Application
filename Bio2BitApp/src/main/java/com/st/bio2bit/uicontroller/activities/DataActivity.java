package com.st.bio2bit.uicontroller.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Manager;
import com.st.BlueSTSDK.Node;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.adapters.PagerAdapter;
import com.st.bio2bit.uicontroller.fragments.ChartsFragment;
import com.st.bio2bit.uicontroller.fragments.ValuesFragment;
import com.st.bio2bit.utilities.Constants;
import com.st.bio2bit.utilities.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataActivity extends AppCompatActivity {

    private static final int NUM_TABS = 2;
    private Node connectedNode;
    private ValuesFragment valuesFragment;
    private ChartsFragment chartsFragment;
    public static List<Feature> valueFeatures = new ArrayList<>();
    public static List<Feature> chartFeatures = new ArrayList<>();

    public static Handler mHandler;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Manager mManager = Manager.getSharedInstance();
        connectedNode = mManager.getNodeWithTag(getIntent().getStringExtra(Constants.NODE));

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.START_STREAM_VF:
                        for (Feature feature : valueFeatures)
                            connectedNode.enableNotification(feature);
                        Constants.IS_STREAMING_VF = true;
                        return true;
                    case Constants.STOP_STREAM_VF:
                        for(Feature feature : valueFeatures)
                            connectedNode.disableNotification(feature);
                        Constants.IS_STREAMING_VF = false;
                        return true;
                    case Constants.START_STREAM_CF:
                        Feature feature_start = (Feature) msg.obj;
                        connectedNode.enableNotification(feature_start);
                        return true;
                    case Constants.STOP_STREAM_CF:
                        Feature feature_stop = (Feature) msg.obj;
                        if(!(Constants.IS_STREAMING_VF && Utilities.isValueFeature(feature_stop)))
                            connectedNode.disableNotification(feature_stop);
                    default:
                        return false;
                }
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && connectedNode != null)
            actionBar.setTitle(connectedNode.getName());

        if (Utilities.isTablet(this))
            setupFragments();
        else
            setupTabs();
    }

    private void extractFeatures() {
        List<Feature> features = connectedNode.getFeatures();
        for (Feature feature : features) {
            feature.removeAllFeatureListener();
            if (Arrays.asList(Constants.valuesFeatures).contains(feature.getClass()))
                valueFeatures.add(feature);
            if (Arrays.asList(Constants.chartsFeatures).contains(feature.getClass()))
                chartFeatures.add(feature);
        }
    }

    private void setupFragments() {

        valuesFragment = (ValuesFragment) getSupportFragmentManager().findFragmentById(R.id.values_fragment);
        chartsFragment = (ChartsFragment) getSupportFragmentManager().findFragmentById(R.id.charts_fragment);

    }

    private void setupTabs() {

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), DataActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.fragment_tabs);
        tabLayout.setupWithViewPager(viewPager);

        valuesFragment = (ValuesFragment) ((PagerAdapter) viewPager.getAdapter()).getItem(0);
        chartsFragment = (ChartsFragment) ((PagerAdapter) viewPager.getAdapter()).getItem(1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        extractFeatures();
    }

    @Override
    protected void onPause() {
        super.onPause();
        valueFeatures = new ArrayList<>();
        chartFeatures = new ArrayList<>();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(Constants.DEBUG) Log.d(Constants.TAG, "DataActivity destroyed!");
        connectedNode.disconnect();
    }
}
