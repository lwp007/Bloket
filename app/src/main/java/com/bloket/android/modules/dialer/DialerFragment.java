package com.bloket.android.modules.dialer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bloket.android.R;
import com.bloket.android.utilities.helpers.permission.PermissionHelper;
import com.bloket.android.utilities.helpers.telephone.TelephoneHelper;
import com.bloket.android.utilities.helpers.ui.UIHelper;

public class DialerFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private Button mKeyCall;
    private EditText mPhoneNumber;
    private LinearLayout mKeyGrid;
    private DialerContactAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private final int PERM_CALL_PHONE = 0, PERM_READ_PHONE_STATE = 1;

    public static DialerFragment newInstance() {
        return new DialerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, @Nullable ViewGroup mContainer, @Nullable Bundle mSavedInstance) {
        View mView = mInflater.inflate(R.layout.fm_dialer_layout, mContainer, false);
        mKeyGrid = mView.findViewById(R.id.drKeyGrid);
        mKeyCall = mView.findViewById(R.id.drKeyCall);
        mKeyCall.setLayoutParams(callButtonParams());
        mKeyCall.setOnClickListener(this);
        mPhoneNumber = mView.findViewById(R.id.drPhoneNumber);
        setNumberPanel();

        // Setup contact list
        mRecyclerView = mView.findViewById(R.id.drDialerContactList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView mRecyclerView, int dx, int dy) {
                super.onScrolled(mRecyclerView, dx, dy);
                if (dy > 0 || dx < -10)
                    hideDialPad();
            }
        });
        DialerContactTask mAsyncTask = new DialerContactTask(getContext(), mContactList -> mAdapter = new DialerContactAdapter(getContext(), mContactList));
        mAsyncTask.execute();

        // Normal click listeners
        mView.findViewById(R.id.drKeyOne).setOnClickListener(this);
        mView.findViewById(R.id.drKeyTwo).setOnClickListener(this);
        mView.findViewById(R.id.drKeyThree).setOnClickListener(this);
        mView.findViewById(R.id.drKeyFour).setOnClickListener(this);
        mView.findViewById(R.id.drKeyFive).setOnClickListener(this);
        mView.findViewById(R.id.drKeySix).setOnClickListener(this);
        mView.findViewById(R.id.drKeySeven).setOnClickListener(this);
        mView.findViewById(R.id.drKeyEight).setOnClickListener(this);
        mView.findViewById(R.id.drKeyNine).setOnClickListener(this);
        mView.findViewById(R.id.drKeyStar).setOnClickListener(this);
        mView.findViewById(R.id.drKeyZero).setOnClickListener(this);
        mView.findViewById(R.id.drKeyPound).setOnClickListener(this);
        mView.findViewById(R.id.drKeyHide).setOnClickListener(this);
        mView.findViewById(R.id.drKeyBack).setOnClickListener(this);

        // Long press click listeners
        mView.findViewById(R.id.drKeyZero).setOnLongClickListener(this);
        mView.findViewById(R.id.drKeyBack).setOnLongClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.drKeyZero:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_0));
                break;

            case R.id.drKeyOne:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_1));
                break;

            case R.id.drKeyTwo:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_2));
                break;

            case R.id.drKeyThree:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_3));
                break;

            case R.id.drKeyFour:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_4));
                break;

            case R.id.drKeyFive:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_5));
                break;

            case R.id.drKeySix:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_6));
                break;

            case R.id.drKeySeven:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_7));
                break;

            case R.id.drKeyEight:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_8));
                break;

            case R.id.drKeyNine:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_num_9));
                break;

            case R.id.drKeyStar:
                if (mPhoneNumber == null) return;
                String mInput = mPhoneNumber.getText().toString();

                // TODO: Android secret codes
                if (mInput.startsWith("*#*#") && mInput.endsWith("#*#") && mInput.length() > 7) {
                    Intent mSecretIntent = new Intent(Intent.ACTION_MAIN);
                    mSecretIntent.setClassName("com.android.settings", "com.android.settings.RadioInfo");
                    startActivity(mSecretIntent);
                    return;
                }
                mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_star));
                break;

            case R.id.drKeyPound:
                if (mPhoneNumber == null) return;

                // Handle secret code for IMEI
                if (mPhoneNumber.getText().toString().equals("*#06")) {
                    showDeviceId();
                    mPhoneNumber.setText("");
                    return;
                }
                mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_hash));
                break;

            case R.id.drKeyHide:
                hideDialPad();
                break;

            case R.id.drKeyCall:
                if (mKeyCall.getText().toString().equals(getResources().getString(R.string.dp_txt_call))) {
                    placeVoiceCall();
                } else {
                    showDialPad();
                }
                break;

            case R.id.drKeyBack:
                if (mPhoneNumber == null) break;
                int mCursorPosition = mPhoneNumber.getSelectionStart();
                if (mCursorPosition < 1) {
                    break;
                }
                String mText = mPhoneNumber.getText().toString();
                mText = mText.substring(0, mCursorPosition - 1) + mText.substring(mCursorPosition);
                mPhoneNumber.setText(mText);
                mPhoneNumber.setSelection(mCursorPosition - 1);
                break;

            default:
                break;

        }
    }

    @Override
    public boolean onLongClick(View mView) {
        switch (mView.getId()) {
            case R.id.drKeyZero:
                if (mPhoneNumber != null)
                    mPhoneNumber.getText().insert(mPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_plus));
                break;

            case R.id.drKeyBack:
                if (mPhoneNumber != null)
                    mPhoneNumber.setText("");
                break;

            default:
                break;
        }
        return false;
    }

    private void setNumberPanel() {
        if (mPhoneNumber == null) return;
        mPhoneNumber.setShowSoftInputOnFocus(false);
        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mSequence, int mStart, int mCount, int mAfter) {
            }

            @Override
            public void onTextChanged(CharSequence mSequence, int mStart, int mBefore, int mCount) {
                if (mRecyclerView == null) return;
                mRecyclerView.getRecycledViewPool().clear();
                getFilteredList();
            }

            @Override
            public void afterTextChanged(Editable mEditable) {
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void hideDialPad() {
        if (mKeyGrid.getVisibility() == View.VISIBLE) {
            Spannable mButtonLabel = new SpannableString(" ");
            mButtonLabel.setSpan(new ImageSpan(getContext(), R.drawable.ic_dialer_keypad, ImageSpan.ALIGN_BOTTOM), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mKeyGrid.animate().translationY(mKeyGrid.getHeight()).alpha(0.0f);
            mKeyGrid.setVisibility(View.GONE);
            mKeyCall.setText(mButtonLabel);
        }
    }

    private void showDialPad() {
        if (mKeyGrid.getVisibility() == View.GONE) {
            mKeyGrid.setVisibility(View.VISIBLE);
            mKeyGrid.animate().translationY(0).alpha(1.0f);
            mKeyCall.setText(getResources().getString(R.string.dp_txt_call));
        }
    }

    private void getFilteredList() {
        String mSearchRegex = mPhoneNumber.getText().toString();
        if (!mSearchRegex.trim().isEmpty()) {
            mSearchRegex = mSearchRegex.replaceAll("\\+", "");
            if (mSearchRegex.isEmpty()) return;
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_0), getResources().getString(R.string.dp_reg_0));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_1), getResources().getString(R.string.dp_reg_1));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_2), getResources().getString(R.string.dp_reg_2));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_3), getResources().getString(R.string.dp_reg_3));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_4), getResources().getString(R.string.dp_reg_4));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_5), getResources().getString(R.string.dp_reg_5));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_6), getResources().getString(R.string.dp_reg_6));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_7), getResources().getString(R.string.dp_reg_7));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_8), getResources().getString(R.string.dp_reg_8));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_num_9), getResources().getString(R.string.dp_reg_9));
            mSearchRegex = mSearchRegex.replaceAll("\\*", getResources().getString(R.string.dp_reg_star));
            mSearchRegex = mSearchRegex.replaceAll(getResources().getString(R.string.dp_txt_hash), getResources().getString(R.string.dp_reg_hash));
            mSearchRegex += ".*";
            if (mAdapter != null && mRecyclerView != null && mRecyclerView.getAdapter() == null)
                mRecyclerView.setAdapter(mAdapter);
        } else {
            mSearchRegex = getResources().getString(R.string.app_name);
        }
        if (mAdapter != null) mAdapter.getFilter().filter(mSearchRegex);
    }

    @SuppressWarnings("ConstantConditions")
    private void placeVoiceCall() {
        if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERM_CALL_PHONE);
            return;
        }
        TelephoneHelper.placeCall(getActivity(), mPhoneNumber.getText().toString());
    }

    @SuppressLint("HardwareIds, InflateParams")
    @SuppressWarnings("ConstantConditions")
    private void showDeviceId() {
        if (getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERM_READ_PHONE_STATE);
            return;
        }

        TelephonyManager mTelephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        View mContentView = LayoutInflater.from(getContext()).inflate(R.layout.dg_generic_one, null, false);
        final Dialog mDialog = new Dialog(getContext());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mContentView);
        TextView tvTitle = mDialog.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.dp_device_imei));
        TextView tvMessage = mDialog.findViewById(R.id.tvMessage);
        if (TelephoneHelper.isDualSimDevice(getActivity())) {
            String mText = getResources().getString(R.string.dp_device_imei_1) + " " + mTelephonyManager.getDeviceId(0) + "\n" +
                    getResources().getString(R.string.dp_device_imei_2) + " " + mTelephonyManager.getDeviceId(1);
            tvMessage.setText(mText);
        } else
            tvMessage.setText(mTelephonyManager.getDeviceId());
        Button btPositive = mDialog.findViewById(R.id.btPositive);
        btPositive.setText(getResources().getString(R.string.gc_ok));
        btPositive.setOnClickListener(mView -> mDialog.dismiss());
        mDialog.show();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onRequestPermissionsResult(int mRequestCode, @NonNull String[] mPermissions, @NonNull int[] mResult) {
        switch (mRequestCode) {
            case PERM_CALL_PHONE:
                if (mResult.length > 0 && mResult[0] == PackageManager.PERMISSION_GRANTED)
                    placeVoiceCall();
                else {
                    boolean mShowRationale = shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE);
                    if (!mShowRationale)
                        PermissionHelper.requestPermissionSnack(getActivity(), getView());
                    else
                        UIHelper.showImageSnack(getActivity(), getView(), "Permission denied.");
                }
                break;

            case PERM_READ_PHONE_STATE:
                if (mResult.length > 0 && mResult[0] == PackageManager.PERMISSION_GRANTED)
                    showDeviceId();
                else {
                    boolean mShowRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE);
                    if (!mShowRationale)
                        PermissionHelper.requestPermissionSnack(getActivity(), getView());
                    else
                        UIHelper.showImageSnack(getActivity(), getView(), "Permission denied.");
                }
                break;

            default:
                break;
        }
        super.onRequestPermissionsResult(mRequestCode, mPermissions, mResult);
    }

    private ViewGroup.LayoutParams callButtonParams() {
        int mWidth = getResources().getDisplayMetrics().widthPixels / 3;
        ViewGroup.LayoutParams mParams = mKeyCall.getLayoutParams();
        mParams.width = mWidth;
        return mParams;
    }
}