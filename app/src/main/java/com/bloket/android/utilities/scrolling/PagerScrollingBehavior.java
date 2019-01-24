package com.bloket.android.utilities.scrolling;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("unused")
public class PagerScrollingBehavior extends AppBarLayout.ScrollingViewBehavior {

    private AppBarLayout mAppBar;

    public PagerScrollingBehavior() {
        super();
    }

    public PagerScrollingBehavior(Context mContext, AttributeSet mAttrs) {
        super(mContext, mAttrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout mParent, View mChild, View mDependency) {
        if (mAppBar == null) mAppBar = (AppBarLayout) mDependency;
        final boolean mResult = super.onDependentViewChanged(mParent, mChild, mDependency);
        final int mBottomPadding = calculateBottomPadding(mAppBar);
        final boolean mPaddingChanged = mBottomPadding != mChild.getPaddingBottom();
        if (mPaddingChanged) {
            mChild.setPadding(mChild.getPaddingLeft(), mChild.getPaddingTop(), mChild.getPaddingRight(), mBottomPadding);
            mChild.requestLayout();
        }
        return mPaddingChanged || mResult;
    }


    private int calculateBottomPadding(AppBarLayout mDependency) {
        final int mScrollRange = mDependency.getTotalScrollRange();
        return mScrollRange + mDependency.getTop();
    }
}
