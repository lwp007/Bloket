package com.bloket.android.modules.mainscreen.components.contacts;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactsTask extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private ArrayList<ContactsDataPair> mContactList;
    private ContactsResponse mResponse;

    ContactsTask(Context mContext, ContactsResponse mResponse) {
        this.mContext = mContext;
        this.mResponse = mResponse;
    }

    @Override
    protected Object doInBackground(Object[] mObjects) {

        // Set existing contact list to null
        mContactList = new ArrayList<>();

        // Fetch contacts
        ContentResolver mContactResolver = mContext.getContentResolver();
        Cursor mCursor = mContactResolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI}, null, null, null);
        if (mCursor == null) return null;
        if (mCursor.getCount() > 0)
            while (mCursor.moveToNext()) {
                mContactList.add(new ContactsDataPair(
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))));
            }
        mCursor.close();

        // Sort the list alphabetically
        Collections.sort(mContactList, new Comparator<ContactsDataPair>() {
            @Override
            public int compare(ContactsDataPair mNameOne, ContactsDataPair mNameTwo) {
                return mNameOne.getName().compareTo(mNameTwo.getName());
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Object mObject) {
        mResponse.onTaskCompletion(mContactList);
    }
}