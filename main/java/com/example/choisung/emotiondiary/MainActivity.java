package com.example.choisung.emotiondiary;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * View pager
     */
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private TabLayout mTab;
    private int[] tabIcons = {
            R.drawable.ic_insert_invitation_black_24dp,
            R.drawable.ic_format_list_bulleted_black_24dp,
            R.drawable.ic_equalizer_black_24dp,
            R.drawable.ic_settings_black_24dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         ViewPager
         */
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        mViewPager = (ViewPager) findViewById(R.id.ViewPager_diary);
        mTab = (TabLayout) findViewById(R.id.Tab_diary);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager());
        setSupportActionBar(toolbar);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        setTabIcons();
    }

    /**
     TabIcons
     */
    private void setTabIcons() {
        mTab.getTabAt(0).setIcon(tabIcons[0]);
        mTab.getTabAt(1).setIcon(tabIcons[1]);
        mTab.getTabAt(2).setIcon(tabIcons[2]);
        mTab.getTabAt(3).setIcon(tabIcons[3]);
    }

}