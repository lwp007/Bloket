package com.bloket.android.modules.contacts;

public class ContactsDataPair {

    private String mId, mName, mPhotoUri;
    private int mType;

    public ContactsDataPair(String mId, String mName, String mPhotoUri, int mType) {
        this.mId = mId;
        this.mName = mName;
        this.mPhotoUri = mPhotoUri;
        this.mType = mType;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    int getType() {
        return mType;
    }
}