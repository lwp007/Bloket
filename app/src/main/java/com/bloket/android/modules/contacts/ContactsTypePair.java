package com.bloket.android.modules.contacts;

public class ContactsTypePair {

    private String mTypeLabel, mPhoneNumber;

    ContactsTypePair(String mTypeLabel, String mPhoneNumber) {
        this.mTypeLabel = mTypeLabel;
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getTypeLabel() {
        return mTypeLabel;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }
}
