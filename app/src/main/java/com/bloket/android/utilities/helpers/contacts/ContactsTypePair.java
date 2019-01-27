package com.bloket.android.utilities.helpers.contacts;

public class ContactsTypePair {

    private String mTypeLabel, mPhoneNumber;

    ContactsTypePair(String mTypeLabel, String mPhoneNumber) {
        this.mTypeLabel = mTypeLabel;
        this.mPhoneNumber = mPhoneNumber;
    }

    private String getTypeLabel() {
        return mTypeLabel;
    }

    private String getPhoneNumber() {
        return mPhoneNumber;
    }
}
