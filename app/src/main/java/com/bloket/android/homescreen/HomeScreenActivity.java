package com.bloket.android.homescreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.bloket.android.R;

public class HomeScreenActivity extends AppCompatActivity {


    private MaterialMenuDrawable mMaterialMenu;
    private FloatingActionButton mFloatingButton;

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

        // Floating action button
        mFloatingButton = findViewById(R.id.mpFabButton);

        // Set up viewpager
        setViewPager();
    }

    private static void hideSoftKeyboard(Context mContext, View mView) {
        InputMethodManager mManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (mManager != null) mManager.hideSoftInputFromWindow(mView.getWindowToken(), 0);

    }

    @SuppressWarnings("ConstantConditions")
    private void setViewPager() {

        // Set up pager adapter
        final ViewPager mViewPager = findViewById(R.id.mpViewPager);
        HomeScreenAdapter mAdapter = new HomeScreenAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int mPosition, float mPosOffset, int mPosOffsetPixels) {
                hideSoftKeyboard(getApplicationContext(), mViewPager);
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int mPosition) {
                if (mPosition == 1) {
                    if (mFloatingButton != null) mFloatingButton.setVisibility(View.VISIBLE);
                } else {
                    if (mFloatingButton != null) mFloatingButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int mState) {
            }
        });

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