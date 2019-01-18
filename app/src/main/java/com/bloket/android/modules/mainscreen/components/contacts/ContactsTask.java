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
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)), 0));
            }
        mCursor.close();

        // Sort the list alphabetically
        Collections.sort(mContactList, new Comparator<ContactsDataPair>() {
            @Override
            public int compare(ContactsDataPair mNameOne, ContactsDataPair mNameTwo) {
                return mNameOne.getName().compareTo(mNameTwo.getName());
            }
        });

        // Alphabetically group the list
        char mPrev = ' ';
        for (int mCount = 0; mCount < mContactList.size(); mCount++) {
            ContactsDataPair mPair = mContactList.get(mCount);
            if (mPair.getName().charAt(0) != mPrev) {
                mContactList.add(mCount, new ContactsDataPair(mPair.getName().substring(0, 1).toUpperCase(), "", 1));
                mPrev = mPair.getName().charAt(0);
                mCount++;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object mObject) {
        mResponse.onTaskCompletion(mContactList);
    }
}