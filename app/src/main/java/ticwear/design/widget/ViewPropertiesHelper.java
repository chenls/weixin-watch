/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package ticwear.design.widget;

import android.view.View;
import android.view.ViewGroup;

class ViewPropertiesHelper {
    ViewPropertiesHelper() {
    }

    private static boolean clipToPadding(View view) {
        return !(view instanceof ViewGroup) || ((ViewGroup)view).getClipToPadding();
    }

    static int getAdjustedHeight(View view) {
        if (ViewPropertiesHelper.clipToPadding(view)) {
            return view.getHeight() - view.getPaddingBottom() - view.getPaddingTop();
        }
        return view.getHeight();
    }

    static int getBottom(View view) {
        if (ViewPropertiesHelper.clipToPadding(view)) {
            return view.getBottom() - view.getPaddingBottom();
        }
        return view.getBottom();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int getCenterYPos(View view) {
        int n2;
        if (ViewPropertiesHelper.clipToPadding(view)) {
            n2 = view.getPaddingTop();
            return view.getTop() + n2 + ViewPropertiesHelper.getAdjustedHeight(view) / 2;
        }
        n2 = 0;
        return view.getTop() + n2 + ViewPropertiesHelper.getAdjustedHeight(view) / 2;
    }

    static int getTop(View view) {
        if (ViewPropertiesHelper.clipToPadding(view)) {
            return view.getTop() + view.getPaddingTop();
        }
        return view.getTop();
    }
}

