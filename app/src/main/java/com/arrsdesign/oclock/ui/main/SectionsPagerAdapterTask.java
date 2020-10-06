package com.arrsdesign.oclock.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.arrsdesign.oclock.Task2_Fragments.Completed;
import com.arrsdesign.oclock.Task2_Fragments.Current;
import com.arrsdesign.oclock.Task2_Fragments.Future;
import com.arrsdesign.oclock.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapterTask extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_5, R.string.tab_text_6, R.string.tab_text_7};
    private final Context mContext;

    public SectionsPagerAdapterTask(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new Current();
                break;
            case 1:
                fragment = new Future();
                break;
            case 2:
                fragment = new Completed();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}