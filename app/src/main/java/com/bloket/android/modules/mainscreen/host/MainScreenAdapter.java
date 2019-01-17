package com.bloket.android.modules.mainscreen.host;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bloket.android.modules.mainscreen.components.blacklist.BlacklistFragment;
import com.bloket.android.modules.mainscreen.components.blocklogs.BlocklogsFragment;
import com.bloket.android.modules.mainscreen.components.contacts.ContactsFragment;
import com.bloket.android.modules.mainscreen.components.dialer.DialerFragment;
import com.bloket.android.modules.mainscreen.components.whitelist.WhitelistFragment;

public class MainScreenAdapter extends FragmentPagerAdapter {

    MainScreenAdapter(FragmentManager mFragmentManager) {
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