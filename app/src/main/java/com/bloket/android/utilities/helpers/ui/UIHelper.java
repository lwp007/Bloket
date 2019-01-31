package com.bloket.android.utilities.helpers.ui;

import android.annotation.SuppressLint;
import android.content.Context;
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
    public static void showImageSnack(Context mContext, View mView, String mMessage) {
        Snackbar mSnackbar = Snackbar.make(mView, mMessage, Snackbar.LENGTH_LONG);
        try {
            View mSnackLayout = mSnackbar.getView();
            TextView mTextView = mSnackLayout.findViewById(android.support.design.R.id.snackbar_text);
            mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tab_blacklist, 0, 0, 0);
            mTextView.setCompoundDrawablePadding(dpToPx(mContext, 16));
            mTextView.setGravity(Gravity.CENTER_VERTICAL);
        } catch (Exception mException) {
            Log.d("BLOKET_LOGS", "UIHelper: Error setting icon to snackbar.\n" + mException.toString());
        }
        mSnackbar.show();
    }
}
