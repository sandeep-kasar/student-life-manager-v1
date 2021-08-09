package com.studentlifemanager.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.studentlifemanager.fragment.BlankFragment;
import com.studentlifemanager.R;
import com.studentlifemanager.adapter.ViewPagerAdapter;
import com.studentlifemanager.database.SessionData;
import com.studentlifemanager.fragment.BrowserFragment;
import com.studentlifemanager.fragment.MyExpenseFragment;
import com.studentlifemanager.fragment.TaskFragment;

public class HomeActivity extends AppCompatActivity {

    //class
    SessionData sessionData;

    //declare widgets
    private NavigationView navigationView;
    private DrawerLayout drawer;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setup view
        setUpView();

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        //screen content
        setupNavigationTab();

    }

    private void setUpView() {

        //init class
        sessionData = new SessionData(HomeActivity.this);

        // initialize widgets
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //setup view
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sdlm");


    }

    public void loadNavHeader() {

        View navHeader = navigationView.getHeaderView(0);
        TextView tvAvatar = navHeader.findViewById(R.id.tvAvatar);
        TextView tvName = navHeader.findViewById(R.id.tvName);

        String email = sessionData.getString("email", "User name");
        if (!email.equals("")) {
            tvAvatar.setText(email.substring(0, 1));
            tvName.setText(email);
        }

    }

    private void setUpNavigationView() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //call required activity
                    case R.id.nav_home:
                        //just close the drawer
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_my_task:
                        // launch MyTaskActivity
                        startActivity(new Intent(getApplicationContext(), MyTaskActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_my_browser:
                        // launch MyBrowserActivity
                        startActivity(new Intent(getApplicationContext(), MyBrowserActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_my_expense:
                        // launch MyExpenseActivity
                        startActivity(new Intent(getApplicationContext(), MyExpenseActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_help:
                        // launch HelpActivity
                        startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_setting:
                        // launch SettingActivity
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        //just close the drawer
                        drawer.closeDrawers();
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void setupNavigationTab() {

        //init ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        //init TabLayout and set viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyExpenseFragment(), "Expense");
        adapter.addFragment(new TaskFragment(), "Task");
        adapter.addFragment(new BrowserFragment(), "Browser");
        viewPager.setAdapter(adapter);
    }
}

//app:layout_scrollFlags="scroll|enterAlways"