package com.bloket.android.utilities.helpers.telephone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telecom.PhoneAccountHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bloket.android.R;

import java.lang.reflect.Method;
import java.util.List;

public class TelephoneHelper {


    public static void placeCall(Activity mActivity, String mNumber) {
        if (mNumber.length() < 1) return;
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            return;

        if (isDualSimDevice(mActivity)) {
            showSimSelector(mActivity, mNumber);
        } else {
            Intent mCallIntent = new Intent(Intent.ACTION_CALL);
            mCallIntent.setData(Uri.parse("tel:" + Uri.encode(mNumber)));
            mActivity.startActivity(mCallIntent);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isDualSimDevice(Activity mActivity) {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return false;

        final SubscriptionManager mManager = (SubscriptionManager) mActivity.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        final List<SubscriptionInfo> mActiveSubList = mManager.getActiveSubscriptionInfoList();
        return mActiveSubList.size() > 1;
    }

    @SuppressLint({"InflateParams", "PrivateApi"})
    @SuppressWarnings({"ConstantConditions", "JavaReflectionMemberAccess"})
    private static void showSimSelector(Activity mActivity, String mNumber) {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return;

        final SubscriptionManager mManager = (SubscriptionManager) mActivity.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        final List<SubscriptionInfo> mActiveSubList = mManager.getActiveSubscriptionInfoList();
        View mContentView = LayoutInflater.from(mActivity).inflate(R.layout.fm_dialer_sim_chooser, null, false);
        final Dialog mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mContentView);

        TextView tvSimOne = mContentView.findViewById(R.id.drSimOne);
        tvSimOne.setText(mActiveSubList.get(0).getDisplayName());
        tvSimOne.setOnClickListener(mView -> {
            final Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(mNumber)));
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
            mActivity.startActivity(mIntent);
            mDialog.dismiss();
        });

        TextView tvSimTwo = mContentView.findViewById(R.id.drSimTwo);
        tvSimTwo.setText(mActiveSubList.get(1).getDisplayName());
        tvSimTwo.setOnClickListener(mView -> {
            final Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(mNumber)));
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
            mActivity.startActivity(mIntent);
            mDialog.dismiss();
        });
        mDialog.show();
    }
}
