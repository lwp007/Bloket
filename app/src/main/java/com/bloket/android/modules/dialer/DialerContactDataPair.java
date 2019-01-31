package com.bloket.android.modules.dialer;

import java.util.ArrayList;

class DialerContactDataPair {

    private String mContactId, mDisplayName, mPhotoUri;
    private ArrayList<DialerContactTypePair> mPhoneNumbers;

    DialerContactDataPair(String mContactId, String mDisplayName, String mPhotoUri, ArrayList<DialerContactTypePair> mPhoneNumbers) {
        this.mContactId = mContactId;
        this.mDisplayName = mDisplayName;
        this.mPhotoUri = mPhotoUri;
        this.mPhoneNumbers = mPhoneNumbers;
    }

    String getContactId() {
        return mContactId;
    }

    String getDisplayName() {
        return mDisplayName;
    }

    String getPhotoUri() {
        return mPhotoUri;
    }

    String getSearchText() {
        StringBuilder mSearchText = new StringBuilder(mDisplayName);
        for (int mCount = 0; mCount < mPhoneNumbers.size(); mCount++)
            mSearchText.append(" ").append(mPhoneNumbers.get(mCount).getPhoneNumber().replaceAll("[^0-9]", ""));
        return mSearchText.toString();
    }

    void addPhoneNumber(DialerContactTypePair mNumber) {
        if (mPhoneNumbers != null) mPhoneNumbers.add(mNumber);
    }

    ArrayList<DialerContactTypePair> getPhoneNumbers() {
        return mPhoneNumbers;
    }
}