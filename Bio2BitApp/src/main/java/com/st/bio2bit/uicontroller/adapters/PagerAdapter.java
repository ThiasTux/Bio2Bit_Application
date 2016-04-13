package com.st.bio2bit.uicontroller.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.fragments.BGWChartsFragment;
import com.st.bio2bit.uicontroller.fragments.BGWValuesFragment;
import com.st.bio2bit.uicontroller.fragments.ChartsFragment;
import com.st.bio2bit.uicontroller.fragments.ValuesFragment;

/**
 * Created by mathias on 12/01/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private final int NUM_TABS = 2;
    private Context context;
    private String[] tabTitles = new String[NUM_TABS];
    private Fragment valuesFragment;
    private Fragment chartsFragment;

    public PagerAdapter(FragmentManager fm, Context context, boolean isBGW) {
        super(fm);
        this.context = context;
        tabTitles[0] = context.getResources().getString(R.string.values);
        tabTitles[1] = context.getResources().getString(R.string.charts);
        if(isBGW) {
            valuesFragment = new BGWValuesFragment();
            chartsFragment = new BGWChartsFragment();
        } else {
            valuesFragment = new ValuesFragment();
            chartsFragment = new ChartsFragment();
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return valuesFragment;
        else
            return chartsFragment;
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
