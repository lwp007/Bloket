package com.bloket.android.modules.mainscreen.components.contacts;

import java.util.ArrayList;

public interface ContactsResponse {

    void onTaskCompletion(ArrayList<ContactsDataPair> mContactList);

}