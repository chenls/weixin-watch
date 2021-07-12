/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.view;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;

class ViewParentCompatLollipop {
    private static final String TAG = "ViewParentCompat";

    ViewParentCompatLollipop() {
    }

    public static boolean onNestedFling(ViewParent viewParent, View view, float f2, float f3, boolean bl2) {
        try {
            bl2 = viewParent.onNestedFling(view, f2, f3, bl2);
            return bl2;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onNestedFling"), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view, float f2, float f3) {
        try {
            boolean bl2 = viewParent.onNestedPreFling(view, f2, f3);
            return bl2;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onNestedPreFling"), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int n2, int n3, int[] nArray) {
        try {
            viewParent.onNestedPreScroll(view, n2, n3, nArray);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onNestedPreScroll"), (Throwable)abstractMethodError);
            return;
        }
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int n2, int n3, int n4, int n5) {
        try {
            viewParent.onNestedScroll(view, n2, n3, n4, n5);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onNestedScroll"), (Throwable)abstractMethodError);
            return;
        }
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int n2) {
        try {
            viewParent.onNestedScrollAccepted(view, view2, n2);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onNestedScrollAccepted"), (Throwable)abstractMethodError);
            return;
        }
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int n2) {
        try {
            boolean bl2 = viewParent.onStartNestedScroll(view, view2, n2);
            return bl2;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onStartNestedScroll"), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view) {
        try {
            viewParent.onStopNestedScroll(view);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            Log.e((String)TAG, (String)("ViewParent " + viewParent + " does not implement interface " + "method onStopNestedScroll"), (Throwable)abstractMethodError);
            return;
        }
    }
}

