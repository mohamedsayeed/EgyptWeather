package com.ibs.cityweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Mohamed Sayed on 9/3/2017.
 */

public class TabSelector extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Map", "List"};

    public TabSelector(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MapsFragment();
        } else {
            return new CitiesFragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}