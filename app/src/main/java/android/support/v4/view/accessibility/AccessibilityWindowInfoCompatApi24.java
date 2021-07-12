/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityWindowInfo
 */
package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityWindowInfo;

class AccessibilityWindowInfoCompatApi24 {
    AccessibilityWindowInfoCompatApi24() {
    }

    public static Object getAnchor(Object object) {
        return ((AccessibilityWindowInfo)object).getAnchor();
    }

    public static CharSequence getTitle(Object object) {
        return ((AccessibilityWindowInfo)object).getTitle();
    }
}

