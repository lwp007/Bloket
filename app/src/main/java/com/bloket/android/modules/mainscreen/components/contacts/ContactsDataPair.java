package com.bloket.android.modules.mainscreen.components.contacts;

class ContactsDataPair {

    private String mName, mPhotoUri;
    private int mType;

    ContactsDataPair(String mName, String mPhotoUri, int mType) {
        this.mName = mName;
        this.mPhotoUri = mPhotoUri;
        this.mType = mType;
    }

    String getName() {
        return mName;
    }

    String getPhotoUri() {
        return mPhotoUri;
    }

    int getType() {
        return mType;
    }
}