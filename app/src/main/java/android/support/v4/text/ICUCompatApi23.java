/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package android.support.v4.text;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

class ICUCompatApi23 {
    private static final String TAG = "ICUCompatIcs";
    private static Method sAddLikelySubtagsMethod;

    static {
        try {
            sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", Locale.class);
            return;
        }
        catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    ICUCompatApi23() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String maximizeAndGetScript(Locale locale) {
        try {
            return ((Locale)sAddLikelySubtagsMethod.invoke(null, locale)).getScript();
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.w((String)TAG, (Throwable)invocationTargetException);
            return locale.getScript();
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.w((String)TAG, (Throwable)illegalAccessException);
            return locale.getScript();
        }
    }
}

