package com.bloket.android.modules.dialer;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

public class DialerContactTask extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private ArrayList<DialerContactDataPair> mContactList;
    private DialerContactTask.ContactsResponse mResponse;

    DialerContactTask(Context mContext, DialerContactTask.ContactsResponse mResponse) {
        this.mContext = mContext;
        this.mResponse = mResponse;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected Void doInBackground(Void... mVoid) {
        try {

            // Initialize contact list
            mContactList = new ArrayList<>();

            // Fetch contacts
            ContentResolver mContactResolver = mContext.getContentResolver();
            ContentProviderClient mProviderClient = mContactResolver.acquireContentProviderClient(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            Cursor mCursor = mProviderClient.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.LABEL},
                    ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " > 0",
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            // Add data to contact list
            if (mCursor != null && mCursor.getCount() > 0) {
                String mLabel, mCurrentId, mLastId = "";
                int mCount = -1;
                while (mCursor.moveToNext()) {
                    mCurrentId = mCursor.getString(0);
                    mLabel = ContactsContract.CommonDataKinds.Phone.getTypeLabel(mContext.getResources(), mCursor.getInt(4), mCursor.getString(5)).toString();
                    if (!mLastId.equals(mCurrentId)) {
                        ArrayList<DialerContactTypePair> mPhoneNumber = new ArrayList<>();
                        mPhoneNumber.add(new DialerContactTypePair(mCursor.getString(3), mLabel));
                        mContactList.add(new DialerContactDataPair(
                                mCurrentId,
                                mCursor.getString(1),
                                mCursor.getString(2),
                                mPhoneNumber));
                        mCount++;
                    } else {
                        mContactList.get(mCount).addPhoneNumber(new DialerContactTypePair(mCursor.getString(3), mLabel));
                    }
                    mLastId = mCurrentId;
                }
            }
            mCursor.close();
            mProviderClient.release();
        } catch (Exception mException) {
            Log.d("BLOKET_LOGS", "DialerContactTask: Error fetching contacts for dialer, Details: " + mException.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void mVoid) {
        mResponse.onTaskCompletion(mContactList);
    }

    public interface ContactsResponse {

        void onTaskCompletion(ArrayList<DialerContactDataPair> mContactList);

    }
}