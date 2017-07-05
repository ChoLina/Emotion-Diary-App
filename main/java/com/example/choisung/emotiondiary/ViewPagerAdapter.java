package com.example.choisung.emotiondiary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.choisung.emotiondiary.fragment.DiaryFragment;
import com.example.choisung.emotiondiary.fragment.EntriesFragment;
import com.example.choisung.emotiondiary.fragment.SettingFragment;
import com.example.choisung.emotiondiary.fragment.StaticsFragment;

/**
 * Created by ChoISung on 2017-05-16.
 */

class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                return DiaryFragment.newInstance();
            case 1:
                return EntriesFragment.newInstance();
            case 2:
                return StaticsFragment.newInstance();
            case 3:
                return SettingFragment.newInstance();
        }
        return null;
    }

    private static int PAGE_NUMBER = 4;

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    public CharSequence getPageTitle(int position){
        return null;
    }
}
