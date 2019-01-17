package com.bloket.android.modules.mainscreen.components.whitelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloket.android.R;

public class WhitelistFragment extends Fragment {

    public static WhitelistFragment newInstance() {
        return new WhitelistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, @Nullable ViewGroup mContainer, @Nullable Bundle mSavedInstance) {
        View mView = mInflater.inflate(R.layout.fm_main_screen_whitelist, mContainer, false);
        return mView;
    }
}