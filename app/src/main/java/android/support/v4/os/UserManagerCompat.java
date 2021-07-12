/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package android.support.v4.os;

import android.content.Context;
import android.support.v4.os.BuildCompat;
import android.support.v4.os.UserManagerCompatApi24;

public class UserManagerCompat {
    private UserManagerCompat() {
    }

    @Deprecated
    public static boolean isUserRunningAndLocked(Context context) {
        return !UserManagerCompat.isUserUnlocked(context);
    }

    @Deprecated
    public static boolean isUserRunningAndUnlocked(Context context) {
        return UserManagerCompat.isUserUnlocked(context);
    }

    public static boolean isUserUnlocked(Context context) {
        if (BuildCompat.isAtLeastN()) {
            return UserManagerCompatApi24.isUserUnlocked(context);
        }
        return true;
    }
}

