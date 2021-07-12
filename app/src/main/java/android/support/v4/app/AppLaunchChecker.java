/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

public class AppLaunchChecker {
    private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
    private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";

    public static boolean hasStartedFromLauncher(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, 0).getBoolean(KEY_STARTED_FROM_LAUNCHER, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void onActivityCreate(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFS_NAME, 0);
        if (sharedPreferences.getBoolean(KEY_STARTED_FROM_LAUNCHER, false) || (activity = activity.getIntent()) == null || !"android.intent.action.MAIN".equals(activity.getAction()) || !activity.hasCategory("android.intent.category.LAUNCHER") && !activity.hasCategory("android.intent.category.LEANBACK_LAUNCHER")) {
            return;
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(sharedPreferences.edit().putBoolean(KEY_STARTED_FROM_LAUNCHER, true));
    }
}

