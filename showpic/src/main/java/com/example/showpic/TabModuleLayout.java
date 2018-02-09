package com.example.showpic;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 524202 on 2017/12/28.
 */

public class TabModuleLayout extends FrameLayout {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public TabModuleLayout(Context context) {
        this(context, null);
    }

    public TabModuleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabModuleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.tab_module_layout, this, true);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#00bbc0"));
    }

    class ModuleAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private String[] tabs;

        public ModuleAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] tabs) {
            super(fm);
            this.fragments = fragments;
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
