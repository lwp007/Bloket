package com.bloket.android.modules.contacts;

import java.util.ArrayList;

public class ContactsDataPair {

    private ArrayList<ContactsTypePair> mPhoneNumbers;
    private String mContactId, mName, mPhotoUri;
    private int mRowType;

    ContactsDataPair(String mContactId, String mName, String mPhotoUri, ArrayList<ContactsTypePair> mPhoneNumbers, int mRowType) {
        this.mContactId = mContactId;
        this.mName = mName;
        this.mPhotoUri = mPhotoUri;
        this.mPhoneNumbers = mPhoneNumbers;
        this.mRowType = mRowType;
    }

    public String getContactId() {
        return mContactId;
    }

    public String getName() {
        return mName;
    }

    public String getHeaderName() {
        return mName;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public ArrayList<ContactsTypePair> getPhoneNumbers() {
        return mPhoneNumbers;
    }

    public int getRowType() {
        return mRowType;
    }
}