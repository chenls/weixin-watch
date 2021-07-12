/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.v4.view.accessibility;

import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

class AccessibilityNodeInfoCompatJellybeanMr2 {
    AccessibilityNodeInfoCompatJellybeanMr2() {
    }

    public static List<Object> findAccessibilityNodeInfosByViewId(Object object, String string2) {
        return ((AccessibilityNodeInfo)object).findAccessibilityNodeInfosByViewId(string2);
    }

    public static int getTextSelectionEnd(Object object) {
        return ((AccessibilityNodeInfo)object).getTextSelectionEnd();
    }

    public static int getTextSelectionStart(Object object) {
        return ((AccessibilityNodeInfo)object).getTextSelectionStart();
    }

    public static String getViewIdResourceName(Object object) {
        return ((AccessibilityNodeInfo)object).getViewIdResourceName();
    }

    public static boolean isEditable(Object object) {
        return ((AccessibilityNodeInfo)object).isEditable();
    }

    public static boolean refresh(Object object) {
        return ((AccessibilityNodeInfo)object).refresh();
    }

    public static void setEditable(Object object, boolean bl2) {
        ((AccessibilityNodeInfo)object).setEditable(bl2);
    }

    public static void setTextSelection(Object object, int n2, int n3) {
        ((AccessibilityNodeInfo)object).setTextSelection(n2, n3);
    }

    public static void setViewIdResourceName(Object object, String string2) {
        ((AccessibilityNodeInfo)object).setViewIdResourceName(string2);
    }
}

