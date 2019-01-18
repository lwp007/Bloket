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

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private ArrayList<ContactsDataPair> mContactsList;
    private Context mContext;

    ContactsAdapter(Context mContext, ArrayList<ContactsDataPair> mContactsList) {
        this.mContext = mContext;
        this.mContactsList = mContactsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup mParent, int mViewType) {
        View mView = LayoutInflater.from(mParent.getContext()).inflate(R.layout.fm_main_screen_contacts_row, mParent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder mHolder, int mPosition) {
        ContactsDataPair mDataPair = mContactsList.get(mPosition);
        mHolder.mContactName.setText(mDataPair.getName());
        if (mDataPair.getPhotoUri() == null) {
            int mColor = new RandomColor().randomColor();
            mHolder.mContactImage.setImageResource(R.drawable.ic_contact_default);
            mHolder.mContactImage.setColorFilter(mColor);
            mHolder.mContactImage.setBorderWidth(0);
            mHolder.mContactFirstLetter.setVisibility(View.VISIBLE);
            if (isColorDark(mColor))
                mHolder.mContactFirstLetter.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            mHolder.mContactFirstLetter.setText(mDataPair.getName().substring(0, 1));
        } else {
            mHolder.mContactImage.setImageURI(Uri.parse(mDataPair.getPhotoUri()));
            mHolder.mContactFirstLetter.setVisibility(View.INVISIBLE);
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mContactName, mContactFirstLetter;
        CircularImageView mContactImage;

        MyViewHolder(View mView) {
            super(mView);
            mContactName = mView.findViewById(R.id.cfContactName);
            mContactFirstLetter = mView.findViewById(R.id.cfContactFirstLetter);
            mContactImage = mView.findViewById(R.id.cfContactImage);
        }
    }
}