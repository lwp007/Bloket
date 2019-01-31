package com.bloket.android.modules.dialer;

class DialerContactTypePair {

    private String mPhoneNumber, mPhoneLabel;

    DialerContactTypePair(String mPhoneNumber, String mPhoneLabel) {
        this.mPhoneLabel = mPhoneLabel;
        this.mPhoneNumber = mPhoneNumber;
    }

    String getPhoneLabel() {
        return mPhoneLabel;
    }

    String getPhoneNumber() {
        return mPhoneNumber;
    }
}