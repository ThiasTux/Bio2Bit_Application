package com.st.bio2bit.uicontroller.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.st.bio2bit.R;

/**
 * Created by mathias on 11/04/16.
 */
public class BGWSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.bgw_preferences);
    }
}
