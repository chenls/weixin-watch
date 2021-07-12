/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package mobvoiapi;

import android.text.TextUtils;

public final class a {
    public static Object a(Object object) {
        if (object == null) {
            throw new NullPointerException("obj = " + object);
        }
        return object;
    }

    public static Object a(Object object, Object object2) {
        if (object == null) {
            throw new NullPointerException(String.valueOf(object2));
        }
        return object;
    }

    public static String a(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("String is = " + string2);
        }
        return string2;
    }

    public static String a(String string2, Object object) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }

    public static void a(boolean bl2) {
        if (!bl2) {
            throw new IllegalStateException();
        }
    }

    public static void a(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static void b(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }
}

