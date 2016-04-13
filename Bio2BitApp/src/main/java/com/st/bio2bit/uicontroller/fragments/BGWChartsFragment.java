package com.st.bio2bit.uicontroller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.uicontroller.adapters.BGWChartsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mathias on 12/04/16.
 */
public class BGWChartsFragment extends Fragment {

    private FragmentActivity activity;

    @Bind(R.id.charts_list)
    ViewPager chartsList;
    @Bind(R.id.previous_fab)
    FloatingActionButton previousFab;
    @Bind(R.id.next_fab)
    FloatingActionButton nextFab;
    BGWChartsAdapter chartsAdapter;

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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_charts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        chartsAdapter = new BGWChartsAdapter(activity);
        chartsList.setAdapter(chartsAdapter);
        previousFab.setEnabled(false);
        nextFab.setEnabled(false);
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
        if(DataActivity.chartFeatures.size()>1)
            nextFab.setEnabled(true);
    }

    @OnClick(R.id.previous_fab)
    public void previousChart(){
        int totalFeatures = chartsAdapter.getCount();
        int currentElementIdx = chartsList.getCurrentItem();
        if(currentElementIdx==1 || currentElementIdx==0)
            previousFab.setEnabled(false);
        else if(currentElementIdx==totalFeatures-1)
            nextFab.setEnabled(true);
        chartsList.setCurrentItem(--currentElementIdx);
    }

    @OnClick(R.id.next_fab)
    public void nextChart(){
        int totalFeatures = chartsAdapter.getCount();
        int currentElementIdx = chartsList.getCurrentItem();
        if(currentElementIdx==(totalFeatures)-2)
            nextFab.setEnabled(false);
        else if(currentElementIdx==0)
            previousFab.setEnabled(true);
        chartsList.setCurrentItem(++currentElementIdx);
    }

}
