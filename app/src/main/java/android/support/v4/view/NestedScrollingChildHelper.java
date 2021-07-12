/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.view;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

public class NestedScrollingChildHelper {
    private boolean mIsNestedScrollingEnabled;
    private ViewParent mNestedScrollingParent;
    private int[] mTempNestedScrollConsumed;
    private final View mView;

    public NestedScrollingChildHelper(View view) {
        this.mView = view;
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean bl2) {
        if (this.isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return ViewParentCompat.onNestedFling(this.mNestedScrollingParent, this.mView, f2, f3, bl2);
        }
        return false;
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        if (this.isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return ViewParentCompat.onNestedPreFling(this.mNestedScrollingParent, this.mView, f2, f3);
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean dispatchNestedPreScroll(int n2, int n3, int[] nArray, int[] nArray2) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (!this.isNestedScrollingEnabled()) return bl3;
        bl3 = bl2;
        if (this.mNestedScrollingParent == null) return bl3;
        if (n2 != 0 || n3 != 0) {
            int n4 = 0;
            int n5 = 0;
            if (nArray2 != null) {
                this.mView.getLocationInWindow(nArray2);
                n4 = nArray2[0];
                n5 = nArray2[1];
            }
            int[] nArray3 = nArray;
            if (nArray == null) {
                if (this.mTempNestedScrollConsumed == null) {
                    this.mTempNestedScrollConsumed = new int[2];
                }
                nArray3 = this.mTempNestedScrollConsumed;
            }
            nArray3[0] = 0;
            nArray3[1] = 0;
            ViewParentCompat.onNestedPreScroll(this.mNestedScrollingParent, this.mView, n2, n3, nArray3);
            if (nArray2 != null) {
                this.mView.getLocationInWindow(nArray2);
                nArray2[0] = nArray2[0] - n4;
                nArray2[1] = nArray2[1] - n5;
            }
            if (nArray3[0] != 0) return true;
            bl3 = bl2;
            if (nArray3[1] == 0) return bl3;
            return true;
        }
        bl3 = bl2;
        if (nArray2 == null) return bl3;
        nArray2[0] = 0;
        nArray2[1] = 0;
        return false;
    }

    public boolean dispatchNestedScroll(int n2, int n3, int n4, int n5, int[] nArray) {
        if (this.isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (n2 != 0 || n3 != 0 || n4 != 0 || n5 != 0) {
                int n6 = 0;
                int n7 = 0;
                if (nArray != null) {
                    this.mView.getLocationInWindow(nArray);
                    n6 = nArray[0];
                    n7 = nArray[1];
                }
                ViewParentCompat.onNestedScroll(this.mNestedScrollingParent, this.mView, n2, n3, n4, n5);
                if (nArray != null) {
                    this.mView.getLocationInWindow(nArray);
                    nArray[0] = nArray[0] - n6;
                    nArray[1] = nArray[1] - n7;
                }
                return true;
            }
            if (nArray != null) {
                nArray[0] = 0;
                nArray[1] = 0;
            }
        }
        return false;
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingParent != null;
    }

    public boolean isNestedScrollingEnabled() {
        return this.mIsNestedScrollingEnabled;
    }

    public void onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(this.mView);
    }

    public void onStopNestedScroll(View view) {
        ViewCompat.stopNestedScroll(this.mView);
    }

    public void setNestedScrollingEnabled(boolean bl2) {
        if (this.mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(this.mView);
        }
        this.mIsNestedScrollingEnabled = bl2;
    }

    public boolean startNestedScroll(int n2) {
        if (this.hasNestedScrollingParent()) {
            return true;
        }
        if (this.isNestedScrollingEnabled()) {
            View view = this.mView;
            for (ViewParent viewParent = this.mView.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
                if (ViewParentCompat.onStartNestedScroll(viewParent, view, this.mView, n2)) {
                    this.mNestedScrollingParent = viewParent;
                    ViewParentCompat.onNestedScrollAccepted(viewParent, view, this.mView, n2);
                    return true;
                }
                if (!(viewParent instanceof View)) continue;
                view = (View)viewParent;
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        if (this.mNestedScrollingParent != null) {
            ViewParentCompat.onStopNestedScroll(this.mNestedScrollingParent, this.mView);
            this.mNestedScrollingParent = null;
        }
    }
}

