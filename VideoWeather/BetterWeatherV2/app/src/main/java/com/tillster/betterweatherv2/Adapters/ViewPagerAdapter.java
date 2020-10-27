package com.tillster.betterweatherv2.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter  extends FragmentPagerAdapter
{
    public static List<Fragment> fragmentList = new ArrayList<>();
    public static List <String> fragmentTitles = new ArrayList<>();
    //constructor
    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public void addFragment( Fragment fragment, String title)
    {
        fragmentList.add(fragment);
        fragmentTitles.add(title);
    }
}
