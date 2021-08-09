package com.studentlifemanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.studentlifemanager.fragment.DoneFragment;
import com.studentlifemanager.fragment.TaskFragment;

/**
 * Created by Owner on 04-04-2017.
 */

//Extending FragmentStatePagerAdapter
public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                TaskFragment tab1 = new TaskFragment();
                return tab1;
            case 1:
                DoneFragment tab2 = new DoneFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
