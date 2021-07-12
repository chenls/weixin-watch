/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package android.support.design.widget;

import android.content.Context;
import android.support.design.R;

class ThemeUtils {
    private static final int[] APPCOMPAT_CHECK_ATTRS = new int[]{R.attr.colorPrimary};

    ThemeUtils() {
    }

    static void checkAppCompatTheme(Context context) {
        boolean bl2 = false;
        if (!(context = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS)).hasValue(0)) {
            bl2 = true;
        }
        if (context != null) {
            context.recycle();
        }
        if (bl2) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }
}

