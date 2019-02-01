package com.bloket.android.modules.contacts;

class ContactsDataPair {

    private String mContactId, mDisplayName, mPhotoUri;
    private int mRowType;

    ContactsDataPair(String mContactId, String mDisplayName, String mPhotoUri, int mRowType) {
        this.mContactId = mContactId;
        this.mDisplayName = mDisplayName;
        this.mPhotoUri = mPhotoUri;
        this.mRowType = mRowType;
    }

    String getContactId() {
        return mContactId;
    }

    String getDisplayName() {
        return mDisplayName;
    }

    String getHeaderName() {
        return mDisplayName;
    }

    String getPhotoUri() {
        return mPhotoUri;
    }

    int getRowType() {
        return mRowType;
    }
}