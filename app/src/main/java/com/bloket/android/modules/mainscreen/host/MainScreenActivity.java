package com.bloket.android.modules.mainscreen.host;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.bloket.android.R;

public class MainScreenActivity extends AppCompatActivity {


    private MaterialMenuDrawable mMaterialMenu;

    @Override
    protected void onCreate(@Nullable Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.ac_main_screen);

        // Set up toolbar
        Toolbar mToolbar = findViewById(R.id.mpToolbar);
        if (mToolbar != null) setSupportActionBar(mToolbar);

        // Set up hamburger menu
        mMaterialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        mMaterialMenu.setTransformationDuration(350);
        if (mToolbar != null) mToolbar.setNavigationIcon(mMaterialMenu);

        // Set up viewpager
        setViewPager();
    }

    @SuppressWarnings("ConstantConditions")
    private void setViewPager() {
        // Set up pager adapter
        final ViewPager mViewPager = findViewById(R.id.mpViewPager);
        MainScreenAdapter mAdapter = new MainScreenAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        // Set up tabs
        TabLayout mTabLayout = findViewById(R.id.mpTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_dialer);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_contacts);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_blocklogs);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_tab_blacklist);
        mTabLayout.getTabAt(4).setIcon(R.drawable.ic_tab_whitelist);

        // Set up tab icon color
        mTabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        mTabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorWhitePressed), PorterDuff.Mode.MULTIPLY);
        mTabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.colorWhitePressed), PorterDuff.Mode.MULTIPLY);
        mTabLayout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.colorWhitePressed), PorterDuff.Mode.MULTIPLY);
        mTabLayout.getTabAt(4).getIcon().setColorFilter(getResources().getColor(R.color.colorWhitePressed), PorterDuff.Mode.MULTIPLY);

        // Set up tab listener
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab mTab) {
                mTab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab mTab) {
                mTab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhitePressed), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab mTab) {

            }
        });

    }
}