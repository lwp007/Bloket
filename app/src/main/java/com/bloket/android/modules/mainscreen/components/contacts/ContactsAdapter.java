package com.bloket.android.modules.mainscreen.components.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloket.android.R;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private ArrayList<ContactsDataPair> mContactsList;

    public ContactsAdapter(ArrayList<ContactsDataPair> mContactsList) {
        this.mContactsList = mContactsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup mParent, int mViewType) {
        View mView = LayoutInflater.from(mParent.getContext()).inflate(R.layout.fm_main_screen_contacts_row, mParent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder mHolder, int mPosition) {
        ContactsDataPair mDataPair = mContactsList.get(mPosition);
        mHolder.mContactName.setText(mDataPair.getName());
        mHolder.mContactImage.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mContactName;
        ImageView mContactImage;

        MyViewHolder(View mView) {
            super(mView);
            mContactName = mView.findViewById(R.id.cfContactName);
            mContactImage = mView.findViewById(R.id.cfContactImage);
        }
    }
}