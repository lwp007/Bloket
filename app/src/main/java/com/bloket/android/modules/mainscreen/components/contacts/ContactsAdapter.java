package com.bloket.android.modules.mainscreen.components.contacts;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bloket.android.R;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter {

    private ArrayList<ContactsDataPair> mContactsList;
    private Context mContext;

    ContactsAdapter(Context mContext, ArrayList<ContactsDataPair> mContactsList) {
        this.mContext = mContext;
        this.mContactsList = mContactsList;
    }

    @Override
    public int getItemViewType(int mPosition) {
        return mContactsList.get(mPosition).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup mParent, int mViewType) {
        if (mViewType == 0) {
            View mView = LayoutInflater.from(mParent.getContext()).inflate(R.layout.fm_main_screen_contacts_row, mParent, false);
            return new ContactsViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(mParent.getContext()).inflate(R.layout.fm_main_screen_contacts_header, mParent, false);
            return new HeaderViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, int mPosition) {
        if (mHolder instanceof ContactsViewHolder) {
            ContactsViewHolder mContactsHolder = ((ContactsViewHolder) mHolder);
            ContactsDataPair mDataPair = mContactsList.get(mPosition);
            mContactsHolder.mContactName.setText(mDataPair.getName());
            if (mDataPair.getPhotoUri() == null) {
                int mColor = new RandomColor().randomColor();
                mContactsHolder.mContactImage.setImageResource(R.drawable.ic_contact_default);
                mContactsHolder.mContactImage.setColorFilter(mColor);
                mContactsHolder.mContactImage.setBorderWidth(0);
                mContactsHolder.mContactFirstLetter.setVisibility(View.VISIBLE);
                if (isColorDark(mColor))
                    mContactsHolder.mContactFirstLetter.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                mContactsHolder.mContactFirstLetter.setText(mDataPair.getName().substring(0, 1));
            } else {
                mContactsHolder.mContactImage.setImageURI(Uri.parse(mDataPair.getPhotoUri()));
                mContactsHolder.mContactFirstLetter.setVisibility(View.INVISIBLE);
            }
        } else if (mHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) mHolder).mHeaderText.setText(mContactsList.get(mPosition).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return !(darkness < 0.5);
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView mContactName, mContactFirstLetter;
        CircularImageView mContactImage;

        ContactsViewHolder(View mView) {
            super(mView);
            mContactName = mView.findViewById(R.id.cfContactName);
            mContactFirstLetter = mView.findViewById(R.id.cfContactFirstLetter);
            mContactImage = mView.findViewById(R.id.cfContactImage);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView mHeaderText;

        HeaderViewHolder(View mView) {
            super(mView);
            mHeaderText = mView.findViewById(R.id.cfHeaderText);
        }
    }
}