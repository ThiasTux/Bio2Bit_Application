package com.st.bio2bit.uicontroller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.st.BlueSTSDK.Feature;
import com.st.BlueSTSDK.Manager;
import com.st.BlueSTSDK.Node;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.uicontroller.adapters.ValuesAdapter;
import com.st.bio2bit.utilities.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValuesFragment extends Fragment {

    private FragmentActivity activity;

    @Bind(R.id.values_list)
    RecyclerView valuesList;
    @Bind(R.id.start_log)
    FloatingActionButton startLogFab;
    @Bind(R.id.stop_log)
    FloatingActionButton stopLogFab;

    private ValuesAdapter valuesAdapter;
    private Node node;
    private List<Feature> valuesFeatures;
    private Manager mManager;
    private Handler handler;


    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
        mManager = Manager.getSharedInstance();
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
        View view = inflater.inflate(R.layout.fragment_values, container, false);
        ButterKnife.bind(this, view);
        startLogFab.setEnabled(false);
        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        valuesList.setHasFixedSize(true);

        RecyclerView.LayoutManager llm = new LinearLayoutManager(activity);
        valuesList.setLayoutManager(llm);

        valuesAdapter = new ValuesAdapter(activity);
        valuesList.setAdapter(valuesAdapter);

    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFeatureLists();
    }


    public void setFeatureLists() {
        valuesAdapter.blindFeatureList();
        valuesAdapter.notifyDataSetChanged();
        startLogFab.setEnabled(true);
        handler = DataActivity.mHandler;
    }

    @OnClick(R.id.start_log)
    public void startAllValues() {
        Message message = handler.obtainMessage(Constants.START_STREAM_VF);
        handler.sendMessage(message);
        stopLogFab.setVisibility(View.VISIBLE);
        startLogFab.setVisibility(View.GONE);
    }

    @OnClick(R.id.stop_log)
    public void stopAllValues(){
        Message message = handler.obtainMessage(Constants.STOP_STREAM_VF);
        handler.sendMessage(message);
        valuesAdapter.notifyDataSetChanged();
        startLogFab.setVisibility(View.VISIBLE);
        stopLogFab.setVisibility(View.GONE);
    }
}