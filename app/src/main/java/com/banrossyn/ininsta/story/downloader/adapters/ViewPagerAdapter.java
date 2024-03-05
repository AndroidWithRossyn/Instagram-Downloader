package com.banrossyn.ininsta.story.downloader.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int arg0) {
        return this.mFragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        this.mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}