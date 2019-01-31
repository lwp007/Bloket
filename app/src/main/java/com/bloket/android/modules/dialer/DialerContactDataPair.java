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

    void addPhoneNumber(DialerContactTypePair mNumber) {
        if (mPhoneNumbers != null) mPhoneNumbers.add(mNumber);
    }

    ArrayList<DialerContactTypePair> getPhoneNumbers() {
        return mPhoneNumbers;
    }
}