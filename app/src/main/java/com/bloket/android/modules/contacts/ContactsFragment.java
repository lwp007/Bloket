package com.bloket.android.modules.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloket.android.R;
import com.bloket.android.utilities.datautil.ContactsListTask;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    private ContactsAdapter mAdapter;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, @Nullable ViewGroup mContainer, @Nullable Bundle mSavedInstance) {
        View mView = mInflater.inflate(R.layout.fm_main_screen_contacts, mContainer, false);


        final String permissionToCall = Manifest.permission.READ_CONTACTS;
        if (ActivityCompat.checkSelfPermission(getActivity(), permissionToCall) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permissionToCall}, 1);
        }

        final SearchView mSearchView = mView.findViewById(R.id.cfSearchView);
        final View mSearchBackground = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        mSearchBackground.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        mSearchView.setQueryHint("Search contacts");
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });

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
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent mIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                mIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivityForResult(mIntent, 1);
            }
        });

        final RecyclerView cfRecyclerView = mView.findViewById(R.id.cfRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        cfRecyclerView.setLayoutManager(mLayoutManager);
        cfRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ContactsListTask mAsyncTask = new ContactsListTask(getContext(), new ContactsListTask.ContactsResponse() {
            @Override
            public void onTaskCompletion(ArrayList<ContactsDataPair> mContactList) {
                mAdapter = new ContactsAdapter(getContext(), mContactList);
                cfRecyclerView.setAdapter(mAdapter);
            }
        });
        mAsyncTask.execute();
        return mView;
    }
}