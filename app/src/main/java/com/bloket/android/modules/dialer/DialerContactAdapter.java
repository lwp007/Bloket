package com.bloket.android.modules.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bloket.android.R;
import com.bloket.android.homescreen.HomeScreenActivity;
import com.bloket.android.utilities.helpers.telephone.TelephoneHelper;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialerContactAdapter extends RecyclerView.Adapter implements Filterable {

    private Context mContext;
    private ArrayList<DialerContactDataPair> mContactsList;
    private ArrayList<DialerContactDataPair> mFilteredList;
    private String mSearchRegex = "";
    private StyleSpan mBoldSpan;
    private final int FOOTER_VIEW = 1;

    DialerContactAdapter(Context mContext, ArrayList<DialerContactDataPair> mContactsList) {
        this.mContext = mContext;
        this.mContactsList = mContactsList;
        this.mFilteredList = mContactsList;
        this.mBoldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
    }

    @Override
    public int getItemViewType(int mPosition) {
        if (mPosition == mFilteredList.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(mPosition);
    }

    @SuppressWarnings("NullableProblems")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup mParent, int mViewType) {
        View mView;
        if (mViewType == FOOTER_VIEW) {
            mView = LayoutInflater.from(mParent.getContext()).inflate(R.layout.fm_dialer_contact_empty, mParent, false);
            return new FooterViewHolder(mView);
        } else {
            mView = LayoutInflater.from(mParent.getContext()).inflate(R.layout.fm_dialer_contact_row, mParent, false);
            return new ContactsViewHolder(mView);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mHolder, int mPosition) {
        if (mHolder instanceof DialerContactAdapter.ContactsViewHolder) {
            ContactsViewHolder mContactsHolder = ((ContactsViewHolder) mHolder);
            DialerContactDataPair mDataPair = mFilteredList.get(mPosition);

            // Highlight filtered text
            String mDisplayName = mDataPair.getDisplayName();
            String mLowerCaseName = mDisplayName.toLowerCase();
            int mMatchPosition[] = getHighlightPosition(mLowerCaseName);
            if (mMatchPosition[0] != -1) {
                Spannable mSpanString = Spannable.Factory.getInstance().newSpannable(mDisplayName);
                mSpanString.setSpan(mBoldSpan, mMatchPosition[0], mMatchPosition[1], Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                mContactsHolder.mContactName.setText(mSpanString);
            } else {
                mContactsHolder.mContactName.setText(mDisplayName);
            }

            if (mDataPair.getPhotoUri() == null) {
                mContactsHolder.mContactFirstLetter.setVisibility(View.VISIBLE);
                mContactsHolder.mContactFirstLetter.setText(mDataPair.getDisplayName().substring(0, 1));
                mContactsHolder.mContactImage.setImageResource(R.drawable.ic_contact_default);
            } else {
                mContactsHolder.mContactFirstLetter.setVisibility(View.INVISIBLE);
                mContactsHolder.mContactImage.setImageURI(Uri.parse(mDataPair.getPhotoUri()));
            }

            // Add all phone numbers to row
            mContactsHolder.mPhoneNumbers.removeAllViews();
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int mCount = 0; mCount < mDataPair.getPhoneNumbers().size(); mCount++) {
                if (mInflater != null) {
                    final String mNumber = mDataPair.getPhoneNumbers().get(mCount).getPhoneNumber().replaceAll("[^0-9]", "");
                    View mView = mInflater.inflate(R.layout.fm_dialer_contact_number, null, false);
                    TextView mPhoneNumber = mView.findViewById(R.id.drPhoneNumber);

                    // Highlight filtered text
                    int mMatchPos[] = getHighlightPosition(mNumber);
                    if (mMatchPos[0] != -1) {
                        Spannable mSpanString = Spannable.Factory.getInstance().newSpannable(mNumber);
                        mSpanString.setSpan(mBoldSpan, mMatchPos[0], mMatchPos[1], Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        mPhoneNumber.setText(mSpanString);
                    } else {
                        mPhoneNumber.setText(mNumber);
                    }

                    TextView mPhoneLabel = mView.findViewById(R.id.drPhoneLabel);
                    mPhoneLabel.setText(mDataPair.getPhoneNumbers().get(mCount).getPhoneLabel());
                    RelativeLayout mPhoneContainer = mView.findViewById(R.id.drPhoneContainer);
                    mPhoneContainer.setOnClickListener(mTelView -> TelephoneHelper.placeCall((HomeScreenActivity) mContext, mNumber));
                    mContactsHolder.mPhoneNumbers.addView(mView);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mSearchRegex.length() < 9)
            return mFilteredList.size();
        return mFilteredList.size() + 1;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence mCharSequence) {
                mSearchRegex = mCharSequence.toString().toLowerCase();
                if (mSearchRegex.isEmpty()) {
                    mFilteredList = mContactsList;
                } else {

                    // T9 Contact search
                    ArrayList<DialerContactDataPair> mList = new ArrayList<>();
                    for (DialerContactDataPair mPair : mContactsList) {
                        String mNameWords[] = mPair.getSearchText().split(" ");
                        for (String mWords : mNameWords) {
                            if (mWords.toLowerCase().matches(mSearchRegex)) {
                                mList.add(mPair);
                                break;
                            }
                        }
                    }
                    mFilteredList = mList;
                }
                FilterResults mFilteredResults = new FilterResults();
                mFilteredResults.values = mFilteredList;
                return mFilteredResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence mCharSequence, FilterResults mFilterResult) {
                mFilteredList = (ArrayList<DialerContactDataPair>) mFilterResult.values;
                notifyDataSetChanged();
            }
        };
    }

    private int[] getHighlightPosition(String mText) {
        Pattern mPattern = Pattern.compile(mSearchRegex.substring(0, mSearchRegex.length() - 2));
        String mNameWords[] = mText.split(" ");
        int mWordLength = 0;
        for (String mNameWord : mNameWords) {
            Matcher mMatcher = mPattern.matcher(mNameWord);
            if (mMatcher.find()) {
                return new int[]{mWordLength + mMatcher.start(), mWordLength + mMatcher.end()};
            }
            mWordLength = mNameWord.length() + 1;
        }
        return new int[]{-1};
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircularImageView mContactImage;
        TextView mContactName, mContactFirstLetter;
        LinearLayout mPhoneNumbers;

        ContactsViewHolder(View mView) {
            super(mView);
            mContactImage = mView.findViewById(R.id.drContactImage);
            mContactFirstLetter = mView.findViewById(R.id.drContactFirstLetter);
            mContactName = mView.findViewById(R.id.drContactName);
            mPhoneNumbers = mView.findViewById(R.id.drPhoneNumbers);

            // Set click listeners
            mContactImage.setOnClickListener(this);
            mContactFirstLetter.setOnClickListener(this);
        }

        @Override
        public void onClick(View mView) {
            Intent mIntent = new Intent(Intent.ACTION_VIEW);
            mIntent.setData(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, mFilteredList.get(getAdapterPosition()).getContactId()));
            mContext.startActivity(mIntent);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mNewContact, mAddContact, mSendMessage;

        FooterViewHolder(View mView) {
            super(mView);
            mNewContact = mView.findViewById(R.id.drNewContact);
            mAddContact = mView.findViewById(R.id.drAddContact);
            mSendMessage = mView.findViewById(R.id.drSendMessage);

            // Set click listeners
            mNewContact.setOnClickListener(this);
            mAddContact.setOnClickListener(this);
            mSendMessage.setOnClickListener(this);
        }

        @Override
        public void onClick(View mView) {
            switch (mView.getId()) {
                case R.id.drNewContact:
                    Intent mNewIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    mNewIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    mContext.startActivity(mNewIntent);
                    break;

                case R.id.drAddContact:
                    // TODO: Show contact picker
                    break;

                case R.id.drSendMessage:
                    Intent mMsgIntent = new Intent(Intent.ACTION_VIEW);
                    mMsgIntent.setData(Uri.parse("sms:"));
                    mContext.startActivity(mMsgIntent);
                    break;

                default:
                    Intent mIntent = new Intent(Intent.ACTION_VIEW);
                    mIntent.setData(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, mFilteredList.get(getAdapterPosition()).getContactId()));
                    mContext.startActivity(mIntent);
                    break;
            }
        }
    }
}