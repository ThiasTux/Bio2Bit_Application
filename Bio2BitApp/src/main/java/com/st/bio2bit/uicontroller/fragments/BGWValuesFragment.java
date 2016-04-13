package com.st.bio2bit.uicontroller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.st.bio2bit.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mathias on 12/04/16.
 */
public class BGWValuesFragment extends Fragment {

    private FragmentActivity activity;

    @Bind(R.id.bgw_heartrate_value)
    TextView bgwHeartRate;
    @Bind(R.id.bgw_breathingrate_value)
    TextView bgwBreathingRate;
    @Bind(R.id.bgw_activity_level_value)
    TextView bgwActivityLevel;
    @Bind(R.id.bgw_battery_level_value)
    TextView bgwBatteryLevel;
    @Bind(R.id.bgw_console)
    TextView bgwConsole;
    @Bind(R.id.bgw_console_scrollview)
    ScrollView bgwConsoleScrollview;

    private Handler handler;


    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bgw_fragment_values, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
