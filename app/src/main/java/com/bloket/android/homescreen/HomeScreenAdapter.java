package com.bloket.android.homescreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bloket.android.modules.blacklist.BlacklistFragment;
import com.bloket.android.modules.blocklogs.BlocklogsFragment;
import com.bloket.android.modules.contacts.ContactsFragment;
import com.bloket.android.modules.dialer.DialerFragment;
import com.bloket.android.modules.whitelist.WhitelistFragment;

public class HomeScreenAdapter extends FragmentPagerAdapter {

    HomeScreenAdapter(FragmentManager mFragmentManager) {
        super(mFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DialerFragment.newInstance();
            case 1:
                return ContactsFragment.newInstance();
            case 2:
                return BlocklogsFragment.newInstance();
            case 3:
                return BlacklistFragment.newInstance();
            case 4:
                return WhitelistFragment.newInstance();
            default:
                return DialerFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}