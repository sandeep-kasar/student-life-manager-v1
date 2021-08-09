package com.studentlifemanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.studentlifemanager.R;
import com.studentlifemanager.activity.HomeActivity;
import com.studentlifemanager.activity.SearchTaskActivity;
import com.studentlifemanager.adapter.PagerAdapter;


public class ToDoListFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflating layout
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        //set menu
        setHasOptionsMenu(true);

        //Initializing the tablayout
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        //((HomeActivity)getContext()).toolbar.setTitle(R.string.todolist);
        ((HomeActivity)getContext()).toolbar.setTitleTextColor(this.getResources().getColor(R.color.light_gray));

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager)view.findViewById(R.id.pager);

        //Creating our pager adapter
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Todo");
        tabLayout.getTabAt(1).setText("Done");

        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflating menu
        inflater.inflate(R.menu.menu_mytask, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search: //on click search icon on toolbar
                //open search activity
                startActivity(new Intent(getActivity(), SearchTaskActivity.class));
                return true;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e("frag","onTabSelected"+tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        Log.e("frag","onTabUnselected");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

        Log.e("frag","onTabReselected");
    }


}
