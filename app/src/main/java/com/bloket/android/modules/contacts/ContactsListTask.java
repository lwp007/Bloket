package com.bloket.android.modules.contacts;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

public class ContactsListTask extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private ContactsResponse mResponse;
    private ArrayList<ContactsDataPair> mContactList;

    ContactsListTask(Context mContext, ContactsResponse mResponse) {
        this.mContext = mContext;
        this.mResponse = mResponse;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected Void doInBackground(Void... mVoid) {
        try {

            // Set existing contact list to null
            mContactList = new ArrayList<>();

            // Fetch contacts
            ContentResolver mContactResolver = mContext.getContentResolver();
            ContentProviderClient mProviderClient = mContactResolver.acquireContentProviderClient(ContactsContract.Contacts.CONTENT_URI);
            Cursor mCursor = mProviderClient.query(ContactsContract.Contacts.CONTENT_URI,
                    new String[]{
                            ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI},
                    ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0",
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC");

            // Add item to contact list
            if (mCursor != null && mCursor.getCount() > 0) {
                while (mCursor.moveToNext()) {
                    mContactList.add(new ContactsDataPair(
                            mCursor.getString(0),
                            mCursor.getString(1),
                            mCursor.getString(2),
                            0));
                }
            }
            mCursor.close();
            mProviderClient.release();

            // Alphabetically group the list
            char mPrev = ' ';
            for (int mCount = 0; mCount < mContactList.size(); mCount++) {
                ContactsDataPair mPair = mContactList.get(mCount);
                if (mPair.getDisplayName().charAt(0) != mPrev) {
                    mContactList.add(mCount, new ContactsDataPair(null, mPair.getDisplayName().substring(0, 1).toUpperCase(), null, 1));
                    mPrev = mPair.getDisplayName().charAt(0);
                    mCount++;
                }
            }
        } catch (Exception mException) {
            Log.d("BLOKET_LOGS", "ContactsListTask: Error fetching contacts for contact list, Details: " + mException.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void mVoid) {
        mResponse.onTaskCompletion(mContactList);
    }

    public interface ContactsResponse {

        void onTaskCompletion(ArrayList<ContactsDataPair> mContactList);

    }
}