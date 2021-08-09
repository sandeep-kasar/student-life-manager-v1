package com.studentlifemanager.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.studentlifemanager.R;
import com.studentlifemanager.fragment.AprFragment;
import com.studentlifemanager.fragment.AugFragment;
import com.studentlifemanager.fragment.DecFragment;
import com.studentlifemanager.fragment.FebFragment;
import com.studentlifemanager.fragment.JanFragment;
import com.studentlifemanager.fragment.JulFragment;
import com.studentlifemanager.fragment.JunFragment;
import com.studentlifemanager.fragment.MarFragment;
import com.studentlifemanager.fragment.MayFragment;
import com.studentlifemanager.fragment.NovFragment;
import com.studentlifemanager.fragment.OctFragment;
import com.studentlifemanager.fragment.SeptFragment;

import java.util.ArrayList;
import java.util.List;

public class MyExpenseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_expense);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.lbl_my_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //open search activity
                startActivity(new Intent(MyExpenseActivity.this, SearchExpenseActivity.class));
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new JanFragment(), "Jan");
        adapter.addFrag(new FebFragment(), "Feb");
        adapter.addFrag(new MarFragment(), "Mar");
        adapter.addFrag(new AprFragment(), "Apr");
        adapter.addFrag(new MayFragment(), "May");
        adapter.addFrag(new JunFragment(), "June");
        adapter.addFrag(new JulFragment(), "July");
        adapter.addFrag(new AugFragment(), "Aug");
        adapter.addFrag(new SeptFragment(), "Sept");
        adapter.addFrag(new OctFragment(), "Oct");
        adapter.addFrag(new NovFragment(), "Nov");
        adapter.addFrag(new DecFragment(), "Dec");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
