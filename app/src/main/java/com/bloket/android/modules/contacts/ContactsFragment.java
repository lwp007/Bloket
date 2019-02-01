package com.bloket.android.modules.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloket.android.R;
import com.bloket.android.utilities.helpers.ui.UIHelper;

public class ContactsFragment extends Fragment {

    private ContactsAdapter mAdapter;
    private final int PERM_READ_CONTACTS = 0;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, @Nullable ViewGroup mContainer, @Nullable Bundle mSavedInstance) {
        View mView = mInflater.inflate(R.layout.fm_main_screen_contacts, mContainer, false);
        mSearchView = mView.findViewById(R.id.cfSearchView);
        View mSearchBackground = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        mSearchBackground.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        mSearchView.setQueryHint("Search contacts");
        mSearchView.setOnClickListener(mTempView -> mSearchView.setIconified(false));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String mSearchText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String mSearchText) {
                if (mAdapter != null) mAdapter.getFilter().filter(mSearchText);
                return false;
            }
        });

        FloatingActionButton mFloatingButton = getActivity().findViewById(R.id.mpFabButton);
        mFloatingButton.setImageResource(R.drawable.ic_action_add);
        mFloatingButton.setOnClickListener(mTempView -> {
            Intent mIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
            mIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            startActivityForResult(mIntent, 1);
        });

        mRecyclerView = mView.findViewById(R.id.cfRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERM_READ_CONTACTS);
            return mView;
        }
        loadContactList();
        return mView;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onRequestPermissionsResult(int mRequestCode, @NonNull String[] mPermissions, @NonNull int[] mResult) {
        switch (mRequestCode) {
            case PERM_READ_CONTACTS:
                if (mResult.length > 0 && mResult[0] == PackageManager.PERMISSION_GRANTED)
                    loadContactList();
                else {
                    boolean mShowRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);
                    if (!mShowRationale)
                        UIHelper.showPermissionSnack(getActivity(), "read contacts");
                    else
                        UIHelper.showImageSnack(getActivity(), "Permission denied.");
                }
                break;

            default:
                break;
        }
        super.onRequestPermissionsResult(mRequestCode, mPermissions, mResult);
    }

    private void loadContactList() {
        ContactsListTask mAsyncTask = new ContactsListTask(getContext(), mContactList -> {
            mAdapter = new ContactsAdapter(getContext(), mContactList);
            mRecyclerView.setAdapter(mAdapter);
            mSearchView.setQueryHint("Search among your " + mContactList.size() + " contacts");
        });
        mAsyncTask.execute();
    }
}