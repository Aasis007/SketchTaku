package com.example.laptop.sketchtaku.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.laptop.sketchtaku.Fragment.CategoryFragment;
import com.example.laptop.sketchtaku.Fragment.MostPopularFragment;
import com.example.laptop.sketchtaku.Fragment.RecentFragment;
import com.example.laptop.sketchtaku.Home;

/**
 * Created by Laptop on 3/12/2018.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    public Context context;
    public MyFragmentAdapter(FragmentManager fm, Home home) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return MostPopularFragment.getInstance();
        else if (position==1)
            return CategoryFragment.getInstance();
        else if (position ==2)
                return RecentFragment.getInstance();
        else
            return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Most Popular";

            case 1:
                return "Category";

            case 2:
                return "Recent";

        }
        return "";
    }
}
