package com.bloket.android.modules.contacts;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bloket.android.R;
import com.bloket.android.utilities.helpers.contacts.ContactsDataPair;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter implements Filterable {

    private ArrayList<ContactsDataPair> mContactsList;
    private Context mContext;
    private ArrayList<ContactsDataPair> mFilteredList;
    private String mSearchText = "";
    private StyleSpan mBoldSpan;

    ContactsAdapter(Context mContext, ArrayList<ContactsDataPair> mContactsList) {
        this.mContext = mContext;
        this.mContactsList = mContactsList;
        this.mFilteredList = mContactsList;
        this.mBoldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
    }

    @Override
    public int getItemViewType(int mPosition) {
        return mFilteredList.get(mPosition).getRowType();
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
            ContactsDataPair mDataPair = mFilteredList.get(mPosition);

            // Highlight filtered text
            String mDisplayName = mDataPair.getName();
            String mLowerCaseName = mDisplayName.toLowerCase();
            if (mLowerCaseName.contains(mSearchText)) {
                int mPosStr = mLowerCaseName.indexOf(mSearchText);
                int mPosEnd = mPosStr + mSearchText.length();
                Spannable mSpanString = Spannable.Factory.getInstance().newSpannable(mDisplayName);
                mSpanString.setSpan(mBoldSpan, mPosStr, mPosEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                mContactsHolder.mContactName.setText(mSpanString);
            } else {
                mContactsHolder.mContactName.setText(mDisplayName);
            }

            if (mDataPair.getPhotoUri() == null) {
                mContactsHolder.mContactFirstLetter.setVisibility(View.VISIBLE);
                mContactsHolder.mContactFirstLetter.setText(mDataPair.getName().substring(0, 1));
                mContactsHolder.mContactImage.setImageResource(R.drawable.ic_contact_default);
            } else {
                mContactsHolder.mContactFirstLetter.setVisibility(View.INVISIBLE);
                mContactsHolder.mContactImage.setImageURI(Uri.parse(mDataPair.getPhotoUri()));
            }
        } else {
            if (mPosition >= 0)
                ((HeaderViewHolder) mHolder).mHeaderText.setText(mFilteredList.get(mPosition).getHeaderName());
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence mCharSequence) {
                mSearchText = mCharSequence.toString().toLowerCase();
                if (mSearchText.isEmpty()) {
                    mFilteredList = mContactsList;
                } else {
                    // Normal search
                    ArrayList<ContactsDataPair> mList = new ArrayList<>();
                    for (ContactsDataPair mPair : mContactsList) {
                        if (mPair.getRowType() == 0 && mPair.getName().toLowerCase().contains(mSearchText.toLowerCase()))
                            mList.add(mPair);
                    }
                    mFilteredList = mList;
                }
                FilterResults mFilteredResults = new FilterResults();
                mFilteredResults.values = mFilteredList;
                return mFilteredResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ContactsDataPair>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView mContactName, mContactFirstLetter;
        CircularImageView mContactImage;

        ContactsViewHolder(View mView) {
            super(mView);
            mContactName = mView.findViewById(R.id.drContactName);
            mContactFirstLetter = mView.findViewById(R.id.drContactFirstLetter);
            mContactImage = mView.findViewById(R.id.drContactImage);
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