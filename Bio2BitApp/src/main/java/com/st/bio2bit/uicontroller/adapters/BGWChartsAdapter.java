package com.st.bio2bit.uicontroller.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by mathias on 12/04/16.
 */
public class BGWChartsAdapter extends android.support.v4.view.PagerAdapter{

    private final Context mContext;

    public BGWChartsAdapter(FragmentActivity activity) {
        this.mContext = (Context) activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
