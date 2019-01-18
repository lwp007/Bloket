package com.bloket.android.modules.mainscreen.components.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloket.android.R;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

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

        final RecyclerView cfRecyclerView = mView.findViewById(R.id.cfRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        cfRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        cfRecyclerView.setLayoutManager(mLayoutManager);
        cfRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ContactsTask mAsyncTask = new ContactsTask(getContext(), new ContactsResponse() {
            @Override
            public void onTaskCompletion(ArrayList<ContactsDataPair> mContactList) {
                ContactsAdapter mAdapter = new ContactsAdapter(mContactList);
                cfRecyclerView.setAdapter(mAdapter);
            }
        });
        mAsyncTask.execute();
        return mView;
    }
}