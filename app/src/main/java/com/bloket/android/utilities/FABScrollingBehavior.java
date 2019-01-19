package com.bloket.android.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.bloket.android.R;

@SuppressWarnings("unused")
public class FABScrollingBehavior extends FloatingActionButton.Behavior {

    private int mToolbarHeight;

    public FABScrollingBehavior(Context mContext, AttributeSet mAttrs) {
        super();
        this.mToolbarHeight = getToolbarHeight(mContext);
    }

    private static int getToolbarHeight(Context mContext) {
        final TypedArray mAttr = mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int mToolbarHeight = (int) mAttr.getDimension(0, 0);
        mAttr.recycle();
        return mToolbarHeight;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean layoutDependsOn(CoordinatorLayout mParent, FloatingActionButton mFabButton, View mDependentView) {
        return super.layoutDependsOn(mParent, mFabButton, mDependentView) || (mDependentView instanceof AppBarLayout);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout mParent, FloatingActionButton mFabButton, View mDependentView) {
        boolean mReturnValue = super.onDependentViewChanged(mParent, mFabButton, mDependentView);
        if (mDependentView instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams mParams = (CoordinatorLayout.LayoutParams) mFabButton.getLayoutParams();
            int mFabBottomMargin = mParams.bottomMargin;
            int mDistanceToScroll = mFabButton.getHeight() + mFabBottomMargin;
            float mRatio = mDependentView.getY() / (float) mToolbarHeight;
            mFabButton.setTranslationY(-mDistanceToScroll * mRatio);
        }
        return mReturnValue;
    }
}