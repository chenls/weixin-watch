/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.util.Log
 */
package android.support.v4.os;

import android.os.Build;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompatKitKat;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    private EnvironmentCompat() {
    }

    public static String getStorageState(File object) {
        if (Build.VERSION.SDK_INT >= 19) {
            return EnvironmentCompatKitKat.getStorageState((File)object);
        }
        try {
            if (((File)object).getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                object = Environment.getExternalStorageState();
                return object;
            }
        }
        catch (IOException iOException) {
            Log.w((String)TAG, (String)("Failed to resolve canonical path: " + iOException));
        }
        return MEDIA_UNKNOWN;
    }
}

