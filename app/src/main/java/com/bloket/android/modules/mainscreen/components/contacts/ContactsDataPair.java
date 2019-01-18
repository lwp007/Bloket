package com.bloket.android.modules.mainscreen.components.contacts;

public class ContactsDataPair {

    private String mName, mPhotoUri;

    ContactsDataPair(String mName, String mPhotoUri) {
        this.mName = mName;
        this.mPhotoUri = mPhotoUri;
    }

    String getName() {
        return mName;
    }

    String getPhotoUri() {
        return mPhotoUri;
    }

}