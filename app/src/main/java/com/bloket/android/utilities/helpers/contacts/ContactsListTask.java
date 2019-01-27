package com.bloket.android.utilities.helpers.contacts;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ContactsListTask extends AsyncTask {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private ArrayList<ContactsDataPair> mContactList;
    private ContactsResponse mResponse;

    public ContactsListTask(Context mContext, ContactsResponse mResponse) {
        this.mContext = mContext;
        this.mResponse = mResponse;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected Object doInBackground(Object[] mObjects) {

        // Set existing contact list to null
        mContactList = new ArrayList<>();

        // Fetch contacts
        ContentResolver mContactResolver = mContext.getContentResolver();
        Cursor mCursor = mContactResolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI, ContactsContract.Contacts.HAS_PHONE_NUMBER},
                ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0",
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        // Add item to contact list
        if (mCursor != null && mCursor.getCount() > 0)
            while (mCursor.moveToNext()) {

                // Fetch all contact numbers
                Cursor mNestedCursor = mContactResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID))},
                        ContactsContract.CommonDataKinds.Phone.TYPE + " ASC");

                // Add numbers to number list
                ArrayList<ContactsTypePair> mPhoneNumbers = new ArrayList<>();
                while (mNestedCursor != null && mNestedCursor.moveToNext()) {
                    mPhoneNumbers.add(new ContactsTypePair(
                            ContactsContract.CommonDataKinds.Phone.getTypeLabel(mContext.getResources(), mNestedCursor.getInt(mNestedCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)), "").toString(),
                            mNestedCursor.getString(mNestedCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    ));
                }
                mNestedCursor.close();

                // Add data to contact list
                mContactList.add(new ContactsDataPair(
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID)),
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)),
                        mPhoneNumbers,
                        0));
            }
        mCursor.close();

        // Alphabetically group the list
        char mPrev = ' ';
        for (int mCount = 0; mCount < mContactList.size(); mCount++) {
            ContactsDataPair mPair = mContactList.get(mCount);
            if (mPair.getName().charAt(0) != mPrev) {
                mContactList.add(mCount, new ContactsDataPair(null, mPair.getName().substring(0, 1).toUpperCase(), null, null, 1));
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

    public interface ContactsResponse {

        void onTaskCompletion(ArrayList<ContactsDataPair> mContactList);

    }
}