/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package ticwear.design.widget;

import android.view.View;

class AppBarScrollController {
    private boolean mAppBarChanging = false;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffsets = new int[2];
    private View mScrollingView;

    AppBarScrollController(View view) {
        this.mScrollConsumed = new int[2];
        this.mScrollingView = view;
    }

    void hideAppBar() {
        this.mAppBarChanging = true;
        this.mScrollingView.startNestedScroll(2);
        this.mScrollingView.dispatchNestedPreScroll(0, this.mScrollingView.getHeight(), this.mScrollConsumed, this.mScrollOffsets);
        this.mScrollingView.stopNestedScroll();
        this.mAppBarChanging = false;
    }

    boolean isAppBarChanging() {
        return this.mAppBarChanging;
    }

    void showAppBar() {
        this.mAppBarChanging = true;
        this.mScrollingView.startNestedScroll(2);
        this.mScrollingView.dispatchNestedScroll(0, 0, -this.mScrollConsumed[0], -this.mScrollConsumed[1], this.mScrollOffsets);
        this.mScrollingView.stopNestedScroll();
        this.mAppBarChanging = false;
    }
}

