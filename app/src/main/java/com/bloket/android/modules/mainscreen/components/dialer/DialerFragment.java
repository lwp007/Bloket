package com.bloket.android.modules.mainscreen.components.dialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bloket.android.R;

public class DialerFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private EditText etPhoneNumber;
    private ImageButton dpKeyHide;
    private LinearLayout drDialerGrid;

    public static DialerFragment newInstance() {
        return new DialerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater mInflater, @Nullable ViewGroup mContainer, @Nullable Bundle mSavedInstance) {
        View mView = mInflater.inflate(R.layout.fm_main_screen_dialer, mContainer, false);

        etPhoneNumber = mView.findViewById(R.id.etPhoneNumber);
        etPhoneNumber.setShowSoftInputOnFocus(false);
        dpKeyHide = mView.findViewById(R.id.dpKeyHide);
        dpKeyHide.setOnClickListener(this);
        drDialerGrid = mView.findViewById(R.id.drDialerGrid);

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
        mView.findViewById(R.id.dpKeyDial).setOnClickListener(this);
        mView.findViewById(R.id.dpKeyBack).setOnClickListener(this);

        // Long press click listeners
        mView.findViewById(R.id.dpKeyBack).setOnLongClickListener(this);
        mView.findViewById(R.id.dpKeyZero).setOnLongClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.dpKeyOne:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_one));
                break;

            case R.id.dpKeyTwo:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_two));
                break;

            case R.id.dpKeyThree:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_three));
                break;

            case R.id.dpKeyFour:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_four));
                break;

            case R.id.dpKeyFive:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_five));
                break;

            case R.id.dpKeySix:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_six));
                break;

            case R.id.dpKeySeven:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_seven));
                break;

            case R.id.dpKeyEight:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_eight));
                break;

            case R.id.dpKeyNine:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_nine));
                break;

            case R.id.dpKeyStar:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_star));
                break;

            case R.id.dpKeyZero:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_num_zero));
                break;

            case R.id.dpKeyPound:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_pound));
                break;

            case R.id.dpKeyHide:
                if (drDialerGrid.getVisibility() == View.VISIBLE) {
                    drDialerGrid.setVisibility(View.INVISIBLE);
                    dpKeyHide.setImageResource(R.drawable.ic_dialpad_show);
                } else {
                    drDialerGrid.setVisibility(View.VISIBLE);
                    dpKeyHide.setImageResource(R.drawable.ic_dialpad_hide);
                }
                break;

            case R.id.dpKeyDial:
                if (etPhoneNumber.getText().toString().length() < 1) {
                    Toast.makeText(getActivity(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    break;
                }
                final String permissionToCall = Manifest.permission.CALL_PHONE;
                Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                phoneCallIntent.setData(Uri.parse("tel:" + etPhoneNumber.getText().toString()));
                if (ActivityCompat.checkSelfPermission(getActivity(), permissionToCall) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permissionToCall}, 1);
                    return;
                }
                startActivity(phoneCallIntent);


                break;

            case R.id.dpKeyBack:
                if (etPhoneNumber == null) break;
                int mCursorPosition = etPhoneNumber.getSelectionStart();
                if (mCursorPosition < 1) break;
                String mText = etPhoneNumber.getText().toString();
                mText = mText.substring(0, mCursorPosition - 1) + mText.substring(mCursorPosition);
                etPhoneNumber.setText(mText);
                etPhoneNumber.setSelection(mCursorPosition - 1);
                break;

            default:
                break;

        }
    }

    @Override
    public boolean onLongClick(View mView) {
        switch (mView.getId()) {
            case R.id.dpKeyZero:
                if (etPhoneNumber != null)
                    etPhoneNumber.getText().insert(etPhoneNumber.getSelectionStart(), getString(R.string.dp_txt_plus));
                break;

            case R.id.dpKeyBack:
                if (etPhoneNumber != null) etPhoneNumber.setText("");
                break;

            default:
                break;
        }
        return false;
    }
}
