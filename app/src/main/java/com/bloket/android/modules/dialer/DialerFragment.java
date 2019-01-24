package com.bloket.android.modules.dialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bloket.android.R;
import com.bloket.android.modules.contacts.ContactsAdapter;
import com.bloket.android.modules.contacts.ContactsDataPair;
import com.bloket.android.utilities.datautil.ContactsListTask;

import java.util.ArrayList;

public class DialerFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private Button dpKeyCall;
    private EditText etPhoneNumber;
    private LinearLayout drKeyGrid;
    private ContactsAdapter mAdapter;

    public static DialerFragment newInstance() {
        return new DialerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, @Nullable ViewGroup mContainer, @Nullable Bundle mSavedInstance) {
        View mView = mInflater.inflate(R.layout.fm_main_screen_dialer, mContainer, false);

        etPhoneNumber = mView.findViewById(R.id.etPhoneNumber);
        etPhoneNumber.setShowSoftInputOnFocus(false);

        drKeyGrid = mView.findViewById(R.id.drKeyGrid);
        dpKeyCall = mView.findViewById(R.id.dpKeyCall);
        dpKeyCall.setLayoutParams(callButtonParams());
        dpKeyCall.setOnClickListener(this);

        // Normal click listeners
        mView.findViewById(R.id.dpKeyOne).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyTwo).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyThree).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyFour).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyFive).setOnClickListener(this);
        mView.findViewById(R.id.dpKeySix).setOnClickListener(this);
        mView.findViewById(R.id.dpKeySeven).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyEight).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyNine).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyStar).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyZero).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyPound).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyHide).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyBack).setOnClickListener(this);

        // Long press click listeners
        mView.findViewById(R.id.dpKeyZero).setOnLongClickListener(this);
        mView.findViewById(R.id.dpKeyBack).setOnLongClickListener(this);

        final RecyclerView cfRecyclerView = mView.findViewById(R.id.drDialerContactList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        cfRecyclerView.setLayoutManager(mLayoutManager);
        cfRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ContactsListTask mAsyncTask = new ContactsListTask(getContext(), new ContactsListTask.ContactsResponse() {
            @Override
            public void onTaskCompletion(ArrayList<ContactsDataPair> mContactList) {
                mAdapter = new ContactsAdapter(getContext(), mContactList);
                cfRecyclerView.setAdapter(mAdapter);
                getFilteredList();
            }
        });
        mAsyncTask.execute();

        cfRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dx < -10)
                    hideDialPad();
            }
        });
        return mView;
    }

    @Override
    public void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.dpKeyOne:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_one));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyTwo:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_two));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyThree:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_three));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyFour:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_four));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyFive:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_five));
                    getFilteredList();
                }
                break;

            case R.id.dpKeySix:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_six));
                    getFilteredList();
                }
                break;

            case R.id.dpKeySeven:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_seven));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyEight:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_eight));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyNine:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_nine));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyStar:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_star));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyZero:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_zero));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyPound:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_pound));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyHide:
                hideDialPad();
                break;

            case R.id.dpKeyCall:
                if (dpKeyCall.getText().toString().equals(getResources().getString(R.string.dp_txt_call))) {
                    makeCall();
                } else {
                    showDialPad();
                }
                break;

            case R.id.dpKeyBack:
                if (etPhoneNumber == null) break;
                int mCursorPosition = etPhoneNumber.getSelectionStart();
                if (mCursorPosition < 1) {
                    break;
                }
                String mText = etPhoneNumber.getText().toString();
                mText = mText.substring(0, mCursorPosition - 1) + mText.substring(mCursorPosition);
                etPhoneNumber.setText(mText);
                etPhoneNumber.setSelection(mCursorPosition - 1);
                getFilteredList();
                break;

            default:
                break;

        }

    }

    @Override
    public boolean onLongClick(View mView) {
        switch (mView.getId()) {
            case R.id.dpKeyZero:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_plus));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyBack:
                if (etPhoneNumber != null) {
                    etPhoneNumber.setText("");
                    getFilteredList();
                }
                break;

            default:
                break;
        }
        return false;
    }

    private ViewGroup.LayoutParams callButtonParams() {
        int mWidth = getResources().getDisplayMetrics().widthPixels / 3;
        ViewGroup.LayoutParams mParams = dpKeyCall.getLayoutParams();
        mParams.width = mWidth;
        return mParams;
    }

    @SuppressWarnings("ConstantConditions")
    private void hideDialPad() {
        if (drKeyGrid.getVisibility() == View.VISIBLE) {
            Spannable mButtonLabel = new SpannableString(" ");
            mButtonLabel.setSpan(new ImageSpan(getContext(), R.drawable.ic_dialer_keypad, ImageSpan.ALIGN_BOTTOM), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            drKeyGrid.animate().translationY(drKeyGrid.getHeight()).alpha(0.0f);
            drKeyGrid.setVisibility(View.GONE);
            dpKeyCall.setText(mButtonLabel);
        }
    }

    private void showDialPad() {
        if (drKeyGrid.getVisibility() == View.GONE) {
            drKeyGrid.setVisibility(View.VISIBLE);
            drKeyGrid.animate().translationY(0).alpha(1.0f);
            dpKeyCall.setText(getResources().getString(R.string.dp_txt_call));
        }
    }

    private void makeCall() {
        String mInput = etPhoneNumber.getText().toString();
        if (mInput.length() < 1) {
            Toast.makeText(getActivity(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mInput.equals("*#06#")) {
            // Show device id
            return;
        }

        if (mInput.startsWith("*#*#") && mInput.endsWith("#*#*")) {
            // Error - Permission denied
            /*
            String mCode = new StringBuilder(mInput).substring(4, mInput.length()-4);
            getActivity().sendBroadcast(new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://" + mCode)));
            */
            return;
        }

        // TO DO: Handle calls using sim slot
        final String permissionToCall = Manifest.permission.CALL_PHONE;
        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
        phoneCallIntent.setData(Uri.parse("tel:" + Uri.encode(mInput)));
        if (ActivityCompat.checkSelfPermission(getActivity(), permissionToCall) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permissionToCall}, 1);
            return;
        }
        startActivity(phoneCallIntent);
    }

    private void getFilteredList() {
        String mSearchRegex = etPhoneNumber.getText().toString();
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_two), "[" + getResources().getString(R.string.dp_txt_abc) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_three), "[" + getResources().getString(R.string.dp_txt_def) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_four), "[" + getResources().getString(R.string.dp_txt_ghi) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_five), "[" + getResources().getString(R.string.dp_txt_jkl) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_six), "[" + getResources().getString(R.string.dp_txt_mno) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_seven), "[" + getResources().getString(R.string.dp_txt_pqrs) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_eight), "[" + getResources().getString(R.string.dp_txt_tuv) + "]");
        mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_nine), "[" + getResources().getString(R.string.dp_txt_wxyz) + "]");
        Log.d("TAG", "^(.*?)" + mSearchRegex + ".*");
        if (mAdapter != null) mAdapter.getFilter().filter(mSearchRegex + ".*");
    }
}