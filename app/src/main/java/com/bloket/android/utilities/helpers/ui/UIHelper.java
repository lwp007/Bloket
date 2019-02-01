package com.bloket.android.utilities.helpers.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bloket.android.R;

public class UIHelper {

    public static int dpToPx(final Context mContext, final float mDensityPixel) {
        final float mScale = mContext.getResources().getDisplayMetrics().density;
        return (int) ((mDensityPixel * mScale) + 0.5f);
    }

    @SuppressLint("PrivateResource")
    public static void showImageSnack(Activity mActivity, String mMessage) {
        Snackbar mSnackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), mMessage, Snackbar.LENGTH_LONG);
        try {
            View mSnackLayout = mSnackbar.getView();
            TextView mTextView = mSnackLayout.findViewById(android.support.design.R.id.snackbar_text);
            mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tab_blacklist, 0, 0, 0);
            mTextView.setCompoundDrawablePadding(dpToPx(mActivity, 16));
            mTextView.setGravity(Gravity.CENTER_VERTICAL);
        } catch (Exception mException) {
            Log.d("BLOKET_LOGS", "UIHelper: Error setting icon to snackbar.\n" + mException.toString());
        }
        mSnackbar.show();
    }

    public static void showPermissionSnack(Activity mActivity, String mMessage) {
        Snackbar mSnackBar = Snackbar
                .make(mActivity.findViewById(android.R.id.content), "Please allow permission to " + mMessage + ".", Snackbar.LENGTH_LONG)
                .setAction("ALLOW", mTempView -> {
                    Intent mIntent = new Intent();
                    mIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri mUri = Uri.fromParts("package", mActivity.getPackageName(), null);
                    mIntent.setData(mUri);
                    mActivity.startActivity(mIntent);
                });
        try {
            View mSnackLayout = mSnackBar.getView();
            TextView mTextView = mSnackLayout.findViewById(android.support.design.R.id.snackbar_text);
            mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tab_whitelist, 0, 0, 0);
            mTextView.setCompoundDrawablePadding(UIHelper.dpToPx(mActivity, 16));
            mTextView.setGravity(Gravity.CENTER_VERTICAL);
        } catch (Exception mException) {
            Log.d("BLOKET_LOGS", "UIHelper: Error setting icon to snackbar, Error :: " + mException.toString());
        }
        mSnackBar.show();
    }
}
