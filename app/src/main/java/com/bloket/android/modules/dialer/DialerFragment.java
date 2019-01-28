package com.bloket.android.modules.dialer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.PhoneAccountHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bloket.android.R;
import com.bloket.android.modules.contacts.ContactsAdapter;
import com.bloket.android.utilities.helpers.contacts.ContactsDataPair;
import com.bloket.android.utilities.helpers.contacts.ContactsListTask;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_1));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyTwo:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_2));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyThree:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_3));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyFour:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_4));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyFive:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_5));
                    getFilteredList();
                }
                break;

            case R.id.dpKeySix:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_6));
                    getFilteredList();
                }
                break;

            case R.id.dpKeySeven:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_7));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyEight:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_8));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyNine:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_9));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyStar:
                if (etPhoneNumber == null) return;
                String mInput = etPhoneNumber.getText().toString();

                // TODO: Android secret codes, details at https://www.xda-developers.com/codes-hidden-android/
                /*  Best way to deal with the secret codes is as below, but permission is denied unless this app is set as default dialer app.
                    String mCode = new StringBuilder(mInput).substring(4, mInput.length()-4);
                    getActivity().sendBroadcast(new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://" + mCode)));
                    */
                if (mInput.startsWith("*#*#") && mInput.endsWith("#*#") && mInput.length() > 7) {
                    Intent mSecretIntent = new Intent(Intent.ACTION_MAIN);
                    mSecretIntent.setClassName("com.android.settings", "com.android.settings.RadioInfo");
                    startActivity(mSecretIntent);
                    return;
                }
                etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_star));
                getFilteredList();
                break;

            case R.id.dpKeyZero:
                if (etPhoneNumber != null) {
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_0));
                    getFilteredList();
                }
                break;

            case R.id.dpKeyPound:
                if (etPhoneNumber == null) return;

                // Handle secret code for IMEI
                if (etPhoneNumber.getText().toString().equals("*#06")) {
                    showDeviceId();
                    etPhoneNumber.setText("");
                    return;
                }
                etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_hash));
                getFilteredList();
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

    @SuppressLint("NewApi")
    private void makeCall() {
        String mInput = etPhoneNumber.getText().toString();
        if (mInput.length() < 1) {
            Toast.makeText(getActivity(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isDualSim()) {
            // Call is made on and above API 23
            showSimSelector();
        } else {
            final String permissionToCall = Manifest.permission.CALL_PHONE;
            Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
            phoneCallIntent.setData(Uri.parse("tel:" + Uri.encode(mInput)));
            if (ActivityCompat.checkSelfPermission(getActivity(), permissionToCall) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permissionToCall}, 1);
                return;
            }
            startActivity(phoneCallIntent);
        }
    }

    private void getFilteredList() {
        String mSearchRegex = etPhoneNumber.getText().toString();
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
        if (mAdapter != null) mAdapter.getFilter().filter(mSearchRegex + ".*");
    }

    @SuppressLint("HardwareIds")
    @SuppressWarnings("ConstantConditions")
    private void showDeviceId() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            return;
        }

        TelephonyManager mTelephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        View mContentView = LayoutInflater.from(getContext()).inflate(R.layout.dg_generic_one, null);
        final Dialog mDialog = new Dialog(getContext());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mContentView);
        TextView tvTitle = mDialog.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.dp_device_imei));
        TextView tvMessage = mDialog.findViewById(R.id.tvMessage);
        tvMessage.setText(mTelephonyManager.getDeviceId());
        Button btPositive = mDialog.findViewById(R.id.btPositive);
        btPositive.setText(getResources().getString(R.string.gc_ok));
        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private boolean isDualSim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                return false;
            }

            final SubscriptionManager mManager = (SubscriptionManager) getActivity().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            final List<SubscriptionInfo> mActiveSubList = mManager.getActiveSubscriptionInfoList();
            return mActiveSubList.size() > 1;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    private void showSimSelector() {
        final SubscriptionManager mManager = (SubscriptionManager) getActivity().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        final List<SubscriptionInfo> mActiveSubList = mManager.getActiveSubscriptionInfoList();
        View mContentView = LayoutInflater.from(getContext()).inflate(R.layout.fm_dialer_sim_chooser, null);
        final Dialog mDialog = new Dialog(getContext());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mContentView);

        TextView tvSimOne = mContentView.findViewById(R.id.tvSimOne);
        tvSimOne.setText(mActiveSubList.get(0).getDisplayName());
        tvSimOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                final Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(etPhoneNumber.getText().toString())));
                final int simSlotIndex = 0;
                try {
                    final Method mSubIdMethod = SubscriptionManager.class.getDeclaredMethod("getSubId", int.class);
                    mSubIdMethod.setAccessible(true);
                    final long mSubIdForSlot = ((long[]) mSubIdMethod.invoke(SubscriptionManager.class, simSlotIndex))[0];
                    final ComponentName mComponentName = new ComponentName("com.android.phone", "com.android.services.telephony.TelephonyConnectionService");
                    final PhoneAccountHandle mAccountHandle = new PhoneAccountHandle(mComponentName, String.valueOf(mSubIdForSlot));
                    mIntent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", mAccountHandle);
                } catch (Exception mException) {
                    mException.printStackTrace();
                }
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                mDialog.dismiss();
            }
        });
        TextView tvSimTwo = mContentView.findViewById(R.id.tvSimTwo);
        tvSimTwo.setText(mActiveSubList.get(1).getDisplayName());
        tvSimTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                final Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(etPhoneNumber.getText().toString())));
                final int simSlotIndex = 1;
                try {
                    final Method mSubIdMethod = SubscriptionManager.class.getDeclaredMethod("getSubId", int.class);
                    mSubIdMethod.setAccessible(true);
                    final long mSubIdForSlot = ((long[]) mSubIdMethod.invoke(SubscriptionManager.class, simSlotIndex))[0];
                    final ComponentName mComponentName = new ComponentName("com.android.phone", "com.android.services.telephony.TelephonyConnectionService");
                    final PhoneAccountHandle mAccountHandle = new PhoneAccountHandle(mComponentName, String.valueOf(mSubIdForSlot));
                    mIntent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", mAccountHandle);
                } catch (Exception mException) {
                    mException.printStackTrace();
                }
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
