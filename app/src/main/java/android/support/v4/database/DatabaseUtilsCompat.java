/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package android.support.v4.database;

import android.text.TextUtils;

public final class DatabaseUtilsCompat {
    private DatabaseUtilsCompat() {
    }

    public static String[] appendSelectionArgs(String[] stringArray, String[] stringArray2) {
        if (stringArray == null || stringArray.length == 0) {
            return stringArray2;
        }
        String[] stringArray3 = new String[stringArray.length + stringArray2.length];
        System.arraycopy(stringArray, 0, stringArray3, 0, stringArray.length);
        System.arraycopy(stringArray2, 0, stringArray3, stringArray.length, stringArray2.length);
        return stringArray3;
    }

    public static String concatenateWhere(String string2, String string3) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return string3;
        }
        if (TextUtils.isEmpty((CharSequence)string3)) {
            return string2;
        }
        return "(" + string2 + ") AND (" + string3 + ")";
    }
}

