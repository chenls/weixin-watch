/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package u.aly;

import android.content.Context;
import android.content.SharedPreferences;

public class ap {
    private static final String a = "umeng_general_config";

    private ap() {
    }

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences(a, 0);
    }

    public static SharedPreferences a(Context context, String string2) {
        return context.getSharedPreferences(string2, 0);
    }
}

