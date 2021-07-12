/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

class PopupWindowCompatApi21 {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Field sOverlapAnchorField;

    static {
        try {
            sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            sOverlapAnchorField.setAccessible(true);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.i((String)TAG, (String)"Could not fetch mOverlapAnchor field from PopupWindow", (Throwable)noSuchFieldException);
        }
    }

    PopupWindowCompatApi21() {
    }

    static boolean getOverlapAnchor(PopupWindow popupWindow) {
        if (sOverlapAnchorField != null) {
            try {
                boolean bl2 = (Boolean)sOverlapAnchorField.get(popupWindow);
                return bl2;
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)TAG, (String)"Could not get overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void setOverlapAnchor(PopupWindow popupWindow, boolean bl2) {
        if (sOverlapAnchorField == null) return;
        try {
            sOverlapAnchorField.set(popupWindow, bl2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)TAG, (String)"Could not set overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
            return;
        }
    }
}

