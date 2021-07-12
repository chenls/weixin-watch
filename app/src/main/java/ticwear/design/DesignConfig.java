/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package ticwear.design;

import android.os.Build;

public class DesignConfig {
    public static final boolean DEBUG;
    public static final boolean DEBUG_COORDINATOR;
    public static final boolean DEBUG_PICKERS;
    public static final boolean DEBUG_RECYCLER_VIEW;
    public static final boolean DEBUG_SCROLLBAR;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !Build.TYPE.equals("user");
        DEBUG = bl2;
        if (DEBUG) {
            // empty if block
        }
        DEBUG_PICKERS = false;
        if (DEBUG) {
            // empty if block
        }
        DEBUG_RECYCLER_VIEW = false;
        if (DEBUG) {
            // empty if block
        }
        DEBUG_SCROLLBAR = false;
        if (DEBUG) {
            // empty if block
        }
        DEBUG_COORDINATOR = false;
    }
}

