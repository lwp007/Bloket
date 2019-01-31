package com.bloket.android.utilities.helpers.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bloket.android.R;
import com.bloket.android.utilities.helpers.ui.UIHelper;

public class PermissionHelper {

    public static void requestPermissionSnack(Activity mActivity, View mView) {
        Snackbar mSnackBar = Snackbar
                .make(mView, "Please allow permission.", Snackbar.LENGTH_LONG)
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
            Log.d("BLOKET_LOGS", "PermissionHelper: Error setting icon to snackbar, Error :: " + mException.toString());
        }
        mSnackBar.show();
    }
}
