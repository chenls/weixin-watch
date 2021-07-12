/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.os.Build$VERSION
 */
package android.support.v4.app;

import android.app.ActivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityManagerCompatKitKat;

public final class ActivityManagerCompat {
    private ActivityManagerCompat() {
    }

    public static boolean isLowRamDevice(@NonNull ActivityManager activityManager) {
        if (Build.VERSION.SDK_INT >= 19) {
            return ActivityManagerCompatKitKat.isLowRamDevice(activityManager);
        }
        return false;
    }
}

